package Data;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;



public class ConnectionPool {
	
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getName());
    private static final int INITIAL_POOL_SIZE = 10;
    private static BlockingQueue<Connection> connectionPool;
    
    static ResourceBundle resource = ResourceBundle.getBundle("database");
    private static final String URL = resource.getString("url");
    private static final String DRIVER = resource.getString("driver");
    private static final String USER = resource.getString("user");
    private static final String PASSWORD = resource.getString("password"); 
	
    private static ConnectionPool instance;
    private ConnectionPool() throws Exception {
    	Class.forName(DRIVER);
    	connectionPool = new LinkedBlockingQueue<>(INITIAL_POOL_SIZE);
    	initializePool();
    }
    public static ConnectionPool getInstance() throws Exception {
        if (instance == null) { instance = new ConnectionPool(); }
        return instance;
    }


    private void initializePool() {
    	for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
    		try {
    			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    			connectionPool.offer(connection);
    		} catch (SQLException e) {
    			LOGGER.log(Level.SEVERE, "Error creating initial connections", e);
    		}
    	}
    }
    
    public Connection openConnection() throws Exception {
    	try {
    		return connectionPool.take();    		
    	} catch (InterruptedException e) {
    		LOGGER.log(Level.SEVERE, "Thread was interrupted while waiting for connection", e);
    		Thread.currentThread().interrupt(); 
    		return null;
    	}
    }
    
    public void closeConnection(Connection connection) throws Exception {
    	if (connection != null) {
    		connectionPool.offer(connection, 10, TimeUnit.SECONDS);
    	}
    }
    
    public void closePool() {
    	while (!connectionPool.isEmpty()) {
    		try {
    			Connection connection = connectionPool.take();
    			if (connection != null) {
    				connection.close();
    			}
	    	} catch (SQLException | InterruptedException e) {
	    		LOGGER.log(Level.SEVERE, "Error while closing connection", e);
	    	}
    	}
    }

}
