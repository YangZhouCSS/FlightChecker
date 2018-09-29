
import java.lang.Class;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// JDK libraries
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Alliance {
    private String allianceName;


    
    /** Method to check if a table exists
	   * @param connection java.sql.Connection object
	   * @return true is the table exists, false otherwise
	   * @throws SQLException
	   */
    private boolean doesTableExist(Connection connection) throws SQLException {
        boolean bTableExists = false;

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet rs = dmd.getTables (null, null, "ALLIANCE", null);
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
        sbCreate.append(" CREATE TABLE ALLIANCE ");
        sbCreate.append(" ( ");
        sbCreate.append("     ALLIANCE_NAME VARCHAR(20), ");
        sbCreate.append("primary key(ALLIANCE_NAME)");
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
    public Alliance[] loadAll() throws SQLException
    {
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT ALLIANCE_NAME FROM ALLIANCE");

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
                    Alliance alliance = new Alliance();
                    alliance.setAllianceName(rs.getString("ALLIANCE_NAME"));

                    // store it in the list
                    collection.add(alliance);
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
        return (Alliance[])collection.toArray(new Alliance[0]);
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
            System.out.println("Alliance Table doesn't exist. Creating it.....");
            createTable(connection);
        }

        StringBuffer sbInsert = new StringBuffer();
        sbInsert.append(" INSERT INTO ALLIANCE  ");
        sbInsert.append(" VALUES ");
        sbInsert.append(" ('" + allianceName + "') ");

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
    System.out.println("Insert Alliance " + count);
    Alliance allianceTB = new Alliance();
    allianceTB.setAllianceName(InsertName);
    count = count + 1;

    try
    {
        allianceTB.insertData();
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
    
    public String getAllianceName() {
        return allianceName;
    }
    public void setAllianceName(String allianceName) {
        this.allianceName = allianceName;
    }
    
    public static void PrintOut() {
    	Alliance allianceTB = new Alliance();    //change
        Alliance[] db;                           //change
        try {
            db = allianceTB.loadAll();
            System.out.println("\nAlliance");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
                Alliance mdb = db[i];
                System.out.println(mdb.getAllianceName());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }




}