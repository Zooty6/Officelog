package officelog;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * @author Zooty
 */
public class ButtonPerson extends Button{
    private Person person; //redundant data, I'm too lazy to rethink and fix.
    private boolean Plus = false;
    //private final Set<Person> PersonList = new HashSet<>();
    
    public ButtonPerson() {
        super();
    }

    public ButtonPerson(String text) {
        super(text);
    }

    public ButtonPerson(String text, Node graphic) {
        super(text, graphic);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        if(person == null)
            this.setVisible(false);
        else{
            this.setVisible(true);
            this.setGraphic(new ImageView(this.getPerson().getPic())); //TODO: Test
        }
        this.person = person;
    }    

    public void setPlus(boolean Plus) {
        if(Plus)
            this.setText("+");
        else
            this.setGraphic(new ImageView(this.getPerson().getPic())); //TODO: Test
        this.Plus = Plus;
    }

    public boolean isPlus() {
        return Plus;
    }
    
/*
    public Set<Person> getPersonList() {
        return PersonList;
    }    
    
    public void addPerson(Person newPerson){
        PersonList.add(newPerson);
    }
    
    public void removePerson(Person oldPerson){
        PersonList.remove(oldPerson);
    }*/
}
