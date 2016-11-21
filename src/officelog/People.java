package officelog;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Zooty
 */
public class People {
    private Set<Person> IPeople;
    private int NumberOfPplInOffice;
    private int NumberOfPpl;
    
    public People(){
        IPeople = new HashSet<>();
        NumberOfPpl = 0;
        NumberOfPplInOffice = 0;
    }
    
    public Person getPerson(int ID){
        Person Fox=null; //foxes are cute http://i.imgur.com/cOIrbFk.gif
        for (Person person : IPeople) {
            if (person.getID()== ID)
                Fox = person;
        }
        if (Fox == null)
            throw new NullPointerException("No person with such ID");
        return Fox;
    }
    
    public void addPerson(Person newPerson){
        for (Person person : IPeople) 
            if(person.getID() == newPerson.getID())
                throw new IllegalArgumentException("Person with this ID already exist");
        
        IPeople.add(newPerson);
    }
    
    public void removePerson(Person oldPerson){
        if(!"Outside".equals(oldPerson.getLocation().getName())) 
            NumberOfPplInOffice--;        
        IPeople.remove(oldPerson);
        NumberOfPpl--;
    }
    
    public void removePerson(int oldPerson){
        for (Person person : IPeople) {
            if(person.getID() == oldPerson)
                IPeople.remove(person);
            //TODO: throw exception if no such person is found
        }
    }
    
    public void Move(int ID, Room dRoom){
        throw new UnsupportedOperationException("TODO");
    }
    
    public void Move(Person person, Room dRoom){
        if("Outside".equals(person.getLocation().getName()))
            NumberOfPplInOffice++;
        person.setLocation(dRoom);
        if("Outside".equals(dRoom.getName()))
            NumberOfPplInOffice--;
            
    }
}
