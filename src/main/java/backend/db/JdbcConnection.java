package backend.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static backend.Constants.*;

public class JdbcConnection {
    private Optional<Connection> connection = Optional.empty();

    public JdbcConnection(String url, String user, String password) {
        try {
            connection = Optional.ofNullable(DriverManager.getConnection(url, user, password));
        } catch (SQLException ex) {
            LOGGER.info(null, ex);
        }
    }

    public Optional<Connection> getConnection() {
        return this.connection;
    }
}
