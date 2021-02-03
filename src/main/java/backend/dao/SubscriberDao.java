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
        String query = "SELECT * FROM telegram_bot.phone_book WHERE id = " + id;
        return getSubscriber(query);
    }

    public Optional<Subscriber> getByNumber(Long number) {
        String query = "SELECT * FROM telegram_bot.phone_book WHERE phone_number = " + number;
        return getSubscriber(query);
    }

    private Optional<Subscriber> getSubscriber(String query) {
        Optional<Subscriber> subscriber = Optional.empty();

        try (Statement statement = connection.get().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                long phone_number = resultSet.getLong("phone_number");
                String tag = resultSet.getString("tag");

                subscriber = Optional.of(new Subscriber(phone_number, Arrays.asList(tag)));

                LOGGER.log(Level.INFO, "Found {0} in database", subscriber.get());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        return subscriber;
    }

    @Override
    public List<Subscriber> getAll() {
        String query = "SELECT * FROM telegram_bot.phone_book";
        List<Subscriber> subscribers = new ArrayList<>();

        try (Statement statement = connection.get().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                long phone_number = resultSet.getLong("phone_number");
                String tag = resultSet.getString("tag");

                Subscriber subscriber = new Subscriber(phone_number, Arrays.asList(tag));
                subscribers.add(subscriber);

                LOGGER.log(Level.INFO, "Found {0} in database", subscriber);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        return subscribers;
    }

    @Override
    public Optional<Integer> insert(Subscriber subscriber) {
        String query = "INSERT INTO telegram_bot.phone_book (phone_number, tag) VALUES (?, ?)";

        Subscriber nonNullSubscriber = Objects.requireNonNull(subscriber);
        Optional<Integer> generatedId = Optional.empty();

        try (PreparedStatement statement = connection.get().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, nonNullSubscriber.getPhoneNumber());
            statement.setString(2, nonNullSubscriber.getTags().get(0));

            int numberOfInsertedRows = statement.executeUpdate();

            // Retrieve the auto-generated id
            if (numberOfInsertedRows > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        generatedId = Optional.of(resultSet.getInt(1));
                    }
                }
            }

            LOGGER.log(Level.INFO, "{0} created successfully? {1}", new Object[]{nonNullSubscriber, (numberOfInsertedRows > 0)});
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return generatedId;
    }

    @Override
    public void update(Subscriber subscriber) {

    }

    @Override
    public void delete(Subscriber subscriber) {

    }
}
