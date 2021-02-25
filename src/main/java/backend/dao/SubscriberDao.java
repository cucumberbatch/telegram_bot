package backend.dao;

import backend.db.JdbcConnection;
import backend.model.Subscriber;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;

import static backend.Constants.LOGGER;

public class SubscriberDao implements Dao<Subscriber, Integer> {
    private final Optional<Connection> connection;

    public SubscriberDao() {
        connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Subscriber> get(Integer id) {
        LOGGER.log(Level.INFO, "DAO get method invoked! Params: {0}", id);
        String query = "SELECT * FROM telegram_bot.subscribers WHERE id = " + id;
        return getSubscriber(query);
    }

    public Optional<Subscriber> getByNumber(String number) {
        LOGGER.log(Level.INFO, "DAO getByNumber method invoked! Params: {0}", number);
        Optional<Subscriber> subscriber = Optional.of(new Subscriber(number, new ArrayList<>()));
        try (PreparedStatement statement = connection.get().prepareStatement(SQLQueries.GET_TAG_BY_NUMBER_QUERY)) {
            statement.setString(1, number);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                subscriber.get().getTags().add(set.getString("tag"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        LOGGER.log(Level.INFO, "Found {0} in database!", subscriber.get());
        return subscriber;
    }

    private Optional<Subscriber> getSubscriber(String query) {
        return null;
    }

    public Optional<Subscriber> getAllTags() {
        return null;
    }

    @Override
    public List<Subscriber> getAll() {
        LOGGER.info("DAO getAll method invoked!");
        String query = "SELECT * FROM telegram_bot.subscribers";
        List<Subscriber> subscribers = new ArrayList<>();

        try (Statement statement = connection.get().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String phone_number = resultSet.getString("phone_number");
                String tag = resultSet.getString("id");

                Subscriber subscriber = new Subscriber(phone_number, Arrays.asList(tag));
                subscribers.add(subscriber);

                LOGGER.log(Level.INFO, "Found {0} in database", subscriber);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        LOGGER.log(Level.INFO, "Collected subscriber list: {0}", subscribers);
        return subscribers;
    }

    @Override
    public Optional<Integer> insert(Subscriber subscriber) {
        LOGGER.log(Level.INFO, "DAO insert method invoked! Params: {0}", subscriber);
        String selectIdOfSpecificPhoneNumberQuery =
                "select telegram_bot.subscribers.id from telegram_bot.subscribers where telegram_bot.subscribers.phone_number = '" + subscriber.getPhoneNumber() + "'";
        String insertPhoneNumberQuery =
                "insert into telegram_bot.subscribers (phone_number) values (?)";
        String selectIdOfSpecificTagQuery =
                "select telegram_bot.tags.id from telegram_bot.tags where telegram_bot.tags.tag = '" + subscriber.getTags().get(0) + "'";
        String insertSpecificTagQuery =
                "insert into telegram_bot.tags (tag) values (?)";
        String insertLinkNumberToTagQuery =
                "insert into telegram_bot.subscriber_tags_links (subscriber_id, tag_id) values (?, ?)";
        String selectIdOfTagToNumberLinkQuery =
                "select telegram_bot.subscriber_tags_links.id from telegram_bot.subscriber_tags_links where telegram_bot.subscriber_tags_links.subscriber_id = ? and telegram_bot.subscriber_tags_links.tag_id = ?";

        Optional<Integer> generatedId = Optional.empty();
        int subscriberTagId           = 0;
        int subscriberPhoneNumberId   = 0;

        // Try to find a specific phone number
        LOGGER.log(Level.INFO, "Trying to find a phone_number {0} ...", subscriber.getPhoneNumber());
        subscriberPhoneNumberId = executeSelectQuery(selectIdOfSpecificPhoneNumberQuery);


        // try to find a specific subscriber tag
        LOGGER.log(Level.INFO, "Trying to find a tag [{0}] ...", subscriber.getTags().get(0));
        subscriberTagId = executeSelectQuery(selectIdOfSpecificTagQuery);

        // if a specific tag wasn't found we need to insert it
        if (subscriberTagId == 0) {
            LOGGER.log(Level.INFO, "The tag [{0}] wasn't found!\nTrying to execute an insert tag query...", subscriber.getTags().get(0));
            generatedId = insertSpecificTag(subscriber);
            if (generatedId.isPresent()) {
                subscriberTagId = generatedId.get();
            }
        }

        // if a specific number wasn't found we need to insert it
        if (subscriberPhoneNumberId == 0) {
            LOGGER.log(Level.INFO, "The phone_number {0} wasn't found!\nTrying to to execute an insert phone_number query...", subscriber.getPhoneNumber());
            generatedId = insertPhoneNumber(subscriber);
            if (generatedId.isPresent()) {
                subscriberPhoneNumberId = generatedId.get();
            }
        }

        // we trying to find the link that might be already exist in database
        LOGGER.log(Level.INFO, "Trying to find an id for linking a subscriber phone_number_id={0} and tag_id={1} ...", new Integer[]{subscriberPhoneNumberId, subscriberTagId});
        try (PreparedStatement statement = connection.get().prepareStatement(selectIdOfTagToNumberLinkQuery)) {
            statement.setInt(1, subscriberPhoneNumberId);
            statement.setInt(2, subscriberTagId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                generatedId = Optional.of(resultSet.getInt("id"));
            } else {
                generatedId = Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        // insert number-to-tag link if we didn't find one
        if (generatedId.isEmpty()) {
            LOGGER.log(Level.INFO, "Trying to execute an insert query for linking a subscriber phone_number_id={0} and tag_id={1} ...", new Integer[]{subscriberPhoneNumberId, subscriberTagId});
            generatedId = insertNumberToTagLink(subscriberTagId, subscriberPhoneNumberId);
        }

        return generatedId;
    }

    private Optional<Integer> insertNumberToTagLink(int subscriberTagId, int subscriberPhoneNumberId) {
        Optional<Integer> generatedId = Optional.empty();
        try (PreparedStatement statement = connection.get().prepareStatement(SQLQueries.INSERT_NUMBER_TO_TAG_LINK_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, subscriberPhoneNumberId);
            statement.setInt(2, subscriberTagId);
            int numberOfInsertedRows = statement.executeUpdate();
            if (numberOfInsertedRows > 0) {
                ResultSet set = statement.getGeneratedKeys();
                if (set.next()) {
                    generatedId = Optional.of(set.getInt("id"));
                    LOGGER.log(Level.INFO, "Inserted a new element with id={0} !", generatedId.get());
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return generatedId;
    }

    private Optional<Integer> insertPhoneNumber(Subscriber subscriber) {
        Optional<Integer> generatedId = Optional.empty();
        try (PreparedStatement statement = connection.get().prepareStatement(SQLQueries.INSERT_PHONE_NUMBER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, subscriber.getPhoneNumber());
            int numberOfInsertedRows = statement.executeUpdate();
            if (numberOfInsertedRows > 0) {
                ResultSet set = statement.getGeneratedKeys();
                if (set.next()) {
                    generatedId = Optional.of(set.getInt("id"));
                    LOGGER.log(Level.INFO, "Inserted a new element with id={0} !", generatedId.get());
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return generatedId;
    }

    private Optional<Integer> insertSpecificTag(Subscriber subscriber) {
        Optional<Integer> generatedId = Optional.empty();
        try (PreparedStatement statement = connection.get().prepareStatement(SQLQueries.INSERT_SPECIFIC_TAG_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, subscriber.getTags().get(0));
            int numberOfInsertedRows = statement.executeUpdate();
            if (numberOfInsertedRows > 0) {
                ResultSet set = statement.getGeneratedKeys();
                if (set.next()) {
                    generatedId = Optional.of(set.getInt("id"));
                    LOGGER.log(Level.INFO, "Inserted a new element with id={0} !", generatedId.get());
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return generatedId;
    }

    private int executeSelectQuery(String query) {
        int foundedId = 0;
        try (Statement statement = connection.get().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                foundedId = resultSet.getInt("id");
                LOGGER.log(Level.INFO, "Founded id = " + foundedId);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return foundedId;
    }

    @Override
    public void update(Subscriber subscriber) {

    }

    @Override
    public void delete(Subscriber subscriber) {

    }
}
