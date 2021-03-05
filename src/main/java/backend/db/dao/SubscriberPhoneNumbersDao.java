package backend.db.dao;

import backend.db.JdbcConnection;
import backend.dto.SubscriberPhoneNumberDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static backend.Constants.LOGGER;

public class SubscriberPhoneNumbersDao implements Dao<SubscriberPhoneNumberDto, Integer> {
    private final Optional<Connection> connection;

    public SubscriberPhoneNumbersDao(JdbcConnection connection) {
        this.connection = connection.getConnection();
    }

    @Override
    public Optional<Integer> insert(SubscriberPhoneNumberDto subscriberPhoneNumberDto) {
        String query = "insert into telegram_bot.subscribers (phone_number) values (?)";
        Optional<Integer> id = Optional.empty();

        try (PreparedStatement statement = connection.get().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, subscriberPhoneNumberDto.getPhoneNumber());
            int numberOfInsertedRows = statement.executeUpdate();
            if (numberOfInsertedRows > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = Optional.of(resultSet.getInt(1));
                    LOGGER.info(String.format("Inserted a phone number [%1$s] with id [%2$s]!", subscriberPhoneNumberDto.getPhoneNumber(), id.get()));
                }
            }
        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return id;
    }

    @Override
    public void update(SubscriberPhoneNumberDto subscriberPhoneNumberDto) {
    }

    @Override
    public void delete(SubscriberPhoneNumberDto subscriberPhoneNumberDto) {
    }

    @Override
    public Optional<SubscriberPhoneNumberDto> get(Integer id) {
        String query = "select telegram_bot.subscribers.phone_number from telegram_bot.subscribers where telegram_bot.subscribers.id = (?)";
        Optional<SubscriberPhoneNumberDto> dto = Optional.empty();

        try (PreparedStatement statement = connection.get().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dto = Optional.of(new SubscriberPhoneNumberDto(resultSet.getString(1)));
                LOGGER.info(String.format("Phone number search result [%1$s] by id [%2$s]", dto.get(), id));
            }
        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return dto;
    }

    @Override
    public List<SubscriberPhoneNumberDto> getAll() {
        String query = "select telegram_bot.subscribers.phone_number from telegram_bot.subscribers";
        List<SubscriberPhoneNumberDto> dtos = new ArrayList<>();

        try (PreparedStatement statement = connection.get().prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String phoneNumber = resultSet.getString(1);
                dtos.add(new SubscriberPhoneNumberDto(phoneNumber));
            }
            LOGGER.info(String.format("Founded numbers in database: [%1$s]", dtos));
        } catch (SQLException e) {
            LOGGER.info(e.getSQLState());
        }
        return dtos;
    }
}
