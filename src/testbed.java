
//JDBC libraries
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class testbed {

    // DB connection properties
    public String driver = "oracle.jdbc.driver.OracleDriver";
    public String jdbc_url = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";

    // longin info
    public static String username = " ";
    public static String password = " ";
    

    public static void main (String arg[])
    {
	    //prompt user for password
		username = JOptionPane.showInputDialog("username:") ;
		password = JOptionPane.showInputDialog("password:") ;

    	// create and insert records for Alliance
    	Alliance allianceTB = new Alliance();
        Alliance.insertRecord("Aisan Airlines");
        Alliance.insertRecord("US Airlines");
        
        
    	// create and insert record for Aircraft
        Aircraft aircraftTB = new Aircraft();
        Aircraft.insertRecord("Boeing","737");
        Aircraft.insertRecord("Boeing","777");
        Aircraft.insertRecord("Airbus","380");
        
        //create and insert record for SeatClass
        SeatClass.insertRecord("Economic");
        SeatClass.insertRecord("Business");
        
        //create and insert record for Airport_class
        AircraftClass.insertRecord("Airbus","380","Economic",50);
        AircraftClass.insertRecord("Airbus","380","Business",10);
        AircraftClass.insertRecord("Boeing","737","Economic",80);
        AircraftClass.insertRecord("Boeing","737","Business",20);
        AircraftClass.insertRecord("Boeing","777","Economic",60);
        AircraftClass.insertRecord("Boeing","777","Business",15);      
        
        
        //create and insert record for Airline
        Airline.insertRecord("AA","American Airlines","United States","US Airlines");  
        Airline.insertRecord("UA","United Airlines","United States","US Airlines");  
        
        
        //create and insert record for Airport
        Airport.insertRecord("IAD","Washington DC","USA");
        Airport.insertRecord("LAX","Los Angeles","USA");
        
        
        //create and insert record for Flight
        Flight.insertRecord("AA","1023","2017-04-02 18:27:05","2017-04-02 22:27:05","4.0 hours", "IAD","LAX");
        Flight.insertRecord("AA","1024","2017-04-03 18:27:05","2017-04-03 22:27:05","4.0 hours", "IAD","LAX");
        
        //create and insert record for Customer
        Customer.insertRecord("UA12345", "Bob Brown", 20, "Male");
        Customer.insertRecord("D1253", "Lucy Smith", 35, "Female");
        Customer.insertRecord("D1254", "David Smith", 36, "Male");
        
        
        
        //create and insert record for FlightDate
        FlightDate.insertRecord("2017-04-02");
        FlightDate.insertRecord("2017-04-03");
        
        //create an insert record for FlightInstance
        FlightInstance.insertRecord("AA", "1023", "2017-04-02");
        FlightInstance.insertRecord("AA", "1024", "2017-04-03");
        
        //create an insert record for FlightAircraft
        FlightAircraft.insertRecord("AA", "1024", "2017-04-03", "Airbus","380");
        FlightAircraft.insertRecord("AA", "1023", "2017-04-02", "Boeing","737");
        
        //create an insert record for Seat
        Seat.insertRecord("AA", "1024", "2017-04-03", "A1", "Business", "0");
        Seat.insertRecord("AA", "1024", "2017-04-03", "A2", "Business", "1");
        Seat.insertRecord("AA", "1024", "2017-04-03", "A3", "Business", "1");
        Seat.insertRecord("AA", "1024", "2017-04-03", "A4", "Business", "0");
        
        Seat.insertRecord("AA", "1023", "2017-04-02", "A1", "Business", "1");
        Seat.insertRecord("AA", "1023", "2017-04-02", "A2", "Business", "1");
        Seat.insertRecord("AA", "1023", "2017-04-02", "A3", "Business", "1");
        Seat.insertRecord("AA", "1023", "2017-04-02", "A4", "Business", "1");
        
        //create an insert record for Fare
        Fare.insertRecord("AA", "1024", "2017-04-03", "Business", 200.00);
        Fare.insertRecord("AA", "1024", "2017-04-03", "Economic", 150.00);
        Fare.insertRecord("AA", "1023", "2017-04-02", "Economic", 155.00);
        Fare.insertRecord("AA", "1023", "2017-04-02", "Business", 220.00);
        
        //create an insert record for Ticket
        Ticket.insertRecord("T001", "UA12345", "AA", "1024", "2017-04-03", "A4");
        Ticket.insertRecord("T002", "D1253", "AA", "1024", "2017-04-03", "A1");
        
        
        //insert trigger Check ticket
    	// get the connection
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        // create the DELETE SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append("     CREATE OR REPLACE TRIGGER CheckTicket     ");
        sbSelect.append("     BEFORE INSERT ON Ticket   ");
        sbSelect.append("     FOR EACH ROW   ");
        sbSelect.append("     DECLARE   ");
        sbSelect.append("           res_count       INTEGER;   ");
        sbSelect.append("           Too_many        Exception;");
        sbSelect.append("           BEGIN                                    ");
        sbSelect.append("           SELECT COUNT(*) INTO res_count ");
        sbSelect.append("           FROM Ticket    ");
        sbSelect.append("           WHERE airline_code = :NEW.airline_code and flight_num = :NEW.flight_num and seat_number = :NEW.seat_number ;  ");
        sbSelect.append("           IF res_count > 0 THEN");
        sbSelect.append("           RAISE Too_many;");
        sbSelect.append("           END IF; ");
        sbSelect.append("           EXCEPTION");
        sbSelect.append("           WHEN Too_many THEN");
        sbSelect.append("           Raise_application_error(-20001, 'Seat taken!');  ");
        sbSelect.append("           END;");
        
        System.out.print("Inserting trigger");
        
        

        Statement statement = null;
        ResultSet rs = null;
        ArrayList collection = new ArrayList();
        
        //System.out.println(sbSelect);

            // create the statement
            try {
				statement = connection.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            // Insert the data
            try {
				rs = statement.executeQuery(sbSelect.toString());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

        
        
     // List the records
        
        Alliance.PrintOut();
        Aircraft.PrintOut();
        AircraftClass.PrintOut();
        Airline.PrintOut();
        Airport.PrintOut();
        Flight.PrintOut();
        Customer.PrintOut();
        SeatClass.PrintOut();
        FlightDate.PrintOut();
        FlightInstance.PrintOut();
        FlightAircraft.PrintOut();
        Seat.PrintOut();
        Fare.PrintOut();
        Ticket.PrintOut();

    	
    }
}
