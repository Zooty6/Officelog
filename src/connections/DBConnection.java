package connections;



/**
 * Defines the parameters needed to connect to the database
 * 
 * @author Zooty
 */
public interface DBConnection {
    String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String URL = "jdbc:sqlserver://localhost:1433;databaseName=OfficelogDB;";
    String URLLOCAL = "jdbc:sqlserver://localhost:1433;databaseName=OfficelogDB;";
    String URLREMOTE = "jdbc:sqlserver://zoliftp.dlinkddns.com:1433;databaseName=OfficelogDB;";
    String USER = "officelogUser";
    String PASSW = "officelogPW";
}