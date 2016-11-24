package officelog;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * @author Zooty
 */
public class ButtonPersonPlus extends ButtonPerson{
    private boolean Plus = false;
    private ArrayList<Person> PlusList = new ArrayList<>();
    
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
            this.setGraphic(new ImageView(this.getPerson().getPic()));     //TODO: Test            
        this.Plus = Plus;
    }

    public boolean isPlus() {
        return Plus;
    }

    public ArrayList<Person> getPlusList() {
        return PlusList;
    }
    
    
    public void addPerson(Person newPerson){
        PlusList.add(newPerson);
    }
    
    public void removePerson(Person oldPerson){
        PlusList.remove(oldPerson);
    }
}