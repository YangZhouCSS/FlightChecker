
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

	
    // DB connection properties
    public String driver = "oracle.jdbc.driver.OracleDriver";
    public String jdbc_url = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";


    
    
    //use these lines when running testbed
    //private String username = testbed.username;
    //private String password = testbed.password;
    
    private String username = GUI.username;
    private String password = GUI.password;
    

    
	//Method to get the connection for the database
    //@return java.sql.Connection object
    public Connection getConnection() {
    	// register the JDBC driver
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
       // create a connection
        Connection connection = null;
        try {
            connection = DriverManager.getConnection (jdbc_url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    //close connection
    public void close(Connection connection) throws SQLException
    {
        try
        {
            connection.close();
        } catch (SQLException e)
        {
            throw e;
        }
    }
    
}
