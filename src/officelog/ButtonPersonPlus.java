package officelog;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.Node;

/**
 * This class isn't used anymore. Delete this!
 * 
 * @author Zooty
 */
public class ButtonPersonPlus extends ButtonPerson{
    private boolean Plus = false;
    private Set<Person> PlusList = new HashSet<>();
    
    public ButtonPersonPlus() {
        super();
    }

    public ButtonPersonPlus(String text) {
        super(text);
    }

    public ButtonPersonPlus(String text, Node graphic) {
        super(text, graphic);
    }

    public void setPlus(boolean Plus) {
        if(Plus)
            this.setText("+");
        else                      
            this.Plus = Plus;
    }

    public boolean isPlus() {
        return Plus;
    }

    public Set<Person> getPersonList() {
        return PlusList;
    }
    
    
    public void addPerson(Person newPerson){
        PlusList.add(newPerson);
    }
    
    public void removePerson(Person oldPerson){
        PlusList.remove(oldPerson);
    }
}