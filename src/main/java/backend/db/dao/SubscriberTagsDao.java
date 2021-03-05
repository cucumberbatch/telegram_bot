package backend.db.dao;

import backend.db.JdbcConnection;
import backend.dto.SubscriberTagDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static backend.Constants.LOGGER;

public class SubscriberTagsDao implements Dao<SubscriberTagDto, Integer> {
    private final Optional<Connection> connection;

    public SubscriberTagsDao(JdbcConnection connection) {
        this.connection = connection.getConnection();
    }

    @Override
    public Optional<Integer> insert(SubscriberTagDto subscriberTagDto) {
        String query = "insert into telegram_bot.tags (tag) values (?)";
        Optional<Integer> id = Optional.empty();

        try (PreparedStatement statement = connection.get().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, subscriberTagDto.getTag());
            int numberOfInsertedRows = statement.executeUpdate();
            if (numberOfInsertedRows > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = Optional.of(resultSet.getInt(1));
                    LOGGER.info(String.format("Inserted a tag [%1$s] with id [%2$s]!", subscriberTagDto.getTag(), id.get()));
                }
            }
        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return id;
    }

    @Override
    public void update(SubscriberTagDto subscriberNumberDto) {
    }

    @Override
    public void delete(SubscriberTagDto subscriberNumberDto) {
    }

    @Override
    public Optional<SubscriberTagDto> get(Integer id) {
        String query = "select telegram_bot.tags.tag from telegram_bot.tags where telegram_bot.tags.id = (?)";
        Optional<SubscriberTagDto> dto = Optional.empty();

        try (PreparedStatement statement = connection.get().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dto = Optional.of(new SubscriberTagDto(resultSet.getString(1)));
                LOGGER.info(String.format("Tag search result [%1$s] by id [%2$s]", dto.get(), id));
            }
        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return dto;
    }

    @Override
    public List<SubscriberTagDto> getAll() {
        String query = "select telegram_bot.tags.tag from telegram_bot.tags";
        List<SubscriberTagDto> dtos = new ArrayList<>();

        try (PreparedStatement statement = connection.get().prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String tag = resultSet.getString(1);
                dtos.add(new SubscriberTagDto(tag));
            }
            LOGGER.info(String.format("Founded tags in database: [%1$s]", dtos));

        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return dtos;
    }

}
