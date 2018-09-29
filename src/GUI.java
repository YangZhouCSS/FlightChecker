//Author: Yang Zhou

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {
	
	public String driver = "oracle.jdbc.driver.OracleDriver";
    public String jdbc_url = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";
    
    public static String username = " ";
    public static String password = " ";
    


	private JFrame frame;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.setSize(600,500);
		//frame.pack();
		
		JButton btnAddrecords = new JButton("Add_records");
		btnAddrecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				

				
				Object[] possibleValues = { "Alliance", "Airline","Airport","Flight","Flight_Date","Flight_Instance","Aircraft","Class","Aircraft_Class","Fare","Seat","Customer","Ticket"};
				String TableName = (String)JOptionPane.showInputDialog(null,
				"Which table to insert into?","Dialog",
				JOptionPane.INFORMATION_MESSAGE, null,
				possibleValues, possibleValues[0]);
				
				Add addRecord = new Add();
				Add.adding(TableName);
				
				
				
			}
		});
		btnAddrecords.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddrecords.setBounds(17, 154, 206, 74);
		frame.getContentPane().add(btnAddrecords);
		
		JButton btnLogin = new JButton("login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    //prompt user for password
				username = JOptionPane.showInputDialog("username:") ;
				password = JOptionPane.showInputDialog("password:") ;

			}
		});
		btnLogin.setBounds(163, 0, 206, 56);
		frame.getContentPane().add(btnLogin);
		
		JButton btnListalltables = new JButton("List_all_tables");
		btnListalltables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListAllTables.listAll();
			}
		});
		btnListalltables.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnListalltables.setBounds(17, 61, 206, 74);
		frame.getContentPane().add(btnListalltables);
		
		JButton btnSearchflight = new JButton("Search_flight");
		btnSearchflight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Create and populate the panel.
			      JTextField originAirport = new JTextField(5);
			      JTextField destinationAirport = new JTextField(5);
			      JTextField date = new JTextField(5);
				 

			      JPanel myPanel = new JPanel();
			      myPanel.add(new JLabel("Origin airport code:"));
			      myPanel.add(originAirport);
			      myPanel.add(Box.createVerticalStrut(15)); // a spacer
			      myPanel.add(new JLabel("Destination airport code:"));
			      myPanel.add(destinationAirport);
			      myPanel.add(Box.createVerticalStrut(15)); // a spacer
			      myPanel.add(new JLabel("Date (YYYY-MM-DD):"));
			      myPanel.add(date);


			      int result = JOptionPane.showConfirmDialog(null, myPanel, 
			               "Please Enter data", JOptionPane.OK_CANCEL_OPTION);
			      
			      String OA = originAirport.getText();
			      String DA = destinationAirport.getText();
			      String FD = date.getText();
			      

			    	// get the connection
			    	Connect connect = new Connect();
			        Connection connection = connect.getConnection();

			        // create the SELECT SQL
			        StringBuffer sbSelect = new StringBuffer();
			        sbSelect.append(" SELECT * ");
			        sbSelect.append("FROM FLIGHT_INSTANCE FI, FLIGHT F ");
			        sbSelect.append("WHERE FI.FLIGHT_NUM = F.FLIGHT_NUM AND FI.AIRLINE_CODE = F.AIRLINE_CODE AND ");
			        sbSelect.append("FI.FDATE ='" + FD + "' AND F.ORIGIN_AIRPORT ='" + OA + "' AND F.DESTINATION_AIRPORT ='" + DA +"'" );

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
			            if (rs != null) {
			                // when the resultset is not null, there are records returned
			                try {
								while (rs.next())
								{
									//System.out.println("hello world");
									System.out.println("AIRLINE_CODE, FLIGHT_NUM, DEPARTURE_TIME, ARRIVAL_TIME, DURATION");
									System.out.println(rs.getString("AIRLINE_CODE") + " " + rs.getString("FLIGHT_NUM") + " " +  rs.getString("DEPARTURE_TIME") + " " + rs.getString("ARRIVAL_TIME") + " " + rs.getString("DURATION"));


								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			            }
			       
			      

			}
		});
		btnSearchflight.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSearchflight.setBounds(292, 61, 206, 74);
		frame.getContentPane().add(btnSearchflight);
		
		JButton btnSearchcustomer = new JButton("Show_reservations");
		btnSearchcustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			String CID = JOptionPane.showInputDialog("Type in Customer ID:") ;
				
	    	// get the connection
	    	Connect connect = new Connect();
	        Connection connection = connect.getConnection();

	        // create the SELECT SQL
	        StringBuffer sbSelect = new StringBuffer();
	        sbSelect.append(" SELECT * ");
	        sbSelect.append("FROM Ticket T ");
	        sbSelect.append("WHERE CUSTOMER_ID = '" + CID + "'");
	        

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
	            if (rs != null) {
	                // when the resultset is not null, there are records returned
	                try {
	                	System.out.println("Reservations for Customer " + CID + ":");
	                	System.out.println("TICKET_NUMBER, CUSTOMER_ID, AIRLINE_CODE, FLIGHT_NUM, FDATE,SEAT_NUMBER");
						while (rs.next())
						{
							
							
							System.out.println(rs.getString("TICKET_NUMBER") + " " + rs.getString("CUSTOMER_ID") + " " +  rs.getString("AIRLINE_CODE") + " " + rs.getString("FLIGHT_NUM") + " " + rs.getString("FDATE")+ " " + rs.getString("SEAT_NUMBER"));


						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	       	
				
				
				
				
				
			}
		});
		btnSearchcustomer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSearchcustomer.setBounds(292, 154, 206, 74);
		frame.getContentPane().add(btnSearchcustomer);
		
		JButton btnDeleterecords = new JButton("Delete_records");
		btnDeleterecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object[] possibleValues = { "Alliance", "Airline","Airport","Flight","Flight_Date","Flight_Instance","Aircraft","Class","Aircraft_Class","Fare","Seat","Customer","Ticket"};
				String DTableName = (String)JOptionPane.showInputDialog(null,
				"Which table to insert into?","Dialog",
				JOptionPane.INFORMATION_MESSAGE, null,
				possibleValues, possibleValues[0]);
				String DC = JOptionPane.showInputDialog("Delete condition:") ;
				
				
		    	// get the connection
		    	Connect connect = new Connect();
		        Connection connection = connect.getConnection();

		        // create the DELETE SQL
		        StringBuffer sbSelect = new StringBuffer();
		        sbSelect.append("DELETE FROM " + DTableName);
		        sbSelect.append(" WHERE " + DC);
		        System.out.println(sbSelect);
		        

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
			
				
				
				
				
			}
		});
		btnDeleterecords.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDeleterecords.setBounds(17, 247, 206, 74);
		frame.getContentPane().add(btnDeleterecords);
		
		JButton btnWritequery = new JButton("Write_query");
		btnWritequery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String WriteData = JOptionPane.showInputDialog("Your SQL query:") ;
				
		    	// get the connection
		    	Connect connect = new Connect();
		        Connection connection = connect.getConnection();

		        // create the DELETE SQL
		        StringBuffer sbSelect = new StringBuffer();
		        sbSelect.append(WriteData);
		        
		        //System.out.print(sbSelect);

		        

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
		            
		            try {
		            	ResultSetMetaData rsmd = rs.getMetaData();
		            	//System.out.println("querying SELECT * FROM XXX");
		            	   int columnsNumber = rsmd.getColumnCount();
		            	   while (rs.next()) {
		            	       for (int i = 1; i <= columnsNumber; i++) {
		            	           if (i > 1) System.out.print(",  ");
		            	           String columnValue = rs.getString(i);
		            	           System.out.print(columnValue + " ");
		            	       }
		            	       System.out.println("");
		            	   }
		            	
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            
		            
			
		
				
				
			}
		});
		btnWritequery.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnWritequery.setBounds(292, 247, 206, 74);
		frame.getContentPane().add(btnWritequery);
	}
}
