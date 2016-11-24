package officelog;

import javafx.scene.image.Image;

/**
 * This class represents a person in the model.
 * 
 * @author Zooty
 */
public class Person {

    /**
     * This String stores the name of the Person.
     */
    final private String Name;
    
    /**
     * The current location of the Person
     */
    private Room Location;
    
    /**
     * The current picture of the Person
     */
    private Image Pic;
    
    /**
     * The unique ID of the Person. Used as a primary key in the collection.
     */
    final private int ID;

    /**
     * Creates a Person with default picture.
     * 
     * @param Name The name of the Person
     * @param ID The unique Id of the person. Serves as a primary key.
     */
    public Person(String Name, int ID) {
        this.Name = Name;
        this.ID = ID;
        this.Location=null;
        //TODO: Set This.Pic to default
    }
    /**
     * Creates a Person.
     * 
     * @param Name The name of the Person
     * @param Pic The picture assigned to this Person.
     * @param ID The unique Id of the person. Serves as a primary key.
     */
    public Person(String Name, Image Pic, int ID) {
        this.Name = Name;
        if(Pic.getWidth()!=50 && Pic.getHeight()!=50)
            throw new IllegalArgumentException("Icon is not 50x50");
        this.Pic = Pic;
        this.ID = ID;
        this.Location=null;
        System.out.println("done");
    }

    /**
     * Sets the picture of the Person.
     * 
     * @param pic the new picture of the person.
     */
    public void setPic(Image pic) {
        this.Pic = pic;
    }
    
    /**
     * Sets the current location of the specific person.
     * 
     * @param newLoc The new location this person will be at.
     */
    public void setLocation(Room newLoc){
        this.Location = newLoc;
    }

    /**     
     * 
     * @return the picture of the person.
     */
    public Image getPic() {
        return Pic;
    }
    /**
     * 
     * @return the current location of the person.
     */
    public Room getLocation() {
        return Location;
    }

    /**
     * 
     * @return the name of the person.
     */
    public String getName() {
        return Name;
    }

    /**
     * 
     * @return the unique ID of the person.
     */
    public int getID() {
        return ID;
    }
    
    /**
     * This function tells you if the person is allowed to enter the specific Room.
     * 
     * @param room The specific room the person wants to enter.
     * @return True if the person is allowed to enter.
     */
    public boolean isAllowed(Room room) {
        return false;
    }
}
/**
 * Exception for wrong pictures. 
 */
class WrongDimensionException extends Exception {
   public WrongDimensionException(String msg){
      super(msg);
   }
}