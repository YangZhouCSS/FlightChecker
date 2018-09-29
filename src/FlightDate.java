
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FlightDate {
    private String flightDate;

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
        ResultSet rs = dmd.getTables (null, null, "FLIGHT_DATE", null);
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
        sbCreate.append(" CREATE TABLE FLIGHT_DATE ");
        sbCreate.append(" ( ");
        sbCreate.append("     FDATE VARCHAR(20), ");
        sbCreate.append("primary key(FDATE)");
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
    public FlightDate[] loadAll() throws SQLException
    {
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT FDATE FROM FLIGHT_DATE");

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
                    FlightDate flightdate = new FlightDate();
                    flightdate.setFlightDate(rs.getString("FDATE"));

                    // store it in the list
                    collection.add(flightdate);
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
        return (FlightDate[])collection.toArray(new FlightDate[0]);
    }

    /**insert data into table
     * @throws SQLException
     */
    public void insertData () throws SQLException
    {	
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        if (!this.doesTableExist(connection))
        {
            System.out.println("FlightDate Table doesn't exist. Creating it.....");
            createTable(connection);
        }

        StringBuffer sbInsert = new StringBuffer();
        sbInsert.append(" INSERT INTO FLIGHT_DATE  ");
        sbInsert.append(" VALUES ");
        sbInsert.append(" ('" + flightDate + "') ");

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
   
   public static void insertRecord(String InsertName){
    System.out.println("Insert Flight_Date " + count);
    FlightDate flightDateTB = new FlightDate();
    flightDateTB.setFlightDate(InsertName);
    count = count + 1;

    try
    {
        flightDateTB.insertData();
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
    	FlightDate flightDateTB = new FlightDate();    //change
        FlightDate[] db;                           //change
        try {
            db = flightDateTB.loadAll();
            System.out.println("\nFlight_Date");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
                FlightDate mdb = db[i];
                System.out.println(mdb.getFlightDate());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }
	
}