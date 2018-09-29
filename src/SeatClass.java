
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class SeatClass {
    private String className;

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    
    /** Method to check if a table exists
	   * @param connection java.sql.Connection object
	   * @return true is the table exists, false otherwise
	   * @throws SQLException
	   */
    private boolean doesTableExist(Connection connection) throws SQLException {
        boolean bTableExists = false;

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet rs = dmd.getTables (null, null, "CLASS", null);
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
        sbCreate.append(" CREATE TABLE CLASS ");
        sbCreate.append(" ( ");
        sbCreate.append("     CLASS_NAME VARCHAR(20), ");
        sbCreate.append("primary key(CLASS_NAME)");
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
    public SeatClass[] loadAll() throws SQLException
    {
    	Connect connect = new Connect();
        Connection connection = connect.getConnection();

        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT CLASS_NAME FROM CLASS");

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
                    SeatClass sClass = new SeatClass();
                    sClass.setClassName(rs.getString("CLASS_NAME"));

                    // store it in the list
                    collection.add(sClass);
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
        return (SeatClass[])collection.toArray(new SeatClass[0]);
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
            System.out.println("SeatClass Table doesn't exist. Creating it.....");
            createTable(connection);
        }

        StringBuffer sbInsert = new StringBuffer();
        sbInsert.append(" INSERT INTO CLASS  ");
        sbInsert.append(" VALUES ");
        sbInsert.append(" ('" + className + "') ");

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
    System.out.println("Insert Class " + count);
    SeatClass classTB = new SeatClass();
    classTB.setClassName(InsertName);
    count = count + 1;

    try
    {
        classTB.insertData();
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
    	SeatClass classTB = new SeatClass();    //change
        SeatClass[] db;                           //change
        try {
            db = classTB.loadAll();
            System.out.println("\nClass");    //change
            System.out.println("--------");
            System.out.println(db.length);
            for (int i = 0; i <db.length; i++) {
                SeatClass mdb = db[i];
                System.out.println(mdb.getClassName());   //change
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    
    }




}