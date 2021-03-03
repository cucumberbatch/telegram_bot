package backend.db.dao;

import backend.db.JdbcConnection;
import backend.dto.SubscriberNumberToTagLinkDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static backend.Constants.LOGGER;

public class SubscriberNumberToTagLinksDao implements Dao<SubscriberNumberToTagLinkDto, Integer> {
    private final Optional<Connection> connection;

    public SubscriberNumberToTagLinksDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Integer> insert(SubscriberNumberToTagLinkDto subscriberNumberToTagLinkDto) {
        String query = "insert into telegram_bot.subscriber_tags_links (subscriber_id, tag_id) values (?, ?)";
        Optional<Integer> id = Optional.empty();

        try (PreparedStatement statement = connection.get().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, subscriberNumberToTagLinkDto.getSubscriberId());
            statement.setInt(2, subscriberNumberToTagLinkDto.getTagId());
            int numberOfInsertedRows = statement.executeUpdate();
            if (numberOfInsertedRows > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = Optional.of(resultSet.getInt(1));
                    LOGGER.info(String.format("Inserted a link with subscriber id [%1$s] and tag id [%2$s]!", subscriberNumberToTagLinkDto.getSubscriberId(), subscriberNumberToTagLinkDto.getTagId()));
                }
            }
        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return id;
    }

    @Override
    public void update(SubscriberNumberToTagLinkDto subscriberNumberDto) {
    }

    @Override
    public void delete(SubscriberNumberToTagLinkDto subscriberNumberDto) {
    }

    @Override
    public Optional<SubscriberNumberToTagLinkDto> get(Integer id) {
        String query = "select telegram_bot.subscriber_tags_links.subscriber_id, telegram_bot.subscriber_tags_links.tag_id from telegram_bot.subscriber_tags_links where telegram_bot.subscriber_tags_links.id = (?)";
        Optional<SubscriberNumberToTagLinkDto> dto = Optional.empty();

        try (PreparedStatement statement = connection.get().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer subscriberId = resultSet.getInt(1);
                Integer tagId        = resultSet.getInt(2);
                dto = Optional.of(new SubscriberNumberToTagLinkDto(subscriberId, tagId));
            }
            LOGGER.info(String.format("Link search result [%1$s] by id [%2$s]", dto.get(), id));
        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return dto;
    }

    @Override
    public List<SubscriberNumberToTagLinkDto> getAll() {
        String query = "select telegram_bot.subscriber_tags_links.subscriber_id, telegram_bot.subscriber_tags_links.tag_id from telegram_bot.subscriber_tags_links";
        List<SubscriberNumberToTagLinkDto> dtos = new ArrayList<>();

        try (PreparedStatement statement = connection.get().prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer subscriberId = resultSet.getInt(1);
                Integer tagId        = resultSet.getInt(2);
                dtos.add(new SubscriberNumberToTagLinkDto(subscriberId, tagId));
            }
            LOGGER.info(String.format("Founded links in database: [%1$s]", dtos));

        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return dtos;
    }

}
