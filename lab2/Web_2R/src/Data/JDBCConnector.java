package Data;

import java.sql.*;
import java.util.ResourceBundle;

public class JDBCConnector {
	
	public Connection openConnection() throws Exception {
		Connection connection;
		ResourceBundle resource = ResourceBundle.getBundle("database");
    	String url = resource.getString("url");
    	String driver = resource.getString("driver");
    	String user = resource.getString("user");
    	String password = resource.getString("password");    	
    	
    	try {
    		Class.forName(driver);
    		connection = DriverManager.getConnection(url, user, password);
    	} catch (ClassNotFoundException e) {
    		throw new Exception("Can't load database driver.", e);
    	} catch (SQLException e) {
    		throw new Exception("Can't connect to database.", e);
    	} 
    	
    	if (connection == null) {
    		throw new Exception("Driver type is not correct in URL " + driver + ".");
    	}
  
    	return connection;
	}
	
	public void closeConnection(Connection connection) throws Exception {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new Exception("Can't close connection", e);
			}
		}
		
	}
}	
