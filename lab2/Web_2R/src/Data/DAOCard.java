package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Entity.Card;

public class DAOCard {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getName());
	
	private static final String SELECT_CARD = "SELECT * FROM Card WHERE id = ?";
	private static final String INSERT_CARD = "INSERT INTO Card (id, cardNumber, account, client) VALUES (?, ?, ?, ?)";
	private static final String DELETE_CARD = "DELETE FROM Card WHERE id = ?";
	private static final String UPDATE_CARD = "UPDATE Card SET cardNumber = ?, account = ?, client = ? WHERE id = ?";

	public DAOCard() throws Exception {
		super();
	}
    
    public Card getCard(int cardId) throws Exception {
    	Card card = new Card();
        
        Connection connection = null;
        
        try {
    		connection = ConnectionPool.getInstance().openConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CARD);
    	
        	preparedStatement.setInt(1, cardId);
        	
        	ResultSet resultSet = preparedStatement.executeQuery();
        	while (resultSet.next()) {
        		card = new Card(resultSet.getInt("id"), 
   					 resultSet.getString("cardNumber"),
   					 new DAOAccount().getAccount(resultSet.getInt("account")),
   					 new DAOClient().getClient(resultSet.getInt("client")));
            }
        	
        	ConnectionPool.getInstance().closeConnection(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching cards", e);
        }
        
        return card;
    }
    
    public void addCard(Card card) throws Exception {
    	
        Connection connection = null;
        
        try {

    		connection = ConnectionPool.getInstance().openConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CARD);
    	
        	preparedStatement.setInt(1, card.getId());
        	preparedStatement.setString(2, card.getCardNumber());
        	preparedStatement.setInt(3, card.getAccount().getId());
        	preparedStatement.setInt(4, card.getClient().getId());
        	
            preparedStatement.executeUpdate();
            
            ConnectionPool.getInstance().closeConnection(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching cards", e);
        }
    }
    
    public void deleteCard(Card card) throws Exception {
    	
        Connection connection = null;
        
    	try {

			connection = ConnectionPool.getInstance().openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CARD);
		
    		preparedStatement.setInt(1, card.getId());
    		preparedStatement.executeUpdate();
    		
    		System.out.println(connection);
    		
    		ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching cards", e);
        }
    }
    
    public void deleteCard(int cardId) throws Exception {
    	
        Connection connection = null;
        
    	try {
			connection = ConnectionPool.getInstance().openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CARD);
		
    		preparedStatement.setInt(1, cardId);
    		preparedStatement.executeUpdate();
    		
    		ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching cards", e);
        }
    }
    
    public void updateCard(Card card) throws Exception {
    	
        Connection connection = null;
        
    	try {
			connection = ConnectionPool.getInstance().openConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CARD);
		
    		preparedStatement.setInt(4, card.getId());
        	preparedStatement.setString(1, card.getCardNumber());
        	preparedStatement.setInt(2, card.getAccount().getId());
        	preparedStatement.setInt(3, card.getClient().getId());
            preparedStatement.executeUpdate();
    		
            ConnectionPool.getInstance().closeConnection(connection);
    	} catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching cards", e);
        }
    }
}
