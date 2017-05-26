package officelog.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Collection of the events happens in the office.
 *
 * @author Zooty
 */
public class EventList implements Serializable {

    /**
     * collection for the events
     */
    private List<Event> Elist;
    private int Cicle;
    private final String[] wgat = {"What's a pupper?", "A little doggo.", "What's a doggo?", "A good ol' pupper."};

    /**
     * Creates a list for events. Loads previous list if found.
     */
    public EventList() {
        File dir = new File("EventLog");
        if(!dir.exists())
            dir.mkdir();
        Elist = new ArrayList<>();
        Cicle = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date d = new Date();
        ObjectInputStream ois = null;
        File f = new File("EventLog\\" + dateFormat.format(d) + "(0).dat");
        if (f.exists() && !f.isDirectory()) {
            try {
                Cicle=1;
                ois = new ObjectInputStream(new FileInputStream(f));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        File file = new File("EventLog\\" + dateFormat.format(d) + "(1).dat");
        if (file.exists() && !file.isDirectory()) {
            try {
                Cicle=2;
                ois = new ObjectInputStream(new FileInputStream(file));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        file = new File("EventLog\\" + dateFormat.format(d) + "(2).dat");
        if (file.exists() && !file.isDirectory()) {
            try {
                Cicle=3;
                ois = new ObjectInputStream(new FileInputStream(file));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        file = new File("EventLog\\" + dateFormat.format(d) + "(3).dat");
        if (file.exists() && !file.isDirectory() && !(f.exists() && !f.isDirectory())) {
            try {
                Cicle=0;
                ois = new ObjectInputStream(new FileInputStream(file));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (ois != null) {
            try {
                Elist = (List<Event>) ois.readObject();
            } catch (ClassCastException ex){
                System.out.println(ex.getMessage());                
            }catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                ois.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }        
    }

    /**
     * Returns the list of the events.
     * 
     * @return the list of the events.
     */
    public List<Event> getElist() {
        return Elist;
    }    

    /**
     * Adds an event to the list.
     *
     * @param event the event that gets added.
     */
    public void addEvent(Event event) {
        Elist.add(event);
    }

    /**
     * Saves the list of the events in a file to the place of an old file from 2 periods before.
     */
    public void Save() {        //TODO: CREATE FOLDER FOR EVERY DAY
//        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
//        Date d = new Date();
//        File zombie; //https://www.youtube.com/watch?v=4e4bAsQ4r30
//        zombie = new File("EventLog\\" + dateFormat.format(d) + "(" + (Cicle+2)%4 + ").dat");
//        if (zombie.exists() && !zombie.isDirectory())
//            zombie.delete();
//        File f = new File("EventLog\\" + dateFormat.format(d) + "(" + Cicle + ").dat");
//        System.out.println(wgat[Cicle++]);
//        Cicle = Cicle == 4 ? 0 : Cicle;
//        ObjectOutputStream oos = null;
//        try {
//            oos = new ObjectOutputStream(new FileOutputStream(f));            
//                oos.writeObject(Elist);            
//        } catch (NotSerializableException e) {
//            System.out.println("Something is not serializable");
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            if (oos != null) {
//                try {
//                    oos.close();
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }
//        //Clear();
    }

    /**
     * Clears the list of the events.
     */
    public void Clear() {
        Elist.clear();
    }

}
