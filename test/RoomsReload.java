import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import connections.DBConnection;
import officelog.model.Room;

/**
 * Reloads all Rooms in the database. It was used once, when the database was built.
 * Not needed anymore.
 *
 * @author Zooty
 */
public class RoomsReload implements DBConnection {

    public static void load(ArrayList<Room> Office) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)) {
            Statement stm = conn.createStatement();
            for (Room room : Office) {                
                if (room.isOpen()) {
                    stm.execute("INSERT INTO Rooms VALUES('"+ room.getName() + "', " + room.getMaxPeople() + ", 1)");
                } else {
                    stm.execute("INSERT INTO Rooms VALUES('" + room.getName() + "', " + room.getMaxPeople() + ", 0)");
                }                    
            }
            for (Room roomA : Office) {
                for (Room roomB : roomA.getNeighbors()) {                    
                    stm.execute("INSERT INTO RoomConnections VALUES('" + roomA.getName() + "', '" + roomB.getName() + "')");
                }
            }
        } catch (SQLException ex) {
            
        }
    }
}
