package backend.dao;

import backend.db.JdbcConnection;
import backend.model.Subscriber;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberDaoTest {



    @Test
    void testConnection() {
        Optional<Connection> connection = JdbcConnection.getConnection();
        try {
            assertFalse(connection.get().isClosed());
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    void testGetSubscriberById() {
        SubscriberDao dao = new SubscriberDao();
        Optional<Subscriber> subscriber = dao.get(1);
        assertNotNull(subscriber);
    }

    @Test
    void testGetAllSubscribers() {
        SubscriberDao dao = new SubscriberDao();
        List<Subscriber> subscribers = dao.getAll();
        assertFalse(subscribers.isEmpty());
    }

    @Test
    void testInsertSubscriber() {
        SubscriberDao dao = new SubscriberDao();
        Subscriber subscriber = new Subscriber(987654321L, Arrays.asList("num-ber"));
        Optional<Integer> generatedId = dao.insert(subscriber);
        assertFalse(generatedId.isEmpty());
    }
}