package officelog.model;

import Messages.PersonTemplate;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an employee in the office. Extends from Person. 
 * 
 * @author Zooty
 */
public class Employee extends Person {
    
    /**
     * Name of the job.
     */
    private String Job;
    
    /**
     * This collection contains all the Rooms this person can enter.
     */
    final private Set<Room> Permissions = new HashSet<>();
            
    /**
     * Creates and Employee with default picture.
     * 
     * @param Name name of the person.
     * @param ID unique ID of the person.
     * @Param location Location of the person.
     * @param Job Job of the person.
     */
    public Employee(String Name, int ID, Room location, String Job) { 
        super(Name, location, ID);
        this.Job = Job;
    }
     
    /**
     * Creates and Employee.
     * 
     * @param Name name of the person.
     * @param Pic picture of the person.
     * @param ID unique ID of the person.
     * @param Job Job of the person.
     */
    public Employee(String Name, BufferedImage Pic, int ID, String Job) {
        super(Name, Pic, ID);
        this.Job = Job;
    }
    
    /**
     * Creates and Employee.
     * 
     * @param Name name of the person.
     * @param Pic picture of the person.
     * @param ID unique ID of the person.
     * @param Job Job of the person.
     * @param per  list of permissions this employee can enter.
     */
    public Employee(String Name, BufferedImage Pic, int ID, String Job, Room[] per) {
        super(Name, Pic, ID);
        this.Job = Job;
        for (Room room : per) {
            Permissions.add(room);
        }
    }
    
    /**
     * Creates and Employee.
     * 
     * @param Name name of the person.
     * @param ID unique ID of the person.
     * @param Loc The current location of the Employee.
     * @param Job Job of the person.
     * @param per list of permissions this employee can enter.
     */
    public Employee(String Name, int ID, Room Loc, String Job, Room[] per) {
        super(Name, Loc, ID);
        this.Job = Job;        
        for (Room room : per) {
            Permissions.add(room);
        }
    }
    
    /**
     * Creates and Employee.
     * 
     * @param Name name of the person.
     * @param ID unique ID of the person.
     * @param Loc The current location of the Employee.
     * @param Job Job of the person.
     * @param per list of permissions this employee can enter.
     */
    public Employee(String Name, int ID, BufferedImage Pic, Room Loc, String Job, Room[] per) {
        super(Name, Loc, Pic, ID);
        this.Job = Job;        
        for (Room room : per) {
            Permissions.add(room);
        }
    }
    
     /**
     * Creates and Employee.
     * 
     * @param Name name of the person.
     * @param ID unique ID of the person.
     * @param Loc The current location of the Employee.
     * @param Job Job of the person.
     * @param per list of permissions this employee can enter.
     */
    public Employee(String Name, int ID, BufferedImage Pic, Room Loc, String Job, ArrayList<Room> per) {
        super(Name, Loc, Pic, ID);
        this.Job = Job;        
        for (Room room : per) {
            Permissions.add(room);
        }
    }
    
    /**
     * Creates and Employee with default picture.
     * 
     * @param Name name of the person.
     * @param ID unique ID of the person.
     * @param Job Job of the person.
     * @param per  list of permissions this employee can enter.
     */
    public Employee(String Name, int ID, String Job, Room[] per) {
        super(Name, ID);
        this.Job = Job;
        Permissions.addAll(Arrays.asList(per));
    }
    
    public Employee(PersonTemplate tmpl, Room room, ArrayList<Room> office) throws IOException{
        super(tmpl,room);
        this.Job = tmpl.getJob();
         for (String pername : tmpl.getPer()) {            
            for (Room room1 : office) {
                if(room1.getName().equals(pername))
                    this.Permissions.add(room1);
            }
        }
    }
    
    /**
     * Returns the job of the person.
     * 
     * @return the job of the person.
     */
    public String getJob() {
        return Job;
    }

    /**
     * Returns the set of Rooms this Employee can enter.
     * 
     * @return the set of Rooms this Employee can enter.
     */
    public Set<Room> getPermissions() {
        return Permissions;
    }
    
    /**
     * Modifies the job of the person.
     * 
     * @param Job the new job of this person 
     */
    public void setJob(String Job) {
        this.Job = Job;
    }
    
    /**
     * Adds a new Room where this person can enter.
     * 
     * @param room the new room this person can enter.
     */
    public void addPermission(Room room){
        this.Permissions.add(room);
    }
    
    /**
     * Removes a Room from the list of Rooms where this person can enter.
     * 
     * @param room the Room this person can not enter anymore.
     */
    public void removePermission(Room room){
        this.Permissions.remove(room);
    }
    
     /**
     * Removes a Room from the list of Rooms where this person can enter.
     * 
     * @param roomName the name of the Room this person can not enter anymore.
     */
    public void removePermission(String roomName){
        //does not check if the Room is actually found or not
        for (Room p : Permissions) {
            if(p.getName().equals(roomName))
                Permissions.remove(p);
        }        
    }
    /**
     * This function tells you if the person is allowed to enter the specific Room.
     * 
     * @param room The specific room the person wants to enter.
     * @return True if the person is allowed to enter.
     */
    @Override
    public boolean isAllowed(Room room){
        boolean r = false;
        for (Room Permission : Permissions)
            if (Permission.getName().equals(room.getName()))
                r = true;        
        return r;
    }

    /**
     * Returns a String of the ID, name and the Job of the Employee.
     * 
     * @return the ID, name and the Job of the Employee.
     */
    @Override
    public String toString() {
        return "ID: " + this.getID() + ", Name: "+ this.getName();
    }    
}