import static connections.DBConnection.PASSW;
import static connections.DBConnection.USER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import static connections.DBConnection.URL;

/**
 *
 * @author Zooty
 */
public class ConnTest {
 
    public static void main(String[] args) {
        String userName = "officelogUser";
        String password = "officelogPW";
        String url = "jdbc:derby://localhost:1527/OfficelogDB";
        String url2 = "jdbc:sqlserver://localhost:1433;databaseName=OfficelogDB;";
        String url3 = "jdbc:sqlserver://zoliftp.dlinkddns.com:1433;databaseName=OfficelogDB;";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try(Connection conn = DriverManager.getConnection(url2,userName,password)){
            ResultSet rs = conn.createStatement().executeQuery("INSERT INTO People VALUES(3, 'asfda','R20',NULL,NULL,0)");
            /*while (rs.next())
                System.out.println(rs.getInt("Id")+"\t" + rs.getString("Name"));*/
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)) {
            ;
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Officelog");
            alert.setHeaderText("SQL Error");
            alert.setContentText("There was an error connecting to the database");
            alert.showAndWait();
            System.exit(1);
        }
    }

}