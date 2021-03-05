package backend.db.dao;

import backend.db.JdbcConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DaoTest {

    SubscriberNumberToTagLinksDao   numberToTagLinksDao;
    SubscriberPhoneNumbersDao       phoneNumbersDao;
    SubscriberTagsDao               tagsDao;

    JdbcConnection                  connection;

    String url      = "jdbc:postgresql://localhost/";
    String user     = "postgres";
    String password = "admin";




    @BeforeEach
    void setUp() {
        connection          = new JdbcConnection(url, user, password);
        numberToTagLinksDao = new SubscriberNumberToTagLinksDao(connection);
        phoneNumbersDao     = new SubscriberPhoneNumbersDao(connection);
        tagsDao             = new SubscriberTagsDao(connection);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }
}