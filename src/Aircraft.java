

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Aircraft {
    private String manufacturer;
    private String model;

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
        String[] tableTypes = {
                "TABLE",
                "VIEW",
                "ALIAS",
                "SYNONYM",
                "GLOBAL TEMPORARY",
                "LOCAL TEMPORARY",
                "SYSTEM TABLE"};
        ResultSet rs = dmd.getTables (null, null, "AIRCRAFT", tableTypes);
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
        sbCreate.append(" CREATE TABLE AIRCRAFT ");
        sbCreate.append(" ( ");
        sbCreate.append("     manufacturer VARCHAR(20), ");
        sbCreate.append("     model VARCHAR(20), ");
        sbCreate.append("primary key(manufacturer,model)");
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
    public Aircraft[] loadAll() throws SQLException
    {
        // get the connection
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT manufacturer, model FROM AIRCRAFT");

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
                	 Aircraft aircraft = new Aircraft();
                	 aircraft.setManufacturer(rs.getString("manufacturer"));
                	 aircraft.setModel(rs.getString("model"));

                    // store it in the list
                    collection.add(aircraft);
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
        return (Aircraft[])collection.toArray(new Aircraft[0]);
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
        sbInsert.append(" INSERT INTO AIRCRAFT ");
        sbInsert.append(" VALUES ");
        sbInsert.append(" ('" + manufacturer + "', '" + model + "')");

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
    
    public static void insertRecord(String InsertManuf, String InsertModel){
     System.out.println("Insert Aircraft " + count);
     Aircraft aircraftTB = new Aircraft();
     aircraftTB.setManufacturer(InsertManuf);
     aircraftTB.setModel(InsertModel);
     count = count + 1;

     try
     {
    	 aircraftTB.insertData();
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
    	Aircraft aircarftTB = new Aircraft();    //change
    	Aircraft[] db;                           //change
        try {
            db = aircarftTB.loadAll();    //change
            System.out.println("\nAircraft");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
            	Aircraft mdb = db[i];
                System.out.println(mdb.getManufacturer()+ " " + mdb.getModel());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }
}
