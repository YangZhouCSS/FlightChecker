
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Seat
{
    private String airlineCode;
    private String flightNumber;
    private String flightDate;
    private String seatNumber;
    private String className;
    private String availability;   //1 for available, 0 for unavailable

    public String getAirlineCode(){
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode){
        this.airlineCode = airlineCode;
    }

    public String getFlightNumber(){
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber){
        this.flightNumber = flightNumber;
    }

    public String getFlightDate(){
        return flightDate;
    }

    public void setFlightDate(String flightDate){
        this.flightDate = flightDate;
    }

    public String getSeatNumber(){
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber){
        this.seatNumber = seatNumber;
    }

    public String getClassName(){
        return className;
    }

    public void setClassName(String className){
        this.className = className;
    }

    public String getAvailability(){
        return availability;
    }

    public void setAvailability(String availability){
        this.availability = availability;
    }

	/** Method to check if a table exists
	 * @param connection java.sql.Connection object
	 * @return true is the table exists, false otherwise
	 * @throws SQLException
	 */
    private boolean doesTableExist(Connection connection) throws SQLException {
        boolean bTableExists = false;

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet rs = dmd.getTables (null, null, "SEAT", null);
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
        sbCreate.append(" CREATE TABLE SEAT ");
        sbCreate.append(" ( ");
        sbCreate.append("     airline_code VARCHAR(20), ");
        sbCreate.append("     flight_num VARCHAR(20), ");
        sbCreate.append("     FDATE VARCHAR(50), ");
		sbCreate.append("     seat_number VARCHAR(20), ");
		sbCreate.append("     class_name VARCHAR(20), ");
		sbCreate.append("     availability VARCHAR(1), ");
        sbCreate.append("primary key(airline_code, flight_num, FDATE, seat_number),");
		sbCreate.append("foreign key(airline_code,flight_num, FDATE) references Flight_Instance(airline_code,flight_num, FDATE),");
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
    public Seat[] loadAll() throws SQLException
    {
        // get the connection
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT airline_code, flight_num, FDATE, seat_number,class_name, availability  FROM SEAT");

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
                	Seat seat = new Seat();
                	seat.setAirlineCode (rs.getString("airline_code"));
                	seat.setFlightNumber(rs.getString("flight_num"));
                	seat.setFlightDate(rs.getString("FDATE"));
					seat.setSeatNumber(rs.getString("seat_number"));
					seat.setClassName(rs.getString("class_name"));
					seat.setAvailability(rs.getString("availability"));
                	

                    // store it in the list
                    collection.add(seat);
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
        return (Seat[])collection.toArray(new Seat[0]);
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
        sbInsert.append(" INSERT INTO SEAT ");
        sbInsert.append(" VALUES ");
        

        
        sbInsert.append(" ('" + airlineCode + "', '" + flightNumber + "', '" + flightDate + "', '" + seatNumber + "', '" + className + "', '" + availability + "')");
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
    
    public static void insertRecord(String InsertCode, String InsertFnum, String InsertFD, String InsertSeatNum, String InsertClassName, String InsertAvail){
     System.out.println("Insert Seat " + count);
     Seat seatTB = new Seat();

     seatTB.setAirlineCode (InsertCode);
     seatTB.setFlightNumber(InsertFnum);
     seatTB.setFlightDate(InsertFD);
	 seatTB.setSeatNumber(InsertSeatNum);
	 seatTB.setClassName(InsertClassName);
	 seatTB.setAvailability(InsertAvail);
	 
     count = count + 1;

     try
     {
    	 seatTB.insertData();
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
    	Seat seatTB = new Seat();    //change
    	Seat [] db;                           //change
        try {
            db = seatTB.loadAll();    //change
            System.out.println("\nSeat");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
            	Seat mdb = db[i];
                System.out.println(mdb.getAirlineCode() + " " + mdb.getFlightNumber() + " " + mdb.getFlightDate() + " " + mdb.getSeatNumber() + " " + mdb.getClassName() + " " + mdb.getAvailability());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }
	
}