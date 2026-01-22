package Data;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

import Entity.Account;

public class DAOAccount {
	
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getName());
	
	private static final String SELECT_CLIENT_ACCOUNTS = "SELECT * FROM Account WHERE client = ?";
	
	private static final String SELECT_ACCOUNT = "SELECT * FROM Account WHERE id = ?";
	private static final String SELECT_ACCOUNT_2 = "SELECT * FROM Account WHERE accountNumber = ?";
	private static final String INSERT_ACCOUNT = "INSERT INTO Account (id, accountNumber, balance, client) VALUES (?, ?, ?, ?)";
	private static final String DELETE_ACCOUNT = "DELETE FROM Account WHERE id = ?";
	private static final String UPDATE_ACCOUNT = "UPDATE Account SET accountNumber = ?, balance = ?, client = ? WHERE id = ?";

	public DAOAccount() throws Exception {
		super();
	}
	
    public List<Account> getClientAccounts(int clientId) throws Exception {
        List<Account> accounts = new ArrayList<>();
        
        Connection connection = null;
        
        try {
        	connection = ConnectionPool.getInstance().openConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_ACCOUNTS);
    	
        	preparedStatement.setInt(1, clientId);
        	
        	ResultSet resultSet = preparedStatement.executeQuery();
        	while (resultSet.next()) {
        		Account account = new Account(resultSet.getInt("id"), 
                					 resultSet.getString("accountNumber"),
                					 resultSet.getFloat("balance"),
                					 new DAOClient().getClient(resultSet.getInt("client")));
        		accounts.add(account);
            }
        	
        	ConnectionPool.getInstance().closeConnection(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching accounts", e);
        }
        
        return accounts;
    }
    
    public Account getAccount(int accountId) throws Exception {
    	Account account = new Account();
        
        Connection connection = null;
        
        try {
    		connection = ConnectionPool.getInstance().openConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT);
    	
        	preparedStatement.setInt(1, accountId);
        	
        	ResultSet resultSet = preparedStatement.executeQuery();
        	while (resultSet.next()) {
        		account = new Account(resultSet.getInt("id"), 
   					 resultSet.getString("accountNumber"),
   					 resultSet.getFloat("balance"),
					 new DAOClient().getClient(resultSet.getInt("client")));
            }
        	
        	ConnectionPool.getInstance().closeConnection(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching accounts", e);
        }
        
        return account;
    }
    
    public Account getAccount(String accountNumber) throws Exception {
    	Account account = new Account();
        
        Connection connection = null;
        
        try {
    		connection = ConnectionPool.getInstance().openConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_2);
    	
        	preparedStatement.setString(1, accountNumber);
        	
        	ResultSet resultSet = preparedStatement.executeQuery();
        	while (resultSet.next()) {
        		account = new Account(resultSet.getInt("id"), 
   					 resultSet.getString("accountNumber"),
   					 resultSet.getFloat("balance"),
					 new DAOClient().getClient(resultSet.getInt("client")));
            }
        	
        	ConnectionPool.getInstance().closeConnection(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching accounts", e);
        }
        
        return account;
    }
    
    public void addAccount(Account account) throws Exception {
    	
        Connection connection = null;
        
        try {

    		connection = ConnectionPool.getInstance().openConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT);
    	
        	preparedStatement.setInt(1, account.getId());
        	preparedStatement.setString(2, account.getAccountNumber());
        	preparedStatement.setDouble(3, account.getBalance());
        	preparedStatement.setInt(4, account.getClient().getId());
            preparedStatement.executeUpdate();
            
            ConnectionPool.getInstance().closeConnection(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching accounts", e);
        }
    }
    
    public void deleteAccount(Account account) throws Exception {
    	
        Connection connection = null;
        
    	try {

			connection = ConnectionPool.getInstance().openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACCOUNT);
		
    		preparedStatement.setInt(1, account.getId());
    		preparedStatement.executeUpdate();
    		
    		System.out.println(connection);
    		
    		ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching accounts", e);
        }
    }
    
    public void deleteAccount(int accountId) throws Exception {
    	
        Connection connection = null;
        
    	try {
			connection = ConnectionPool.getInstance().openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACCOUNT);
		
    		preparedStatement.setInt(1, accountId);
    		preparedStatement.executeUpdate();
    		
    		ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching accounts", e);
        }
    }
    
    public void updateAccount(Account account) throws Exception {
    	
        Connection connection = null;
        
    	try {
			connection = ConnectionPool.getInstance().openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT);
		
    		preparedStatement.setInt(4, account.getId());
        	preparedStatement.setString(1, account.getAccountNumber());
        	preparedStatement.setDouble(2, account.getBalance());
        	preparedStatement.setInt(3, account.getClient().getId());
            preparedStatement.executeUpdate();
    		
            ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching accounts", e);
        }
    }
}
