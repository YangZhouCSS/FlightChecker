
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Ticket
{
    private String ticketNumber;
    private String customerID;
    private String airlineCode;
    private String flightNumber;
    private String flightDate;
    private String seatNumber;

    public String getTicketNumber(){
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber){
        this.ticketNumber = ticketNumber;
    }

    public String getCustomerID(){
        return customerID;
    }

    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }

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
    

	/** Method to check if a table exists
	 * @param connection java.sql.Connection object
	 * @return true is the table exists, false otherwise
	 * @throws SQLException
	 */
    private boolean doesTableExist(Connection connection) throws SQLException {
        boolean bTableExists = false;

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet rs = dmd.getTables (null, null, "TICKET", null);
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
        sbCreate.append(" CREATE TABLE TICKET ");
        sbCreate.append(" ( ");
		sbCreate.append("     ticket_number VARCHAR(20), ");
		sbCreate.append("     customer_ID VARCHAR(20), ");
        sbCreate.append("     airline_code VARCHAR(20), ");
        sbCreate.append("     flight_num VARCHAR(20), ");
        sbCreate.append("     FDATE VARCHAR(50), ");
		sbCreate.append("     seat_number VARCHAR(20), ");
        sbCreate.append("primary key(ticket_number),");
		sbCreate.append("foreign key(customer_ID) references Customer(customer_ID),");   
		sbCreate.append("foreign key(airline_code,flight_num, FDATE, seat_number) references Seat(airline_code,flight_num, FDATE, seat_number)");
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
    public Ticket[] loadAll() throws SQLException
    {
        // get the connection
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT ticket_number, customer_ID, airline_code, flight_num, FDATE, seat_number  FROM TICKET");

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
                	Ticket ticket = new Ticket();
					
					ticket.setTicketNumber (rs.getString("ticket_number"));
					ticket.setCustomerID (rs.getString("customer_ID"));
                	ticket.setAirlineCode (rs.getString("airline_code"));
                	ticket.setFlightNumber(rs.getString("flight_num"));
                	ticket.setFlightDate(rs.getString("FDATE"));
					ticket.setSeatNumber(rs.getString("seat_number"));
                	

                    // store it in the list
                    collection.add(ticket);
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
        return (Ticket[])collection.toArray(new Ticket[0]);
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
        sbInsert.append(" INSERT INTO TICKET ");
        sbInsert.append(" VALUES ");
        
        sbInsert.append(" ('" + ticketNumber + "', '" + customerID + "', '" + airlineCode + "', '" + flightNumber + "', '" + flightDate + "', '" + seatNumber + "')");
        // create the statement
        
        //System.out.println(sbInsert);
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
    
    public static void insertRecord(String InsertTNum, String InsertCustID, String InsertCode, String InsertFnum, String InsertFD, String InsertSeatNum){
     System.out.println("Insert Ticket " + count);
     Ticket ticketTB = new Ticket();

	 ticketTB.setTicketNumber(InsertTNum);
	 ticketTB.setCustomerID(InsertCustID);
     ticketTB.setAirlineCode (InsertCode);
     ticketTB.setFlightNumber(InsertFnum);
     ticketTB.setFlightDate(InsertFD);
	 ticketTB.setSeatNumber(InsertSeatNum);
	 
     count = count + 1;

     try
     {
    	 ticketTB.insertData();
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
    	Ticket ticketTB = new Ticket();    //change
    	Ticket [] db;                           //change
        try {
            db = ticketTB.loadAll();    //change
            System.out.println("\nTicket");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
            	Ticket mdb = db[i];
                System.out.println(mdb.getTicketNumber() + " " + mdb.getCustomerID() + " " + mdb.getAirlineCode() + " " + mdb.getFlightNumber() + " " + mdb.getFlightDate() + " " + mdb.getSeatNumber());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }
	
}