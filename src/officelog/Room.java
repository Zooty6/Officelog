package officelog;
/**
 * @author Zooty
 */
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Room implements Serializable{
    final private String Name;
    private int MaxPeople;
    final private Set<Room> Neighbours;
    final private Boolean Open;

    public Room(String Name, int MaxPeople, Boolean Open) {
        this.Name = Name;
        this.MaxPeople = MaxPeople;
        this.Open = Open;
        Neighbours = new HashSet<>();
    }
    
    public Room(String Name, int MaxPeople) {
        this(Name, MaxPeople, false);
    }
    
    public Room(String Name){
        this(Name, 0);
    }

    public String getName() {
        return Name;
    }

    public int getMaxPeople() {
        return MaxPeople;
    }

    public Boolean getOpen() {
        return Open;
    }

     public void setMaxPeople(int MaxPeople) {
        this.MaxPeople = MaxPeople;
    }
     
    public void addNeighbour(Room room){
        Neighbours.add(room);
    } 
    
    public boolean isNeighbour(Room room){
        return Neighbours.contains(room);
    }
}
