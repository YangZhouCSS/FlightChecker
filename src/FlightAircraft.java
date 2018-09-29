
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FlightAircraft {
    private String airlineCode;
    private String flightNumber;
    private String flightDate;
    private String manufacturer;
    private String model;

    public String getAirlineCode() {
        return airlineCode;
    }
    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String flightNumber() {
    	return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public String getFlightNumber() {
    	return flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }
    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    
    
	/** Method to check if a table exists
 * @param connection java.sql.Connection object
 * @return true is the table exists, false otherwise
 * @throws SQLException
 */
private boolean doesTableExist(Connection connection) throws SQLException {
    boolean bTableExists = false;

    DatabaseMetaData dmd = connection.getMetaData();
    ResultSet rs = dmd.getTables (null, null, "FLIGHT_AIRCRAFT", null);
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
    sbCreate.append(" CREATE TABLE FLIGHT_AIRCRAFT ");
    sbCreate.append(" ( ");
    sbCreate.append("     airline_code VARCHAR(20), ");
    sbCreate.append("     flight_num VARCHAR(20), ");
    sbCreate.append("     FDATE VARCHAR(50), ");
    sbCreate.append("     manufacturer VARCHAR(50), ");
    sbCreate.append("     model VARCHAR(50), ");
    sbCreate.append("primary key(airline_code, flight_num, FDATE),");
	sbCreate.append("foreign key(airline_code,flight_num,FDATE) references Flight_Instance(airline_code,flight_num,FDATE),");
	sbCreate.append("foreign key(manufacturer, model) references Aircraft(manufacturer, model)");
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
public FlightAircraft[] loadAll() throws SQLException
{
    // get the connection
	Connect connect = new Connect();
    Connection connection = connect.getConnection();

    // create the SELECT SQL
    StringBuffer sbSelect = new StringBuffer();
    sbSelect.append(" SELECT airline_code,flight_num,FDATE,manufacturer, model  FROM FLIGHT_AIRCRAFT");

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
            	FlightAircraft flightAircraft = new FlightAircraft();
            	flightAircraft.setAirlineCode (rs.getString("airline_code"));
            	flightAircraft.setFlightNumber(rs.getString("flight_num"));
            	flightAircraft.setFlightDate(rs.getString("FDATE"));
            	flightAircraft.setManufacturer(rs.getString("manufacturer"));
            	flightAircraft.setModel(rs.getString("model"));
            	

                // store it in the list
                collection.add(flightAircraft);
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
    return (FlightAircraft[])collection.toArray(new FlightAircraft[0]);
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
    sbInsert.append(" INSERT INTO FLIGHT_AIRCRAFT ");
    sbInsert.append(" VALUES ");
    

    
    sbInsert.append(" ('" + airlineCode + "', '" + flightNumber + "', '" + flightDate + "', '" + manufacturer +   "', '" + model + "')");
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

public static void insertRecord(String InsertCode, String InsertFnum, String InsertDate, String InsertManf, String InsertModel){
 System.out.println("Insert FlightAircraft " + count);
 FlightAircraft flightAircraftTB = new FlightAircraft();

 flightAircraftTB.setAirlineCode (InsertCode);
 flightAircraftTB.setFlightNumber(InsertFnum);
 flightAircraftTB.setFlightDate(InsertDate);
 flightAircraftTB.setManufacturer(InsertManf);
 flightAircraftTB.setModel(InsertModel);
 count = count + 1;

 try
 {
	 flightAircraftTB.insertData();
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
	FlightAircraft flightAircraftTB = new FlightAircraft();    //change
	FlightAircraft [] db;                           //change
    try {
        db = flightAircraftTB.loadAll();    //change
        System.out.println("\nFlightAircraft");    //change
        System.out.println("--------");
        System.out.println(db.length);
        for (int i = 0; i <db.length; i++) {
        	FlightAircraft mdb = db[i];
            System.out.println(mdb.getAirlineCode()+ " " + mdb.getFlightNumber()+ " " + mdb.getFlightDate()+ " " + mdb.getManufacturer() + " " + mdb.getModel());   //change
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
	

}

}

