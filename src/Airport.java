

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Airport {
    private String airportCode;
    private String city;
    private String country;

    public String getAirportCode() {
        return airportCode;
    }
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
	
	/** Method to check if a table exists
	 * @param connection java.sql.Connection object
	 * @return true is the table exists, false otherwise
	 * @throws SQLException
	 */
    private boolean doesTableExist(Connection connection) throws SQLException {
        boolean bTableExists = false;

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet rs = dmd.getTables (null, null, "AIRPORT", null);
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
        sbCreate.append(" CREATE TABLE AIRPORT ");
        sbCreate.append(" ( ");
        sbCreate.append("     airportCode VARCHAR(20), ");
        sbCreate.append("     city VARCHAR(20), ");
        sbCreate.append("     country VARCHAR(20), ");
        sbCreate.append("primary key(airportCode)");
        //sbCreate.append("foreign key(alliance_name) references Alliance(alliance_name)");
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
    public Airport[] loadAll() throws SQLException
    {
        // get the connection
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT airportCode, city, country FROM AIRPORT");

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
                	Airport airport = new Airport();
                	airport.setAirportCode(rs.getString("airportCode"));
                	airport.setCity(rs.getString("city"));
                	airport.setCountry(rs.getString("country"));

                    // store it in the list
                    collection.add(airport);
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
        return (Airport[])collection.toArray(new Airport[0]);
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
        sbInsert.append(" INSERT INTO AIRPORT ");
        sbInsert.append(" VALUES ");
        sbInsert.append(" ('" + airportCode + "', '" + city + "', '" + country + "')");

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
    
    public static void insertRecord(String InsertCode, String InsertCity, String InsertCountry){
     System.out.println("Insert Airport " + count);
     Airport airportTB = new Airport();
     airportTB.setAirportCode(InsertCode);
     airportTB.setCity(InsertCity);
     airportTB.setCountry(InsertCountry);
     count = count + 1;

     try
     {
    	 airportTB.insertData();
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
    	Airport airportTB = new Airport();    //change
    	Airport [] db;                           //change
        try {
            db = airportTB.loadAll();    //change
            System.out.println("\nAirport");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
            	Airport mdb = db[i];
                System.out.println(mdb.getAirportCode()+ " " + mdb.getCity()+ " " +mdb.getCountry());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }
}
