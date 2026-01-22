package database;

import exceptions.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.InputStream;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private final List<Connection> availableConnections = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private static final int MAX_CONNECTIONS = 10;

    private ConnectionPool() throws ConnectionPoolException {
        try {
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                availableConnections.add(createNewConnection());
            }
            logger.info("Connection pool initialized with " + MAX_CONNECTIONS + " connections.");
        } catch (SQLException e) {
            logger.error("Ошибка при инициализации пула соединений", e);
            throw new ConnectionPoolException("Ошибка при инициализации пула соединений", e);
        }
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private Connection createNewConnection() throws SQLException {
        try {
            Properties props = new Properties();
            InputStream input = new FileInputStream("src/main/resoursec/database.properties");
            if (input == null) {
                throw new SQLException("Не удалось найти файл настроек database.properties");
            }
            props.load(input);

            String url = props.getProperty("jdbc.url");
            String user = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new SQLException("Ошибка при загрузке данных из файла настроек", e);
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        lock.lock();
        try {
            if (availableConnections.isEmpty()) {
                throw new ConnectionPoolException("No available connections.");
            }
            Connection conn = availableConnections.remove(availableConnections.size() - 1);
            logger.info("Connection provided.");
            return conn;
        } finally {
            lock.unlock();
        }
    }

    public void releaseConnection(Connection conn) {
        lock.lock();
        try {
            if (conn != null) {
                availableConnections.add(conn);
                logger.info("Connection released.");
            }
        } finally {
            lock.unlock();
        }
    }
}
