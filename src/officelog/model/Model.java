package officelog.model;

import connections.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        //FetchPeople();
        BuildOffice();        
        people = new People(this);
    }
    
    
    
    /**
     * Builds up the logical structure of the office.
     */
    private void BuildOffice() {          
        //<editor-fold defaultstate="collapsed" desc="Building ther office the old way.">
            /*office.add(new Room("R1", 0, true));
            office.add(new Room("R2", 2));
            office.add(new Room("R3", 6));
            office.add(new Room("R4", 6));
            office.add(new Room("R5", 4));
            office.add(new Room("R6", 0, true));
            office.add(new Room("R7", 3));
            office.add(new Room("R8"));
            office.add(new Room("R9", 10));
            office.add(new Room("R10", 2));
            office.add(new Room("R11", 2));
            office.add(new Room("R12", 8));
            office.add(new Room("R13", 8));
            office.add(new Room("R14", 8));
            office.add(new Room("R15", 6));
            office.add(new Room("R16", 2));
            office.add(new Room("R17", 0, true));
            office.add(new Room("R18", 0, true));
            office.add(new Room("R19", 0, true));
            office.add(new Room("Outside", 0, true));
            
            for (Room room : office) {
            switch(room.getName()){
            case "R1":
            for (Room nroom : office)
            if("R18".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R2":
            for (Room nroom : office)
            if("R17".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R3":
            for (Room nroom : office)
            if("R17".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R4":
            for (Room nroom : office)
            if("R17".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R5":
            for (Room nroom : office)
            if("R17".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R6":
            for (Room nroom : office)
            if("R19".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R7":
            for (Room nroom : office)
            if("R19".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R8":
            for (Room nroom : office)
            if("R19".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R9":
            for (Room nroom : office)
            if("R18".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R10":
            for (Room nroom : office)
            if("R18".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R11":
            for (Room nroom : office)
            if("R18".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R12":
            for (Room nroom : office)
            if("R18".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R13":
            for (Room nroom : office)
            if("R18".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R14":
            for (Room nroom : office)
            if("R18".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R15":
            for (Room nroom : office)
            if("R18".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R16":
            for (Room nroom : office)
            if("R18".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R17":
            for (Room nroom : office)
            if("R2".equals(nroom.getName()) ||
            "R3".equals(nroom.getName()) ||
            "R4".equals(nroom.getName()) ||
            "R5".equals(nroom.getName()) ||
            "R19".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R18":
            for (Room nroom : office)
            if("R1".equals(nroom.getName()) ||
            "R9".equals(nroom.getName()) ||
            "R10".equals(nroom.getName()) ||
            "R11".equals(nroom.getName()) ||
            "R12".equals(nroom.getName()) ||
            "R13".equals(nroom.getName()) ||
            "R14".equals(nroom.getName()) ||
            "R15".equals(nroom.getName()) ||
            "R16".equals(nroom.getName()) ||
            "R19".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "R19":
            for (Room nroom : office)
            if("R6".equals(nroom.getName()) ||
            "R7".equals(nroom.getName()) ||
            "R8".equals(nroom.getName()) ||
            "R17".equals(nroom.getName()) ||
            "R18".equals(nroom.getName()) ||
            "Outside".equals(nroom.getName()))
            room.addNeighbor(nroom);
            break;
            case "Outside":
            for (Room nroom : office)
            if("R19".equals(nroom.getName()))
            room.addNeighbor(nroom);
            }
            } */
            //</editor-fold>
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)){
            
            ResultSet cat = // Szandi likes cats
                    conn.createStatement().executeQuery("SELECT * FROM Rooms");
            while (cat.next()){
                //System.out.println(cat.getString(1) + "\t" + cat.getString(2) + "\t" + cat.getString(3));                
                office.add(new Room(cat.getString(1),
                                    Integer.parseInt(cat.getString(2)),
                                    (Integer.parseInt(cat.getString(3)) == 1)));
            }
            
            ResultSet fox = // Zoli likes foxes
                        conn.createStatement().executeQuery("SELECT * FROM RoomConnections");           
            while (fox.next()){
                getRoom(fox.getString(1)).getNeighbors().add(getRoom(fox.getString(2)));
            }            
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Officelog");
            alert.setHeaderText("SQL Error");
            alert.setContentText("There was an error connecting to the database");
            alert.showAndWait();
            System.exit(1);
        }
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
