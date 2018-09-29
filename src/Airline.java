
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Airline {
    private String airlineCode;
    private String airlineName;
    private String country;
    private String allianceName;

    public String getAirlineCode() {
        return airlineCode;
    }
    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineName() {
        return airlineName;
    }
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getAllianceName() {
        return allianceName;
    }
    public void setAllianceName(String allianceName) {
        this.allianceName = allianceName;
    }
    /** Method to check if a table exists
	   * @param connection java.sql.Connection object
	   * @return true is the table exists, false otherwise
	   * @throws SQLException
	   */
    private boolean doesTableExist(Connection connection) throws SQLException {
        boolean bTableExists = false;

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet rs = dmd.getTables (null, null, "AIRLINE", null);
        while (rs.next()){
            bTableExists =  true;
        }
        rs.close(); // close the result set
        return bTableExists;
    }
    
	  /** Method to create the table
	   * @param connection java.sql.Connection object
	   * @throws SQLException
	   */

    private void createTable(Connection connection) throws SQLException
    {
        StringBuffer sbCreate = new StringBuffer();
        sbCreate.append(" CREATE TABLE AIRLINE ");
        sbCreate.append(" ( ");
        sbCreate.append("     airline_code VARCHAR(20), ");
        sbCreate.append("     airline_name VARCHAR(20), ");
        sbCreate.append("     country VARCHAR(20), ");
        sbCreate.append("     alliance_name, ");
        sbCreate.append("primary key(airline_code),");
        sbCreate.append("foreign key(alliance_name) references Alliance(alliance_name)");
        sbCreate.append(" ) ");

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate (sbCreate.toString());
        } catch (SQLException e) {
            throw e;
        } finally {
            statement.close();
        }
    }

    
	  /**
	   * @return
	   * @throws SQLException
	   */
    public Airline[] loadAll() throws SQLException
    {
        // get the connection
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT airline_code, airline_name, country, alliance_name FROM AIRLINE");

        Statement statement = null;
        ResultSet rs = null;
        ArrayList collection = new ArrayList();
        try
        {
            // create the statement
            statement = connection.createStatement();
            // Insert the data
            rs = statement.executeQuery(sbSelect.toString());
            if (rs != null) {
                // when the resultset is not null, there are records returned
                while (rs.next())
                {
                    // loop through each result and store the data
                    // as an MusicCollectionDatabase object
                	Airline airline = new Airline();
                	airline.setAirlineCode(rs.getString("airline_code"));
                	airline.setAirlineName(rs.getString("airline_name"));
                	airline.setCountry(rs.getString("country"));
                	airline.setAllianceName(rs.getString("alliance_name"));

                    // store it in the list
                    collection.add(airline);
                }
            }
        } catch (SQLException e)
        {
            throw e;
        } finally
        {
            rs.close();
            statement.close();
            connect.close(connection);
        }

        //   return the array
        return (Airline[])collection.toArray(new Airline[0]);
    }

    /**insert data into table
     * @throws SQLException
     */
    public void insertData () throws SQLException
    {	
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();
     // check if table exists
        if (!this.doesTableExist(connection))
        {  // create the table
            System.out.println("Table doesn't exist. Creating it.....");
            createTable(connection);
        }
     
        
        // create the INSERT SQL
        StringBuffer sbInsert = new StringBuffer();
        sbInsert.append(" INSERT INTO AIRLINE ");
        sbInsert.append(" VALUES ");
        sbInsert.append(" ('" + airlineCode + "', '" + airlineName + "', '" + country + "', '" + allianceName + "')");

        // create the statement
        Statement statement = connection.createStatement();
        try
        {
            statement.executeUpdate (sbInsert.toString());
        } catch (SQLException e)
        {
            throw e;
        } finally
        {
            statement.close();
            connect.close(connection);
        }
    }
    
    private static int count = 1;
    
    public static void insertRecord(String InsertCode, String InsertName, String InsertCountry, String InsertAlliance){
     System.out.println("Insert Airline " + count);
     Airline airlineTB = new Airline();
     airlineTB.setAirlineCode(InsertCode);
     airlineTB.setAirlineName(InsertName);
     airlineTB.setCountry(InsertCountry);
     airlineTB.setAllianceName(InsertAlliance);
     count = count + 1;

     try
     {
    	 airlineTB.insertData();
     } catch (SQLException sqlException)
     {
         while (sqlException != null)
         {
             sqlException.printStackTrace();
             sqlException = sqlException.getNextException();
         }
     } catch (Exception e)
     {
         e.printStackTrace();
     }}
    
    
    public static void PrintOut() {
    	Airline airlineTB = new Airline();    //change
    	Airline [] db;                           //change
        try {
            db = airlineTB.loadAll();    //change
            System.out.println("\nAirline");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
            	Airline mdb = db[i];
                System.out.println(mdb.getAirlineCode()+ " " + mdb.getAirlineName()+ " " +mdb.getCountry()+ " " +mdb.getAllianceName());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }
}