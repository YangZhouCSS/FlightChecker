

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Customer
{
    private String customerID;
    private String name;
    private Integer age;
    private String gender;

    public String getCustomerID(){
        return customerID;
    }

    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Integer getAge(){
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

	
	/** Method to check if a table exists
	   * @param connection java.sql.Connection object
	   * @return true is the table exists, false otherwise
	   * @throws SQLException
	   */
    private boolean doesTableExist(Connection connection) throws SQLException {
        boolean bTableExists = false;

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet rs = dmd.getTables (null, null, "CUSTOMER", null);
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
        sbCreate.append(" CREATE TABLE CUSTOMER ");
        sbCreate.append(" ( ");
        sbCreate.append("     customer_ID VARCHAR(20), ");
        sbCreate.append("     name VARCHAR(20), ");
        sbCreate.append("     age integer, ");
        sbCreate.append("     gender VARCHAR(20), ");
        sbCreate.append("primary key(customer_ID)");
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
    public Customer[] loadAll() throws SQLException
    {
        // get the connection
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT customer_ID, name, age, gender FROM CUSTOMER");

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
                	Customer customer = new Customer();
                	customer.setCustomerID(rs.getString("customer_ID"));
                	customer.setName(rs.getString("name"));
                	customer.setAge(rs.getInt("age"));
                	customer.setGender(rs.getString("gender"));

                    // store it in the list
                    collection.add(customer);
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
        return (Customer[])collection.toArray(new Customer[0]);
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
        sbInsert.append(" INSERT INTO CUSTOMER ");
        sbInsert.append(" VALUES ");
        sbInsert.append(" ('" + customerID + "', '" + name + "', '" + age.toString() + "', '" + gender + "')");

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
    
    public static void insertRecord(String InsertID, String InsertName, Integer InsertAge, String InsertGender){
     System.out.println("Insert Customer " + count);
     Customer customerTB = new Customer();
     customerTB.setCustomerID(InsertID);
     customerTB.setName(InsertName);
     customerTB.setAge(InsertAge);
     customerTB.setGender(InsertGender);
     count = count + 1;

     try
     {
    	 customerTB.insertData();
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
    	Customer customerTB = new Customer();    //change
    	Customer [] db;                           //change
        try {
            db = customerTB.loadAll();    //change
            System.out.println("\nCustomer");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
            	Customer mdb = db[i];
                System.out.println(mdb.getCustomerID()+ " " + mdb.getName()+ " " +mdb.getAge()+ " " +mdb.getGender());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }
}