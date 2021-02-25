package backend;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Constants {

    public static Logger LOGGER;

    static {
        InputStream stream = Constants.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            LOGGER = Logger.getLogger(Constants.class.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static final String INSERT_MESSAGE_COMMAND_TEXT =
            "Чтобы добавить информацию о номере телефона, необходимо отправить сообщение: /insert +7xxxxxxxxxx yyyy\n" +
            ", где y - символ тега. Длина тега может быть любой.";

    public static final String SUBSCRIBER_DOES_NOT_TAGGED_TEXT = "Данный абонент не помечен тегами.";


    public static String APPLICATION_NAME = "Quickstart";

    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static final String BOT_USERNAME = "PhoneNumberTaggerBot";
    public static final String BOT_TOKEN    = "token";


    // PostgreSQL authentication data
    public static final String DB_URL = "jdbc:postgresql://localhost/";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "admin";

}
