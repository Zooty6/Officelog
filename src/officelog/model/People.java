package officelog.model;

import connections.ConnectionToServer;
import connections.DBConnection;
import static connections.DBConnection.PASSW;
import static connections.DBConnection.URL;
import static connections.DBConnection.USER;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.Alert;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A collection class for all the people in the office.
 *
 * @author Zooty
 */
public class People implements Serializable, DBConnection {

    /**
     * Collection of the people.
     */
    private Set<Person> IPeople;

    /**
     * Number of the people who are currently in the building (and not outside). Not used, can be
     * deleted from the project!
     */
    private int NumberOfPplInOffice;

    /**
     * Number of all the people in the collection.
     */
    private int NumberOfPpl;

    /**
     * used for calculating the unique ID for every new Person.
     */
    private int MaxID = 1;

    /**
     * Creates a new collection for people, and fetches all data from database.
     */
    public People(Model model) {
        IPeople = new HashSet<>();
        IPeople = FetchPeople(model);
        NumberOfPpl = IPeople.size();
        NumberOfPplInOffice = 0;
        UpdateNumberOfPplInOffice();
    }

    /**
     * Fetches people from the database
     */
    private Set<Person> FetchPeople(Model model) {
        
        try {
            return ConnectionToServer.fetcfPeople(model.getOffice());
        } catch (InterruptedException ex) {
            Logger.getLogger(People.class.getName()).log(Level.SEVERE, null, ex);
        }
        return IPeople; 
    }

    /**
     * Gets a specific Person from the collection with the given ID.
     *
     * @param ID The ID of the Person we want to get.
     * @return The Person with the ID we have given as parameter.
     *
     * @throws NullPointerException if no such Person is found.
     */
    public Person getPerson(int ID) {
        Person Fox = null; //foxes are cute! http://i.imgur.com/cOIrbFk.gif
        for (Person person : IPeople) {
            if (person.getID() == ID) {
                Fox = person;
            }
        }
        if (Fox == null) {
            throw new NullPointerException("No person with such ID");
        }
        return Fox;
    }

    /**
     * Returns the specific person from the collection who has the parameter name. If more people
     * are found, the user must choose one from a dialog window. (Not used)
     *
     * @param name name of the person we want to get
     * @return the person who has the given name
     */
    public Person GetPerson(String name) {
        Person r = null;
        int count = 0;
        for (Person person : IPeople) {
            if (name.equals(person.getName())) {
                r = person;
                count++;
            }
        }
        if (count > 1) {
            ;//TODO: Multiple people are found. Select the right one! Returns the last found rn.
        }
        return r;
    }

    /**
     *
     * @return the number of people in the building.
     */
    public int getNumberOfPplInOffice() {
        return NumberOfPplInOffice;
    }

    /**
     *
     * @return the number of people in the collection.
     */
    public int getNumberOfPpl() {
        return NumberOfPpl;
    }

    /**
     * Returns the list of People.
     *
     * @return the list of People.
     */
    public Set<Person> getIPeople() {
        return IPeople;
    }

    /**
     * Adds a new Person to the collection
     *
     * @param newPerson the person we want to add.
     *
     * @throws IllegalArgumentException if the new Person's ID already exist within the collection.
     */
    private void addPerson(Person newPerson) {
        for (Person person : IPeople) {
            if (person.getID() == newPerson.getID()) {
                throw new IllegalArgumentException("Person with this ID already exist");
            }
        }
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)) {
            conn.setAutoCommit(false);
            PreparedStatement pstmPpl = conn.prepareStatement(SQLINSERTPEOPLE1);
            pstmPpl.setInt(1, newPerson.getID());
            pstmPpl.setString(2, newPerson.getName());
            pstmPpl.setString(3, "Outside");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(newPerson.getPic(), "jpg", baos);
            baos.flush();
            byte[] PersonImageBytes = baos.toByteArray();
            baos.close();
            pstmPpl.setBytes(4, PersonImageBytes);
            pstmPpl.setString(5, newPerson instanceof Employee ? ((Employee) newPerson).getJob() : null);
            pstmPpl.setBoolean(6, false);
            pstmPpl.executeUpdate();
            if (newPerson instanceof Employee) {
                for (Room room : ((Employee) (newPerson)).getPermissions()) {
                    PreparedStatement pstmPerm = conn.prepareStatement(SQLINSERTPERMISSIONS2);
                    pstmPerm.setInt(1, newPerson.getID());
                    pstmPerm.setString(2, room.getName());
                    pstmPerm.executeUpdate();
                }
            }
            conn.commit();
            IPeople.add(newPerson);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Officelog");
            alert.setHeaderText("SQL Error");
            alert.setContentText("There was an error connecting to the database");
            alert.showAndWait();
            System.exit(1);
        }
        MaxID = newPerson.getID() + 1;
        NumberOfPpl = IPeople.size();
    }

    /**
     * Adds a new Person with given name.
     *
     * @param Name the name of the new Person.
     *
     * @return the unique ID of the new Person.
     */
    public int addPerson(String Name, Room loc) {
        addPerson(new Person(Name, loc, MaxID));
        return MaxID - 1;
    }

    /**
     * Adds a new Person with given name and picture.
     *
     * @param name the name of the new Person.
     * @param Pic the picture of the new Person.
     *
     * @return the unique ID of the new Person.
     */
    public int addPerson(String name, Room loc, BufferedImage Pic) {
        addPerson(new Person(name, loc, Pic, MaxID));
        return MaxID - 1;
    }

    /**
     * Adds a new Employee with given parameters.
     *
     * @param name the name of the new Employee.
     * @param Job the Job of the new Employee.
     *
     * @return the unique ID of the new Employee.
     */
