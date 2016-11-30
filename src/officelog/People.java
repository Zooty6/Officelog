package officelog;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * A collection class for all the people in the office.
 * 
 * @author Zooty
 */
public class People {
    
    /**
     * Collection of the people.
     */
    private final Set<Person> IPeople;
    
    /**
     * Number of the people who are currently in the building (and not outside).
     */
    private int NumberOfPplInOffice;
    
    /**
     * Number of all the people in the collection.
     */
    private int NumberOfPpl;
    
    /**
     * used for calculating the unique ID for every new Person.
     */
    private int MaxID = 0;
    
    /**
     * Creates a new, empty collection.
     */
    public People(){
        IPeople = new HashSet<>();
        NumberOfPpl = 0;
        NumberOfPplInOffice = 0;
    }
    
    /**
     * Gets a specific Person from the collection with the given ID.
     *      
     * @param ID The ID of the Person we want to get.
     * @return The Person with the ID we have given as parameter.
     * 
     * @throws NullPointerException if no such Person is found.
     */
    public Person getPerson(int ID){
        Person Fox=null; //foxes are cute! http://i.imgur.com/cOIrbFk.gif
        for (Person person : IPeople) {
            if (person.getID()== ID)
                Fox = person;
        }
        if (Fox == null)
            throw new NullPointerException("No person with such ID");
        return Fox;
    }
    
    /**
     * Returns the specific person from the collection who has the parameter name.
     * If more people are found, the user must choose one from a dialog window.
     * 
     * @param name name of the person we want to get
     * @return the person who has the given name
     */
    public Person GetPerson(String name){
        Person r = null;
        int count = 0;
        for (Person person : IPeople) 
            if (name.equals(person.getName())){
                r=person;
                count++;
            }
        if(count>1){
            ;//TODO: Multiple people are found. Select the right one! Returns the last found rn.
        }            
        return r;
    }

    /**
     * 
     * @return the number of people in the building.
     */
    public int getNumberOfPplInOffice() {
        return NumberOfPplInOffice;
    }

    /**
     * 
     * @return the number of people in the collection.
     */
    public int getNumberOfPpl() {
        return NumberOfPpl;
    }
    
    /**
     * Adds a new Person to the collection
     * 
     * @param newPerson the person we want to add.
     * 
     * @throws IllegalArgumentException if the new Person's ID already exist within the collection.
     */
    private void addPerson(Person newPerson){
        for (Person person : IPeople) 
            if(person.getID() == newPerson.getID())
                throw new IllegalArgumentException("Person with this ID already exist");
        
        IPeople.add(newPerson);
        MaxID=newPerson.getID()+1;
    }
    
    /**
     * Adds a new Person with given name.
     * 
     * @param Name the name of the new Person.
     * 
     * @return the unique ID of the new Person.
     */
    public int addPerson(String Name){
        addPerson(new Person(Name, MaxID));
        return MaxID-1;
    }
    
    /**
     * Adds a new Person with given name and picture.
     * 
     * @param name the name of the new Person.
     * @param Pic the picture of the new Person.
     * 
     * @return the unique ID of the new Person.
     */
    public int addPerson(String name, BufferedImage Pic){
        addPerson(new Person(name, Pic, MaxID)); 
        return MaxID-1;
    }
    
    /**
     * Adds a new Employee with given parameters.
     * 
     * @param name the name of the new Employee.
     * @param Job the Job of the new Employee.
     * 
     * @return the unique ID of the new Employee.
     */
    public int addEmployee(String name, String Job){
        addPerson(new Employee(name, MaxID, Job));
        return MaxID-1;
    }
    
    /**
     * Adds a new Employee with given parameters.
     * 
     * @param name the name of the new Employee.
     * @param pic the picture of the new Employee.
     * @param Job the Job of the new Employee.
     * 
     * @return the unique ID of the new Employee.
     */
    public int addEmployee(String name, BufferedImage pic, String Job){
        addPerson(new Employee(name, pic, MaxID, Job)); 
        return MaxID-1;
    }
    
    /**
     * Removes a specific Person from the collection. Does not check if it was in the collection!
     * 
     * @param oldPerson the person we want to remove
     * 
     * @throws ClassCastException if the type of the specified element is incompatible with this set (optional)
     * @throws NullPointerException if the specified element is null and this set does not permit null elements (optional)
     * @throws UnsupportedOperationException if the remove operation is not supported by this set
     */
    public void removePerson(Person oldPerson){
        if(!"Outside".equals(oldPerson.getLocation().getName())) 
            NumberOfPplInOffice--;        
        IPeople.remove(oldPerson);
        NumberOfPpl--;
    }
    
    /**
     * Removes a Person from the collection with given ID
     * 
     * @param oldPersonID ID of the Person we want to remove
     * 
     * @throws IllegalArgumentException If the person wasn't in the list.
     */
    public void removePerson(int oldPersonID){
        Person otter = null; //otters are cute as well! http://i.imgur.com/TvhgtOs.mp4
        for (Person person : IPeople) {            
            if(person.getID() == oldPersonID){
                IPeople.remove(person);
                otter=person;
                if(!"outside".equals(otter.getLocation().getName())){
                    NumberOfPplInOffice--;
                    NumberOfPpl--;
                }else
                    NumberOfPpl--;
            }            
        }
        if(otter==null)
            throw new IllegalArgumentException("This person isn't in the list!");
    }
          
    /**
     * Moves a specific person to the parameter Room.
     * 
     * @param person The Person we want to move.
     * @param dRoom The destination Room we move the Person.
     */
    public void Move(Person person, Room dRoom){
        if("Outside".equals(person.getLocation().getName()))
            NumberOfPplInOffice++;
        person.setLocation(dRoom);
        if("Outside".equals(dRoom.getName()))
            NumberOfPplInOffice--;            
    }
    
     /**
     * Moves a specific person with given ID to the parameter Room. May not need to use
     * this method.
     * 
     * @param ID The ID of the Person.
     * @param dRoom The destination Room we move the Person.
     */
    public void Move(int ID, Room dRoom){
        Move(this.getPerson(ID), dRoom);
    }
}