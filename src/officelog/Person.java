package officelog;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;

/**
 * This class represents a person in the model.
 * 
 * @author Zooty
 */
public class Person implements Serializable{
    
    /**
     * Language specific string for "Name"
     */
    protected String NameString="Name";

    /**
     * This String stores the name of the Person.
     */
    final private String Name;
    
    /**
     * The current location of the Person.
     */
    private Room Location;
    
    /**
     * The current picture of the Person.
     */
    private BufferedImage Pic;
    
    /**
     * The unique ID of the Person. Used as a primary key in the collection.
     */
    final private int ID;

    /**
     * Creates a Person with default picture.
     * 
     * @param Name The name of the Person.
     * @param ID The unique Id of the person. Serves as a primary key.
     */
    public Person(String Name, int ID) {
        this.Name = Name;
        this.ID = ID;
        this.Location=null;
        try {
            setPic(ImageIO.read(new File("icons\\Default.png")));
        } catch (IOException e) {
            System.out.println("failed to load "+Name+"'s icon");
        }
    }
    
    /**
     * Creates a Person.
     * 
     * @param Name The name of the Person
     * @param pic The picture assigned to this Person. Must be NxN
     * @param ID The unique Id of the person. Serves as a primary key.
     * 
     * @throws IllegalArgumentException if picture is not NxN.
     */
    public Person(String Name, BufferedImage pic, int ID) {
        this.Name = Name;
        if(pic.getWidth()!=pic.getHeight())
            throw new IllegalArgumentException("Icon is not NxN");
        this.Pic = pic;
        this.ID = ID;
        this.Location=null;
    }

    /**
     * Set the "Name" string according to the selected language.
     * 
     * @param NameString "Name" in the selected language.
     */
    public void setNameString(String NameString) {
        this.NameString = NameString;
    }    

    /**
     * Sets the picture of the Person.
     * 
     * @param pic the new picture of the person.
     */
    public void setPic(BufferedImage pic) {
        if(pic.getWidth()!=pic.getHeight())
            throw new IllegalArgumentException("Icon is not NxN");
        this.Pic = pic;
    }
    
    /**
     * Sets the current location of the specific person. Also adds the Person to the button's list
     * assigned to this location.
     * 
     * @param newLoc The new location this person will be at.
     */
    public void setLocation(Room newLoc){
        this.Location = newLoc;
        newLoc.getBtnRoom().addPerson(this);
    }

    /**     
     * Returns the picture of the person.
     * 
     * @return the picture of the person.
     */
    public BufferedImage getPic() {
        return Pic;
    }
    /**
     * Returns the current location of the person.
     * 
     * @return the current location of the person.
     */
    public Room getLocation() {
        return Location;
    }

    /**
     * Returns the name of the person.
     * 
     * @return the name of the person.
     */
    public String getName() {
        return Name;
    }

    /**
     * Returns the unique ID of the person.
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

    /**
     * Returns a String of the ID and name of the Person.
     * 
     * @return a String of the ID and name of the Person.
     */
    @Override
    public String toString() {
        return "ID: " + ID + ", " + NameString + ": "+ Name;
    }
    
}
///**
// * Exception for wrong pictures. 
// */
//class WrongDimensionException extends Exception {
//   public WrongDimensionException(String msg){
//      super(msg);
//   }
//}