//    public int addEmployee(String name, String Job) {
//        addPerson(new Employee(name, MaxID, Job));
//        return MaxID - 1;
//    }

    /**
     * Adds a new Employee with given parameters.
     *
     * @param name the name of the new Employee.
     * @param Job the Job of the new Employee.
     * @param per list of permissions this employee can enter.
     *
     * @return the unique ID of the new Employee.
     */
    public int addEmployee(String name, Room loc, String Job, Room[] per) {
        addPerson(new Employee(name, MaxID, loc, Job, per));
        return MaxID - 1;
    }

    /**
     * Adds a new Employee with given parameters.
     *
     * @param name the name of the new Employee.
     * @param pic the picture of the new Employee.
     * @param Job the Job of the new Employee.
     *
     * @return the unique ID of the new Employee.
     */
    public int addEmployee(String name, BufferedImage pic, String Job) {
        addPerson(new Employee(name, pic, MaxID, Job));
        return MaxID - 1;
    }

    /**
     * Adds a new Employee with given parameters.
     *
     * @param name the name of the new Employee.
     * @param pic the picture of the new Employee.
     * @param Job the Job of the new Employee.
     * @param per list of permissions this employee can enter.
     *
     * @return the unique ID of the new Employee.
     */
    public int addEmployee(String name, Room loc, BufferedImage pic, String Job, Room[] per) {
        addPerson(new Employee(name ,MaxID, pic, loc, Job, per));
        return MaxID - 1;
    }

    /**
     * Removes a specific Person from the collection. Does not check if it was in the collection!
     *
     * @param oldPerson the person we want to remove
     *
     * @throws ClassCastException if the type of the specified element is incompatible with this set
     * (optional)
     * @throws NullPointerException if the specified element is null and this set does not permit
     * null elements (optional)
     * @throws UnsupportedOperationException if the remove operation is not supported by this set
     */
    public void removePerson(Person oldPerson) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)) {
            Statement stm = conn.createStatement();
            stm.executeUpdate(SQLUPDATEPEOPLE2 + oldPerson.getID());
            IPeople.remove(oldPerson);
            NumberOfPpl--;
            UpdateNumberOfPplInOffice();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Officelog");
            alert.setHeaderText("SQL Error");
            alert.setContentText("There was an error connecting to the database");
            alert.showAndWait();
        }
    }

    /**
     * Removes a Person from the collection with given ID
     *
     * @param oldPersonID ID of the Person we want to remove
     *
     * @throws IllegalArgumentException If the person wasn't in the list.
     */
    public void removePerson(int oldPersonID) {
        Person otter = null; //otters are cute as well! http://i.imgur.com/TvhgtOs.mp4
        for (Person person : IPeople) {
            if (person.getID() == oldPersonID) {
                IPeople.remove(person);
                otter = person;
                UpdateNumberOfPplInOffice();
                NumberOfPpl--;
            }
        }
        if (otter == null) {
            throw new IllegalArgumentException("This person isn't in the list!");
        }
    }

    /**
     * Moves a specific person to the parameter Room. (This method is not used and can be deleted.)
     *
     * @param person The Person we want to move.
     * @param dRoom The destination Room we move the Person.
     */
    public void Move(Person person, Room dRoom) {
        if ("Outside".equals(person.getLocation().getName())) {
            NumberOfPplInOffice++;
        }
        person.setLocation(dRoom);
        if ("Outside".equals(dRoom.getName())) {
            NumberOfPplInOffice--;
        }
    }

    public final void UpdateNumberOfPplInOffice() {
        NumberOfPplInOffice = 0;
        for (Person person : IPeople) {
            if (!person.getLocation().getName().equals("Outside")) {
                NumberOfPplInOffice++;
            }
        }
    }

    /**
     * Moves a specific person with given ID to the parameter Room. May not need to use this method.
     *
     * @param ID The ID of the Person.
     * @param dRoom The destination Room we move the Person.
     */
    public void Move(int ID, Room dRoom) {
        Move(this.getPerson(ID), dRoom);
    }
}
