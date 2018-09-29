
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Fare {
    private String airlineCode;
    private String flightNumber;
    private String flightDate;
    private String className;
    private double price;

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

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    
    
	/** Method to check if a table exists
 * @param connection java.sql.Connection object
 * @return true is the table exists, false otherwise
 * @throws SQLException
 */
private boolean doesTableExist(Connection connection) throws SQLException {
    boolean bTableExists = false;

    DatabaseMetaData dmd = connection.getMetaData();
    ResultSet rs = dmd.getTables (null, null, "FARE", null);
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
    sbCreate.append(" CREATE TABLE FARE ");
    sbCreate.append(" ( ");
    sbCreate.append("     airline_code VARCHAR(20), ");
    sbCreate.append("     flight_num VARCHAR(20), ");
    sbCreate.append("     FDATE VARCHAR(50), ");
    sbCreate.append("     CLASS_NAME VARCHAR(20), ");
    sbCreate.append("     PRICE decimal(30,2), ");
    sbCreate.append("primary key(airline_code, flight_num, FDATE, class_name),");
	sbCreate.append("foreign key(airline_code,flight_num,FDATE) references Flight_Instance(airline_code,flight_num,FDATE),");
	sbCreate.append("foreign key(class_name) references Class(class_name)");
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
public Fare[] loadAll() throws SQLException
{
    // get the connection
	Connect connect = new Connect();
    Connection connection = connect.getConnection();

    // create the SELECT SQL
    StringBuffer sbSelect = new StringBuffer();
    sbSelect.append(" SELECT airline_code, flight_num, FDATE, class_name, price  FROM Fare");

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
            	Fare fare = new Fare();
            	fare.setAirlineCode (rs.getString("airline_code"));
            	fare.setFlightNumber(rs.getString("flight_num"));
            	fare.setFlightDate(rs.getString("FDATE"));
            	fare.setClassName(rs.getString("class_name"));
            	fare.setPrice(rs.getDouble("price"));
            	

                // store it in the list
                collection.add(fare);
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
    return (Fare[])collection.toArray(new Fare[0]);
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
    sbInsert.append(" INSERT INTO FARE ");
    sbInsert.append(" VALUES ");
    

    
    sbInsert.append(" ('" + airlineCode + "', '" + flightNumber + "', '" + flightDate + "', '" + className + "', '" + price + "')");
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

public static void insertRecord(String InsertCode, String InsertFnum, String InsertFD, String InsertClassName, double InsertPrice){
 System.out.println("Insert Fare " + count);
 Fare fareTB = new Fare();

 fareTB.setAirlineCode (InsertCode);
 fareTB.setFlightNumber(InsertFnum);
 fareTB.setFlightDate(InsertFD);
 fareTB.setClassName(InsertClassName);
 fareTB.setPrice(InsertPrice);
 count = count + 1;

 try
 {
	 fareTB.insertData();
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
	Fare fareTB = new Fare();    //change
	Fare [] db;                           //change
    try {
        db = fareTB.loadAll();    //change
        System.out.println("\nFare");    //change
        System.out.println("--------");
        System.out.println(db.length);
        for (int i = 0; i <db.length; i++) {
        	Fare mdb = db[i];
            System.out.println(mdb.getAirlineCode()+ " " + mdb.getFlightNumber()+ " " + mdb.getFlightDate()+ " " + mdb.getClassName()+ " " + mdb.getPrice());
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
	

}

}
