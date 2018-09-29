
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FlightInstance {
    private String airlineCode;
    private String flightNumber;
    private String flightDate;

    public String getAirlineCode() {
        return airlineCode;
    }
    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }
    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }
	
		/** Method to check if a table exists
	 * @param connection java.sql.Connection object
	 * @return true is the table exists, false otherwise
	 * @throws SQLException
	 */
    private boolean doesTableExist(Connection connection) throws SQLException {
        boolean bTableExists = false;

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet rs = dmd.getTables (null, null, "FLIGHT_INSTANCE", null);
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
        sbCreate.append(" CREATE TABLE FLIGHT_INSTANCE ");
        sbCreate.append(" ( ");
        sbCreate.append("     airline_code VARCHAR(20), ");
        sbCreate.append("     flight_num VARCHAR(20), ");
        sbCreate.append("     FDATE VARCHAR(50), ");
        sbCreate.append("primary key(airline_code, flight_num, FDATE),");
		sbCreate.append("foreign key(airline_code,flight_num) references Flight(airline_code,flight_num),");
		sbCreate.append("foreign key(FDATE) references Flight_Date(FDATE)");
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
    public FlightInstance[] loadAll() throws SQLException
    {
        // get the connection
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT airline_code, flight_num, FDATE  FROM FLIGHT_INSTANCE");

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
                	FlightInstance flightInstance = new FlightInstance();
                	flightInstance.setAirlineCode (rs.getString("airline_code"));
                	flightInstance.setFlightNumber(rs.getString("flight_num"));
                	flightInstance.setFlightDate(rs.getString("FDATE"));
                	

                    // store it in the list
                    collection.add(flightInstance);
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
        return (FlightInstance[])collection.toArray(new FlightInstance[0]);
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
        sbInsert.append(" INSERT INTO FLIGHT_INSTANCE ");
        sbInsert.append(" VALUES ");
        

        
        sbInsert.append(" ('" + airlineCode + "', '" + flightNumber + "', '" + flightDate + "')");
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
    
    public static void insertRecord(String InsertCode, String InsertFnum, String InsertFD){
     System.out.println("Insert FlightInstance " + count);
     FlightInstance flightInstanceTB = new FlightInstance();

     flightInstanceTB.setAirlineCode (InsertCode);
     flightInstanceTB.setFlightNumber(InsertFnum);
     flightInstanceTB.setFlightDate(InsertFD);
     count = count + 1;

     try
     {
    	 flightInstanceTB.insertData();
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
    	FlightInstance flightInstanceTB = new FlightInstance();    //change
    	FlightInstance [] db;                           //change
        try {
            db = flightInstanceTB.loadAll();    //change
            System.out.println("\nFlightInstance");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
            	FlightInstance mdb = db[i];
                System.out.println(mdb.getAirlineCode()+ " " + mdb.getFlightNumber()+ " " + mdb.getFlightDate());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }
    

    
	
}
