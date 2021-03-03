package backend.db.dao;

import backend.db.JdbcConnection;
import backend.dto.SubscriberNumberDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static backend.Constants.LOGGER;

public class SubscriberNumbersDao implements Dao<SubscriberNumberDto, Integer> {
    private final Optional<Connection> connection;

    public SubscriberNumbersDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Integer> insert(SubscriberNumberDto subscriberNumberDto) {
        String query = "insert into telegram_bot.subscribers (phone_number) values (?)";
        Optional<Integer> id = Optional.empty();

        try (PreparedStatement statement = connection.get().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, subscriberNumberDto.getPhoneNumber());
            int numberOfInsertedRows = statement.executeUpdate();
            if (numberOfInsertedRows > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = Optional.of(resultSet.getInt(1));
                    LOGGER.info(String.format("Inserted a phone number [%1$s] with id [%2$s]!", subscriberNumberDto.getPhoneNumber(), id.get()));
                }
            }
        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return id;
    }

    @Override
    public void update(SubscriberNumberDto subscriberNumberDto) {
    }

    @Override
    public void delete(SubscriberNumberDto subscriberNumberDto) {
    }

    @Override
    public Optional<SubscriberNumberDto> get(Integer id) {
        String query = "select telegram_bot.subscribers.phone_number from telegram_bot.subscribers where telegram_bot.subscribers.id = (?)";
        Optional<SubscriberNumberDto> dto = Optional.empty();

        try (PreparedStatement statement = connection.get().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dto = Optional.of(new SubscriberNumberDto(resultSet.getString(1)));
            }
            LOGGER.info(String.format("Phone number search result [%1$s] by id [%2$s]", dto.get(), id));
        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return dto;
    }

    @Override
    public List<SubscriberNumberDto> getAll() {
        String query = "select telegram_bot.subscribers.phone_number from telegram_bot.subscribers";
        List<SubscriberNumberDto> dtos = new ArrayList<>();

        try (PreparedStatement statement = connection.get().prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String phoneNumber = resultSet.getString(1);
                dtos.add(new SubscriberNumberDto(phoneNumber));
            }
            LOGGER.info(String.format("Founded numbers in database: [%1$s]", dtos));
        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return dtos;
    }
}
