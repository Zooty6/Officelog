package officelog;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a Room in the office
 * 
 * @author Zooty
 */
public class Room implements Serializable{
    /**
     * The unique name of the Room. Serves as primary key.
     */
    final private String Name;
    
    /**
     * The number of people can be in this room. Set 0 if unlimited. 
     */
    private int MaxPeople;
    
    /**
     * Collection of Rooms that can be reached from this Room.
     */
    final private Set<Room> Neighbors;
    
    /**
     * If open, everybody can enter without authorizing and visitors can go in as well 
     * without permission.
     */
    final private Boolean Open;

    /**
     * Creates a Room.
     * 
     * @param Name Name of the Room.
     * @param MaxPeople Maximum number of people that can be in the Room.
     * @param Open True if can be entered without authorization.
     */
    public Room(String Name, int MaxPeople, Boolean Open) {
        this.Name = Name;
        this.MaxPeople = MaxPeople;
        this.Open = Open;
        Neighbors = new HashSet<>();
    }
    
    /**
     * Creates a room that needs authorization to enter.
     * 
     * @param Name Name of the Room.
     * @param MaxPeople Maximum number of people that can be in the Room.
     */
    public Room(String Name, int MaxPeople) {
        this(Name, MaxPeople, false);
    }
    
    /**
     * Creates a room with "unlimited space" that needs authorization to enter.
     * 
     * @param Name Name of the Room.
     */
    public Room(String Name){
        this(Name, 0);
    }

    /**
     * 
     * @return the name of the Room
     */
    public String getName() {
        return Name;
    }

    /**
     * 
     * @return the maximum number of people can be in that room.
     */
    public int getMaxPeople() {
        return MaxPeople;
    }

    /**
     * 
     * @return true if the room needs no authorization to enter.
     */
    public Boolean getOpen() {
        return Open;
    }

    /**
     * 
     * @param MaxPeople Sets the maximum number of people that can be in the Room.
     */
     public void setMaxPeople(int MaxPeople) {
        this.MaxPeople = MaxPeople;
    }
     
     /**
      * Adds a new Room that can be reached directly from this Room.
      * 
      * @param room the new neighbor.
      */
    public void addNeighbor(Room room){
        Neighbors.add(room);
    } 
    
    /**
     * Returns true if the parameter is neighbor of this Room.
     * 
     * @param room the Room we question if is a neighbor.
     * @return true if it is a neighbor.
     */
    public boolean isNeighbor(Room room){
        return Neighbors.contains(room);
    }
}