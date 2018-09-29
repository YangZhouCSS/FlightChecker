//Author: Yang Zhou


import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Add {

	public static void adding(String tableName) {
		
		
		if (tableName == "Alliance") {
			//Alliance allianceTB = new Alliance();
			String insertR = JOptionPane.showInputDialog("Type in your record:") ;
			Alliance.insertRecord(insertR);
		
		}
		
		if (tableName == "Airline") {
			//Create and populate the panel.
		      JTextField airlineCodeText = new JTextField(5);
		      JTextField airlineNameText = new JTextField(5);
		      JTextField countryText = new JTextField(5);
			  JTextField allianceNameText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("airline_code:"));
		      myPanel.add(airlineCodeText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("airline_name:"));
		      myPanel.add(airlineNameText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("country:"));
		      myPanel.add(countryText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
			  myPanel.add(new JLabel("alliance_name:"));
		      myPanel.add(allianceNameText);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = airlineCodeText.getText();
		      String insertB = airlineNameText.getText();
		      String insertC = countryText.getText();
			  String insertD = allianceNameText.getText();
		      
		      //if (result == JOptionPane.OK_OPTION) {
		         //System.out.println("x value: " + xField.getText());
		         //System.out.println("y value: " + yField.getText());
		         //System.out.println(insertZ);
		      //}

			Airline.insertRecord(insertA, insertB, insertC, insertD);
		}
		
		if (tableName == "Airport") {
			//Create and populate the panel.
		      JTextField airportCodeText = new JTextField(5);
		      JTextField cityText = new JTextField(5);
		      JTextField countryText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("airport_code:"));
		      myPanel.add(airportCodeText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("city:"));
		      myPanel.add(cityText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("country:"));
		      myPanel.add(countryText);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = airportCodeText.getText();
		      String insertB = cityText.getText();
		      String insertC = countryText.getText();
			  
			  Airport.insertRecord(insertA, insertB, insertC);

		}
		
			if (tableName == "Flight") {
			//Create and populate the panel.
		      JTextField airlineCodeText = new JTextField(5);
		      JTextField flightNumberText = new JTextField(5);
		      JTextField departureTimeText = new JTextField(5);
			  JTextField arrivalTimeText = new JTextField(5);
			  JTextField durationText = new JTextField(5);
			  JTextField originText = new JTextField(5);
			  JTextField destinationText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("airline_code:"));
		      myPanel.add(airlineCodeText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("flight_number:"));
		      myPanel.add(flightNumberText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("departure_time:"));
		      myPanel.add(departureTimeText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("arrival_time:"));
		      myPanel.add(arrivalTimeText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("duration:"));
		      myPanel.add(durationText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("origin_airport:"));
		      myPanel.add(originText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("destination_airport:"));
		      myPanel.add(destinationText);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = airlineCodeText.getText();
		      String insertB = flightNumberText.getText();
		      String insertC = departureTimeText.getText();
			  String insertD = arrivalTimeText.getText();
			  String insertE = durationText.getText();
			  String insertF = originText.getText();
			  String insertG = destinationText.getText();
			  
			  Flight.insertRecord(insertA, insertB, insertC, insertD, insertE, insertF, insertG);

		}
		
			if (tableName == "Flight_Date") {
			//Create and populate the panel.
		      JTextField flightDateText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("flight_date:"));
		      myPanel.add(flightDateText);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = flightDateText.getText();
			  
			  FlightDate.insertRecord(insertA);

		}
		
			if (tableName == "Flight_Instance") {
			//Create and populate the panel.
		      JTextField airlineCodeText = new JTextField(5);
		      JTextField flightNumberText = new JTextField(5);
			  JTextField flightDateText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("airline_code:"));
		      myPanel.add(airlineCodeText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("flight_number:"));
		      myPanel.add(flightNumberText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("flight_date:"));
		      myPanel.add(flightDateText);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = airlineCodeText.getText();
		      String insertB = flightNumberText.getText();
		      String insertC = flightDateText.getText();
			  
			  FlightInstance.insertRecord(insertA, insertB, insertC);

		}
		
			if (tableName == "Aircraft") {
			//Create and populate the panel.
		      JTextField manufacturerText = new JTextField(5);
		      JTextField modelText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("manufacturer:"));
		      myPanel.add(manufacturerText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("model:"));
		      myPanel.add(modelText);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = manufacturerText.getText();
		      String insertB = modelText.getText();
			  
			  Aircraft.insertRecord(insertA, insertB);

		}
		
		
		if (tableName == "Flight_Aircraft") {
			//Create and populate the panel.
		      JTextField airlineCodeText = new JTextField(5);
		      JTextField flightNumberText = new JTextField(5);
			  JTextField flightDateText = new JTextField(5);
			  JTextField manufacturerText = new JTextField(5);
		      JTextField modelText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("airline_code:"));
		      myPanel.add(airlineCodeText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("flight_number:"));
		      myPanel.add(flightNumberText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("flight_date:"));
		      myPanel.add(flightDateText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
			  myPanel.add(new JLabel("manufacturer:"));
		      myPanel.add(manufacturerText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("model:"));
		      myPanel.add(modelText);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = airlineCodeText.getText();
		      String insertB = flightNumberText.getText();
		      String insertC = flightDateText.getText();
			  String insertD = manufacturerText.getText();
			  String insertE = modelText.getText();
			  
			  FlightAircraft.insertRecord(insertA, insertB, insertC, insertD, insertE);

		}
		
		if (tableName == "Class") {
			//Create and populate the panel.
		      JTextField classText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("class:"));
		      myPanel.add(classText);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = classText.getText();
			  
			  SeatClass.insertRecord(insertA);

		}
		
		if (tableName == "Aircraft_Class") {
			//Create and populate the panel.
		      JTextField manufacturerText = new JTextField(5);
		      JTextField modelText = new JTextField(5);
			  JTextField classText = new JTextField(5);
			  JTextField capacityText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("manufacturer:"));
		      myPanel.add(manufacturerText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("model:"));
		      myPanel.add(modelText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
			  myPanel.add(new JLabel("class:"));
		      myPanel.add(classText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
			  myPanel.add(new JLabel("capacity:"));
		      myPanel.add(capacityText);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = manufacturerText.getText();
		      String insertB = modelText.getText();
			  String insertC = classText.getText();
			  String insertD = capacityText.getText();
			  
			  Integer intInsertD = Integer.valueOf(insertD);
			  AircraftClass.insertRecord(insertA, insertB, insertC, intInsertD);

		}
		
		if (tableName == "Fare") {
			//Create and populate the panel.
		      JTextField airlineCodeText = new JTextField(5);
		      JTextField flightNumberText = new JTextField(5);
			  JTextField flightDateText = new JTextField(5);
			  JTextField classText = new JTextField(5);
			  JTextField priceText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("airline_code:"));
		      myPanel.add(airlineCodeText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("flight_number:"));
		      myPanel.add(flightNumberText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("flight_date:"));
		      myPanel.add(flightDateText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
			  myPanel.add(new JLabel("class:"));
		      myPanel.add(classText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
			  myPanel.add(new JLabel("price:"));
		      myPanel.add(priceText);
			  

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = airlineCodeText.getText();
		      String insertB = flightNumberText.getText();
		      String insertC = flightDateText.getText();
			  String insertD = classText.getText();
			  String insertE = priceText.getText();
			  
			  Double doubleInsertE = Double.valueOf(insertE);
			  
			  Fare.insertRecord(insertA, insertB, insertC, insertD, doubleInsertE);

		}
		
		if (tableName == "Seat") {
			//Create and populate the panel.
		      JTextField airlineCodeText = new JTextField(5);
		      JTextField flightNumberText = new JTextField(5);
			  JTextField flightDateText = new JTextField(5);
			  JTextField seatNumberText = new JTextField(5);			  
			  JTextField classText = new JTextField(5);
			  JTextField availabilityText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("airline_code:"));
		      myPanel.add(airlineCodeText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("flight_number:"));
		      myPanel.add(flightNumberText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("flight_date:"));
		      myPanel.add(flightDateText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("seat_number:"));
		      myPanel.add(seatNumberText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
			  myPanel.add(new JLabel("class:"));
		      myPanel.add(classText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
			  myPanel.add(new JLabel("availability:"));
		      myPanel.add(availabilityText);
			  

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = airlineCodeText.getText();
		      String insertB = flightNumberText.getText();
		      String insertC = flightDateText.getText();
			  String insertD = seatNumberText.getText();
			  String insertE = classText.getText();
			  String insertF = availabilityText.getText();
			  
			  Seat.insertRecord(insertA, insertB, insertC, insertD, insertE, insertF);

		}
		
				if (tableName == "Customer") {
			//Create and populate the panel.
		      JTextField customerIDText = new JTextField(5);
		      JTextField nameText = new JTextField(5);
			  JTextField ageText = new JTextField(5);
			  JTextField genderText = new JTextField(5);

		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("customer_ID:"));
		      myPanel.add(customerIDText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("name:"));
		      myPanel.add(nameText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("age:"));
		      myPanel.add(ageText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("gender:"));
		      myPanel.add(genderText);
			  

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = customerIDText.getText();
		      String insertB = nameText.getText();
		      String insertC = ageText.getText();
			  String insertD = genderText.getText();
			  
			  Integer intInsertC = Integer.valueOf(insertC);

			  Customer.insertRecord(insertA, insertB, intInsertC, insertD);

		}
		
		if (tableName == "Ticket") {
			//Create and populate the panel.
			  JTextField ticketNumberText = new JTextField(5);
			  JTextField customerIDText = new JTextField(5);
		      JTextField airlineCodeText = new JTextField(5);
		      JTextField flightNumberText = new JTextField(5);
			  JTextField flightDateText = new JTextField(5);
			  JTextField seatNumberText = new JTextField(5);

		      JPanel myPanel = new JPanel();
			  myPanel.add(new JLabel("ticket_number:"));
		      myPanel.add(ticketNumberText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
			  myPanel.add(new JLabel("customer_ID:"));
		      myPanel.add(customerIDText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("airline_code:"));
		      myPanel.add(airlineCodeText);
		      myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("flight_number:"));
		      myPanel.add(flightNumberText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("flight_date:"));
		      myPanel.add(flightDateText);
			  myPanel.add(Box.createVerticalStrut(15)); // a spacer
		      myPanel.add(new JLabel("seat_number:"));
		      myPanel.add(seatNumberText);
			  

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
		      
		      String insertA = ticketNumberText.getText();
			  String insertB = customerIDText.getText();
			  String insertC = airlineCodeText.getText();
		      String insertD = flightNumberText.getText();
		      String insertE = flightDateText.getText();
			  String insertF = seatNumberText.getText();
			  
			  Ticket.insertRecord(insertA, insertB, insertC, insertD, insertE, insertF);

		}
	}
	
	
}