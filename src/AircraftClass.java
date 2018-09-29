

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AircraftClass {
    private String manufacturer;
    private String model;
    private String className;
    private Integer capacity;

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

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getCapacity() {
        return capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    
    /** Method to check if a table exists
	   * @param connection java.sql.Connection object
	   * @return true is the table exists, false otherwise
	   * @throws SQLException
	   */
    private boolean doesTableExist(Connection connection) throws SQLException {
        boolean bTableExists = false;

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet rs = dmd.getTables (null, null, "AIRCRAFT_CLASS", null);
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
        sbCreate.append(" CREATE TABLE AIRCRAFT_CLASS ");
        sbCreate.append(" ( ");
        sbCreate.append("     manufacturer VARCHAR(20), ");
        sbCreate.append("     model VARCHAR(20), ");
        sbCreate.append("     className VARCHAR(20), ");
        sbCreate.append("     capacity integer, ");
        sbCreate.append("primary key(manufacturer,model,className, capacity),");
        sbCreate.append("foreign key(manufacturer,model) references Aircraft(manufacturer,model),");
        sbCreate.append("foreign key(className) references Class(class_name)");
        sbCreate.append(" ) ");
        
        //System.out.println(sbCreate);

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
    public AircraftClass[] loadAll() throws SQLException
    {
        // get the connection
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT manufacturer, model, className, capacity FROM AIRCRAFT_CLASS");

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
                	 AircraftClass aircraftClass = new AircraftClass();
                	 aircraftClass.setManufacturer(rs.getString("manufacturer"));
                	 aircraftClass.setModel(rs.getString("model"));
                	 aircraftClass.setClassName(rs.getString("className"));
                	 aircraftClass.setCapacity(rs.getInt("capacity"));

                    // store it in the list
                    collection.add(aircraftClass);
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
        return (AircraftClass[])collection.toArray(new AircraftClass[0]);
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
        sbInsert.append(" INSERT INTO AIRCRAFT_CLASS ");
        sbInsert.append(" VALUES ");
        sbInsert.append(" ('" + manufacturer + "', '" + model + "', '" + className + "', '" + capacity.toString() + "')");

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
    
    public static void insertRecord(String InsertManuf, String InsertModel, String InsertClass, Integer InsertCapacity){
     System.out.println("Insert Aircraft Class " + count);
     AircraftClass aircraftCTB = new AircraftClass();
     aircraftCTB.setManufacturer(InsertManuf);
     aircraftCTB.setModel(InsertModel);
     aircraftCTB.setClassName(InsertClass);
     aircraftCTB.setCapacity(InsertCapacity);
     count = count + 1;

     try
     {
    	 aircraftCTB.insertData();
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
    	AircraftClass aircarftCTB = new AircraftClass();    //change
    	AircraftClass[] db;                           //change
        try {
            db = aircarftCTB.loadAll();    //change
            System.out.println("\nAircraft Class");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
            	AircraftClass mdb = db[i];
                System.out.println(mdb.getManufacturer()+ " " + mdb.getModel()+ " " +mdb.getClassName()+ " " +mdb.getCapacity());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }
}
