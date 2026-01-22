package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import Entity.Payment;

public class DAOPayment {

	private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getName());

	private static final String SELECT_CLIENT_PAYMENTS = "SELECT * FROM Payment WHERE sender = ?";

	private static final String SELECT_PAYMENT = "SELECT * FROM Payment WHERE id = ?";
	private static final String INSERT_PAYMENT = "INSERT INTO Payment (id, sender, reciever, amount) VALUES (?, ?, ?, ?)";
	private static final String DELETE_PAYMENT = "DELETE FROM Payment WHERE id = ?";
	private static final String UPDATE_PAYMENT = "UPDATE Payment SET sender = ?, receiver = ?, amount = ? WHERE id = ?";
	
	public DAOPayment() throws Exception {
		super();
	}
	
    public List<Payment> getClientPayments(int clientId) throws Exception {
        List<Payment> payments = new ArrayList<>();
        
        Connection connection = null;
        
        try {
        	connection = ConnectionPool.getInstance().openConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_PAYMENTS);
    	
        	preparedStatement.setInt(1, clientId);
        	
        	ResultSet resultSet = preparedStatement.executeQuery();
        	while (resultSet.next()) {
        		Payment payment = new Payment(resultSet.getInt("id"),
						new DAOAccount().getAccount(resultSet.getInt("sender")),
						new DAOAccount().getAccount(resultSet.getInt("reciever")),	
						resultSet.getDouble("amount"));
        		payments.add(payment);
            }
        	
        	ConnectionPool.getInstance().closeConnection(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching payments", e);
        }
        
        return payments;
    }
    
    public Payment getPayment(int paymentId) throws Exception {
    	Payment payment = new Payment();
        
        Connection connection = null;
        
        try {
    		connection = ConnectionPool.getInstance().openConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAYMENT);
    	
        	preparedStatement.setInt(1, paymentId);
        	
        	ResultSet resultSet = preparedStatement.executeQuery();
        	while (resultSet.next()) {
        		payment = new Payment(resultSet.getInt("id"),
        						new DAOAccount().getAccount(resultSet.getInt("sender")),
        						new DAOAccount().getAccount(resultSet.getInt("reciever")),	
        						resultSet.getDouble("amount"));
            }
        	
        	ConnectionPool.getInstance().closeConnection(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching payments", e);
        }
        
        return payment;
    }
    
    public void addPayment(Payment payment) throws Exception {
    	
        Connection connection = null;
        
        try {

    		connection = ConnectionPool.getInstance().openConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PAYMENT);
    	
        	preparedStatement.setInt(1, payment.getId());
        	preparedStatement.setInt(2, payment.getSender().getId());
        	preparedStatement.setInt(3, payment.getReciever().getId());
        	preparedStatement.setDouble(4, payment.getAmount());
        	
            preparedStatement.executeUpdate();
            
            ConnectionPool.getInstance().closeConnection(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching payments", e);
        }
    }
    
    public void deletePayment(Payment payment) throws Exception {
    	
        Connection connection = null;
        
    	try {

			connection = ConnectionPool.getInstance().openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PAYMENT);
		
    		preparedStatement.setInt(1, payment.getId());
    		preparedStatement.executeUpdate();
    		
    		ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching payments", e);
        }
    }
    
    public void deletePayment(int paymentId) throws Exception {
    	
        Connection connection = null;
        
    	try {
			connection = ConnectionPool.getInstance().openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PAYMENT);
		
    		preparedStatement.setInt(1, paymentId);
    		preparedStatement.executeUpdate();
    		
    		ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching payments", e);
        }
    }
    
    public void updatePayment(Payment payment) throws Exception {
    	
        Connection connection = null;
        
    	try {
			connection = ConnectionPool.getInstance().openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PAYMENT);
		
    		preparedStatement.setInt(4, payment.getId());
        	preparedStatement.setInt(1, payment.getSender().getId());
        	preparedStatement.setInt(2, payment.getReciever().getId());
        	preparedStatement.setDouble(3, payment.getAmount());
            preparedStatement.executeUpdate();
    		
            ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching payments", e);
        }
    }

	
}
