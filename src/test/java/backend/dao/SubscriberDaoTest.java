package backend.dao;

import backend.db.JdbcConnection;
import backend.model.Subscriber;
import backend.service.SubscriberService;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberDaoTest {

    private String testNumber1 = "+7(800)555-35-35";
    private String testNumber2 = "+7(844)525-05-30";
    private List<String> testTags1 = Arrays.asList("money");
    private List<String> testTags2 = Arrays.asList("test");


    @Test
    void testConnection() {
        Optional<Connection> connection = JdbcConnection.getConnection();
        try {
            assertFalse(connection.get().isClosed());
        } catch (SQLException throwable) {
            fail();
        }
    }

    @Test
    void testGetAllSubscribers() {
        SubscriberDao dao = new SubscriberDao();
        List<Subscriber> subscribers = dao.getAll();
        assertFalse(subscribers.isEmpty());
    }

    @Test
    void testGetSubscriberByPhoneNumber() {
        Subscriber test = new Subscriber(testNumber1, testTags1);
        Subscriber subscriber = (Subscriber) SubscriberService.getSubscriberByPhoneNumber(testNumber1).getReplyMessage();
        assertNotNull(subscriber);
        assertEquals(test, subscriber);
    }

    @Test
    void testInsertSubscriberTag() {
        Subscriber test = new Subscriber(testNumber2, testTags2);
        Subscriber result = (Subscriber) SubscriberService.addTagByNumber(test.getPhoneNumber(), test.getTags().get(0)).getReplyMessage();
        assertNotNull(result);
    }

    @Test
    void testTagMatcher() {
        String data = "/insert +78005553535 commercial";
        String expectedData = "commercial";
        String regex = "(\\w+)\\s*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        matcher.find();
        String selectedData = matcher.group(1);
        assertEquals(selectedData, expectedData);
    }
}