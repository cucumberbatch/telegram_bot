package backend;

import backend.service.DefaultConfigurationReplyKeyboardMarkupFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final Long CHAT_SESSION_TIMEOUT = 600L;
    public static Logger LOGGER;
    public static DefaultConfigurationReplyKeyboardMarkupFactory keyboardFactory;

    static {
        // logger initialization
        LOGGER = LoggerFactory.getLogger(Constants.class.getName());

        // default keyboard factory initialization
        keyboardFactory = DefaultConfigurationReplyKeyboardMarkupFactory.getInstance();
    }


    public static final String INSERT_MESSAGE_COMMAND_TEXT =
            "Чтобы добавить информацию о номере телефона, необходимо отправить сообщение: /insert +7xxxxxxxxxx yyyy\n" +
            ", где y - символ тега. Длина тега может быть любой.";

    public static final String SUBSCRIBER_DOES_NOT_TAGGED_TEXT = "Данный абонент не помечен тегами.";


    public static String APPLICATION_NAME = "Quickstart";

    public static final String BOT_USERNAME = "PhoneNumberTaggerBot";
    public static final String BOT_TOKEN    = "token";


    // PostgreSQL authentication data
    public static final String DB_URL = "jdbc:postgresql://localhost/";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "admin";

}
