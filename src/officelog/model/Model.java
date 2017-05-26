package officelog.model;

import connections.ConnectionToServer;
import connections.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;


/**
 * @author Zooty
 */
public class Model implements DBConnection{
    
    /**
     * Creates an empty collection for the people
     */
    private People people;
    
    /**
     * Creates a collection for events
     */
    private final EventList eventList = new EventList();
    
    /**
     * Creates a collection for all the rooms in the office
     */
    private ArrayList<Room> office = new ArrayList<>();
    
    /**
     * Builds up the model of the office.
     */
    public Model(){        
        eventList.addEvent(new Event("Officelog has started"));
        BuildOffice();
        people = new People(this);       
        ConnectionToServer.setModel(this);
    }
    
    
    
    /**
     * Builds up the logical structure of the office.
     */
    private void BuildOffice() {
        int tries = 0;
        try {
            office = ConnectionToServer.fetchRooms();
        } catch (InterruptedException ex) {
            if(tries++>3){
                BuildOffice();
            }else{
                System.out.println("There was a problem while fetching data from server.");
                System.out.println("Client will close now.");
                System.exit(3);
            }
        }
        //TODO not rely on this...
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)){
//            
//            ResultSet cat = conn.createStatement().executeQuery(SQLSELECTROOMS1);
//            while (cat.next()){
//                //System.out.println(cat.getString(1) + "\t" + cat.getString(2) + "\t" + cat.getString(3));                
//                office.add(new Room(cat.getString(1),
//                                    Integer.parseInt(cat.getString(2)),
//                                    (Integer.parseInt(cat.getString(3)) == 1)));
//            }
//            
//            ResultSet fox = // Zoli likes foxes
//                    conn.createStatement().executeQuery(SQLSELECTROOMCONNECTIONS);           
//            while (fox.next()){
//                getRoom(fox.getString(1)).getNeighbors().add(getRoom(fox.getString(2)));
//            }            
//        } catch (SQLException ex) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Officelog");
//            alert.setHeaderText("SQL Error");
//            alert.setContentText("There was an error connecting to the database: " + ex.getMessage());
//            alert.showAndWait();            
//            System.exit(1);
//        }
    }

    /**
     * Gets the Room that has the parameter name.
     * 
     * @param name name of the room we want to get.
     * @return the Room we want to get. Returns null if no Room was found with that name.
     */
    public Room getRoom(String name){
        Room r=null;
        for (Room room : office) 
            if(room.getName().equals(name))
                r=room;
        return r;        
    }
            
    /**
     * Returns the list of existing people.
     * 
     * @return the list of existing people.
     */
    public People getPeople() {
        return people;
    }

    /**
     * Returns the list of existing events.
     * 
     * @return the list of existing events. 
     */
    public EventList getEventList() {
        return eventList;
    }

    /**
     * Returns the list of existing Rooms in the office.
     * 
     * @return the list of existing Rooms in the office.
     */
    public ArrayList<Room> getOffice() {
        return office;
    }

    /**
     * Replaces the list of the People in the model. Used when loading from files.
     * 
     * @param people new list we want to load into the model
     */
    public void setPeople(People people) {
        this.people = people;
    }   
}
