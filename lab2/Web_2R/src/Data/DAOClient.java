package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Interface.Client;

public class DAOClient {
	
	private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getName());

	private static final String SELECT_CLIENT = "SELECT * FROM Client WHERE id = ?";
	private static final String INSERT_CLIENT = "INSERT INTO Client (id, name, contact) VALUES (?, ?, ?)";
	private static final String DELETE_CLIENT = "DELETE FROM Client WHERE id = ?";
	private static final String UPDATE_CLIENT = "UPDATE Client SET name = ?, contact = ? WHERE id = ?";

	public DAOClient() throws Exception {
		super();
	}
	
	public Client getClient(int clientId) throws Exception {
		Client client = new Client();
		
		Connection connection = null;
        
        try {
        	connection = ConnectionPool.getInstance().openConnection();
        	PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT);
        	
        	preparedStatement.setInt(1, clientId);
        	
        	ResultSet resultSet = preparedStatement.executeQuery();
        	while (resultSet.next()) {
        		client = new Client(resultSet.getInt("id"), 
                					resultSet.getString("name"),
                					resultSet.getString("contact"));
            }
        	
        	ConnectionPool.getInstance().closeConnection(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching clients", e);
        }
        
        return client;
    }
    
    public void addClient(Client client) throws Exception {
		
		Connection connection = null;
        
        try {
        	connection = ConnectionPool.getInstance().openConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENT);
    	
        	preparedStatement.setInt(1, client.getId());
        	preparedStatement.setString(2, client.getName());
        	preparedStatement.setString(3, client.getContactInfo());
            preparedStatement.executeUpdate();
            
            ConnectionPool.getInstance().closeConnection(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching clients", e);
        }
    }
    
    public void deleteClient(Client client) throws Exception {
		
		Connection connection = null;
        
    	try {
			connection = ConnectionPool.getInstance().openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT);
		
    		preparedStatement.setInt(1, client.getId());
    		preparedStatement.executeUpdate();
    		
    		ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching clients", e);
        }
    }
    
    public void deleteClient(int clientId) throws Exception {
		
		Connection connection = null;
        
    	try {
			connection = ConnectionPool.getInstance().openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT);
		
    		preparedStatement.setInt(1, clientId);
    		preparedStatement.executeUpdate();
    		
    		ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching clients", e);
        }
    }
    
    public void updateClient(Client client) throws Exception {
		
		Connection connection = null;
        
    	try {
    		connection = ConnectionPool.getInstance().openConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT);
		
    		preparedStatement.setInt(3, client.getId());
        	preparedStatement.setString(1, client.getName());
        	preparedStatement.setString(2, client.getContactInfo());
            preparedStatement.executeUpdate();
    		
            ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching clients", e);
        }
    }
}
