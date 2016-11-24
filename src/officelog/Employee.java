package officelog;
/* *
 * @author Zooty
 */
import java.util.HashSet;
import java.util.Set;
import javafx.scene.image.Image;


public class Employee extends Person{
    private String Job;
    final private Set<Room> Permissions;
            
    public Employee(String Name, int ID, String Job) { 
        super(Name, ID);
        this.Job = Job;
        Permissions = new HashSet<>();
    }
            
    public Employee(String Name, Image Pic, int ID, String Job) {
        super(Name, Pic, ID);
        this.Job = Job;
        Permissions = new HashSet<>();
    }
    
    public String getJob() {
        return Job;
    }

    public void setJob(String Job) {
        this.Job = Job;
    }
    
    public void addPermission(Room room){
        this.Permissions.add(room);
    }
    
    public void removePermission(Room room){
        this.Permissions.remove(room);
    }
    
    public void removePermission(String roomname){
        //does not check if the Room is actually found or not
        for (Room p : Permissions) {
            if(p.getName().equals(roomname))
                Permissions.remove(p);
        }        
    }
    
    @Override
    public boolean isAllowed(Room room){
        return Permissions.contains(room);
    }
}
