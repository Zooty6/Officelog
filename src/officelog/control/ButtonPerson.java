package officelog.control;

import officelog.model.Person;
import java.io.Serializable;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


/**
 * A button class that represents a Person on the GUI.
 * 
 * @author Zooty
 */
public class ButtonPerson extends Button implements Serializable{
    /**
     * The person this Button represents on the GUI.
     */
    private Person person;
    
    /**
     * Indicates if there are more people in the room that can be handled by its ButtonPersons.
     * If true, the Button changes function, and instead of selecting one person, pups up a list
     * of everyone currently in the Room, so the user can choose one.
     */
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

    /**
     * Returns the person this Button represents on the GUI.
     * 
     * @return the person this Button represents on the GUI.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the person this Button represents on the GUI.
     * 
     * @param person the person this Button will represent on the GUI.
     */
    public void setPerson(Person person){
        if(person == null){
            this.setVisible(false);
        }
        else{
            this.setVisible(true);
            /*
            BufferedImage a = null;
            Image b = a;           
            Image whydontyouwork = ImageIO.read(new File("icons\\Default.png"));
            Image image = SwingFXUtils.toFXImage(ImageIO.read(new File("icons\\Default.png")), null ); //WTF
            */
            ImageView btnImage = new ImageView(SwingFXUtils.toFXImage(person.getPic(), null ));
            btnImage.setFitHeight(22);
            btnImage.setFitWidth(22);
            this.setGraphic(btnImage); //TODO: Test
        }
        this.person = person;
    }    

    /**
     * Set true if there are more people in the room that can be represented by BurronPersons.
     * 
     * @param Plus 
     */
    public void setPlus(boolean Plus) {
        if(Plus){
            this.setText("+");
            this.setGraphic(null);
        }
        else
            this.setText("");
            //this.setIcon(new ImageIcon(this.getPerson().getPic())); //TODO: Test
        this.Plus = Plus;
    }

    /**
     * Returns true if this Button represents more than one person.
     * 
     * @return true if this Button represents more than one person.
     */
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
