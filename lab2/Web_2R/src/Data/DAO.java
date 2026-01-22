package Data;

public class DAO {

	protected JDBCConnector connector;
	
	public DAO() throws Exception {
		try {
			connector = new JDBCConnector();
		} catch (Exception e) {
			throw new Exception("Can't create JdbcConnector ", e);
		}	
	}
	
	public JDBCConnector getJDBCConnector() { return connector; }
	
}
