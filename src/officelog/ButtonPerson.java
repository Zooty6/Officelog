package officelog;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


/**
 * @author Zooty
 */
public class ButtonPerson extends Button{
    private Person person;
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
