package officelog;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * @author Zooty
 */
public class ButtonPerson extends Button{
    private Person person;
    
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
    
    
    
}
