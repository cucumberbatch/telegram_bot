package backend;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class Constants {

    public static final Logger LOGGER = Logger.getLogger(Constants.class.getName());

    public static String APPLICATION_NAME = "Quickstart";

    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static final String BOT_USERNAME = "PhoneNumberTaggerBot";
    public static final String BOT_TOKEN    = "token";


    // PostgreSQL authentication data
    public static final String DB_URL = "jdbc:postgresql://localhost/";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "admin";

}
