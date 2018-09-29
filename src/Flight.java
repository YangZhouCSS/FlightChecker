
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Flight {
    private String airlineCode;
    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String originAirport;
    private String destinationAirport;

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

    public String getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(String departureTime) {
    	
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOriginAirport() {
        return originAirport;
    }
    public void setOriginAirport(String originAirport) {
        this.originAirport = originAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }
    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }
    
    
	/** Method to check if a table exists
	 * @param connection java.sql.Connection object
	 * @return true is the table exists, false otherwise
	 * @throws SQLException
	 */
    private boolean doesTableExist(Connection connection) throws SQLException {
        boolean bTableExists = false;

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet rs = dmd.getTables (null, null, "FLIGHT", null);
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
        sbCreate.append(" CREATE TABLE FLIGHT ");
        sbCreate.append(" ( ");
        sbCreate.append("     airline_code VARCHAR(20), ");
        sbCreate.append("     flight_num VARCHAR(20), ");
        sbCreate.append("     departure_time VARCHAR(50), ");
        sbCreate.append("     arrival_time VARCHAR(50), ");
        sbCreate.append("     duration VARCHAR(20), ");
        sbCreate.append("     origin_airport VARCHAR(20), ");
        sbCreate.append("     destination_airport VARCHAR(20), ");
        sbCreate.append("primary key(airline_code, flight_num),");
        sbCreate.append("foreign key(airline_code) references Airline(airline_code)");
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
    public Flight[] loadAll() throws SQLException
    {
        // get the connection
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT airline_code, flight_num, departure_time, arrival_time, duration, origin_airport, destination_airport  FROM FLIGHT");

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
                	Flight flight = new Flight();
                	flight.setAirlineCode (rs.getString("airline_code"));
                	flight.setFlightNumber(rs.getString("flight_num"));
                	flight.setDepartureTime(rs.getString("departure_time"));
                	flight.setArrivalTime(rs.getString("arrival_time"));
                	flight.setDuration(rs.getString("duration"));
                	flight.setOriginAirport(rs.getString("origin_airport"));
                	flight.setDestinationAirport(rs.getString("destination_airport"));
                	

                    // store it in the list
                    collection.add(flight);
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
        return (Flight[])collection.toArray(new Flight[0]);
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
        sbInsert.append(" INSERT INTO FLIGHT ");
        sbInsert.append(" VALUES ");
        

        
        sbInsert.append(" ('" + airlineCode + "', '" + flightNumber + "', '" + departureTime + "', '" + arrivalTime + "', '" + duration +  "', '" + originAirport + "', '" + destinationAirport + "')");
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
    
    public static void insertRecord(String InsertCode, String InsertFnum, String InsertDT, String InsertAT, String InsertDuration, String InsertOA, String InsertDA){
     System.out.println("Insert Flight " + count);
     Flight flightTB = new Flight();

     flightTB.setAirlineCode (InsertCode);
     flightTB.setFlightNumber(InsertFnum);
     flightTB.setDepartureTime(InsertDT);
     flightTB.setArrivalTime(InsertAT);
     flightTB.setDuration(InsertDuration);
     flightTB.setOriginAirport(InsertOA);
     flightTB.setDestinationAirport(InsertDA);
     count = count + 1;

     try
     {
    	 flightTB.insertData();
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
    	Flight flightTB = new Flight();    //change
    	Flight [] db;                           //change
        try {
            db = flightTB.loadAll();    //change
            System.out.println("\nFlight");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
            	Flight mdb = db[i];
                System.out.println(mdb.getAirlineCode()+ " " + mdb.getFlightNumber()+ " " + mdb.getDepartureTime() + " " + mdb.getArrivalTime() + " "+ mdb.getDuration() + " "+ mdb.getOriginAirport()+ " "+ mdb.getDestinationAirport());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }
}
