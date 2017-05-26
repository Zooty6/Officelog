package officelog.model;

import Messages.PersonTemplate;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import connections.DBConnection;
import static connections.DBConnection.PASSW;
import static connections.DBConnection.USER;
import javafx.scene.control.Alert;
import static connections.DBConnection.URL;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * This class represents a person in the model.
 *
 * @author Zooty
 */
public class Person implements Serializable, DBConnection {

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
    private String Pic;

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
        this.Location = null;
        this.Pic = "icons\\Default.png";
        /*
        try {
            setPic(ImageIO.read(new File("icons\\Default.png")));
        } catch (IOException e) {
            System.out.println("failed to load "+Name+"'s icon");
         */
    }

    /**
     * Creates a Person.
     *
     * @param Name The name of the Person
     * @param Location The location of this person
     * @param pic The picture assigned to this Person. Must be NxN
     * @param ID The unique Id of the person. Serves as a primary key.
     *
     * @throws IllegalArgumentException if picture is not NxN.
     */
    public Person(String Name, Room Location, BufferedImage pic, int ID) {
        this.Name = Name;
        this.Location = Location;
        this.ID = ID;
        if (pic == null) {
            this.Pic = "icons\\Default.png";
        } else {
            if (pic.getWidth() != pic.getHeight()) {
                throw new IllegalArgumentException("Icon is not NxN");
            }
            File savefile = new File("icons\\" + this.ID + ".png");
            try {
                ImageIO.write(pic, "png", savefile);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            Pic = savefile.getPath();
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
        this.ID = ID;
        if (pic.getWidth() != pic.getHeight()) {
            throw new IllegalArgumentException("Icon is not NxN");
        }
        File savefile = new File("icons\\" + this.ID + ".png");
        try {
            ImageIO.write(pic, "png", savefile);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Pic = savefile.getPath();
        System.out.println(Pic);
        this.Location = null;
    }

    /**
     * Creates a Person.
     *
     * @param Name The name of the Person
     * @param Location The location of this person
     * @param ID The unique Id of the person. Serves as a primary key
     */
    public Person(String Name, Room Location, int ID) {
        this.Name = Name;
        this.Location = Location;
        this.ID = ID;
        this.Pic = "icons\\Default.png";
    }
    
    public Person(PersonTemplate tmpl, Room room) throws IOException {
        this(tmpl.getName(), room, ImageIO.read(new ByteArrayInputStream(tmpl.getPic())), tmpl.getID());
        
    }   

    /**
     * Sets the picture of the Person.
     *
     * @param pic the new picture of the person.
     */
    public void setPic(BufferedImage pic) {
        if (pic.getWidth() != pic.getHeight()) {
            throw new IllegalArgumentException("Icon is not NxN");
        }
        File savefile = new File("icons\\" + this.ID + ".png");
        try {
            ImageIO.write(pic, "png", savefile);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Pic = savefile.getPath();
        System.out.println(pic);
    }    

    /**
     * Sets the current location of the specific person. Also adds the Person to the button's list
     * assigned to this location.
     *
     * @param newLoc The new location this person will be at.
     */
    public void setLocation(Room newLoc) {

        if (!this.Location.equals(newLoc)) {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)) {
                Statement stm = conn.createStatement();               
                stm.executeUpdate(SQLUPDATEPEOPLE3FIRST + newLoc.getName() + "'" + SQLUPDATEPEOPLE3SECOND + this.ID);                
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Officelog");
                alert.setHeaderText("SQL Error");
                alert.setContentText("There was an error connecting to the database");
                alert.showAndWait();
            }
        }
        this.Location = newLoc;
        newLoc.getBtnRoom().addPerson(this);
        
    }

    /**
     * Returns the picture of the person.
     *
     * @return the picture of the person.
     */
    public BufferedImage getPic() {
        BufferedImage r;
        try {
            r = ImageIO.read(new File(Pic));
        } catch (IOException e) {
            System.out.println("failed to load " + Name + "'s icon");
            r = null;
        }
        return r;
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
        return "ID: " + ID + ", Name: " + Name;
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
