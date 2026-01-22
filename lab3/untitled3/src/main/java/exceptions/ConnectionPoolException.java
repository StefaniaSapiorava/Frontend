package exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPoolException extends Exception {
    private static final Logger logger = LogManager.getLogger(ConnectionPoolException.class);
    private String connectionUrl;
    private String username;

    public ConnectionPoolException(String message) {
        super(message);
        logError(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
        logError(message, cause);
    }

    // Конструктор с сообщением, URL подключения и именем пользователя
    public ConnectionPoolException(String message, String connectionUrl, String username) {
        super(message);
        this.connectionUrl = connectionUrl;
        this.username = username;
        logError(message);
    }

    // Конструктор с сообщением, исходным исключением, URL подключения и именем пользователя
    public ConnectionPoolException(String message, Throwable cause, String connectionUrl, String username) {
        super(message, cause);
        this.connectionUrl = connectionUrl;
        this.username = username;
        logError(message, cause);
    }

    // Логирование ошибки без исходного исключения
    private void logError(String message) {
        logger.error("ConnectionPoolException: " + message + ". Connection URL: " + connectionUrl + ", Username: " + username);
    }

    // Логирование ошибки с исходным исключением
    private void logError(String message, Throwable cause) {
        logger.error("ConnectionPoolException: " + message + ". Connection URL: " + connectionUrl + ", Username: " + username, cause);
    }

    // Переопределяем метод toString для более детального вывода
    @Override
    public String toString() {
        return "ConnectionPoolException: " + getMessage() + "\nConnection URL: " + connectionUrl + "\nUsername: " + username;
    }
}
