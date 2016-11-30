package officelog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Collection of the events happens in the office. TODO: javadoc
 * 
 * @author Zooty
 */
public class EventList implements Serializable{
    
    /**
     * collection for the events
     */
    private final List<Event> Elist;   
    private int Cicle;
    private final String[] wgat = {"What's a pupper?","A little doggo.","What's a doggo?","A good ol' pupper."};

    public EventList() {
        Elist = new ArrayList<>();
        Cicle = 0;
    }
    
    public void addEvent(Event event){
        Elist.add(event);
    }
    
    public void Save(){        //TODO: CREATE FOLDER FOR EVERY DAY
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date d = new Date();
        File f = new File("EventLog\\"+dateFormat.format(d)+"("+Cicle%2+").dat"); 
        System.out.println(wgat[Cicle++]);
        Cicle = Cicle == 4 ? 0 : Cicle;
        ObjectOutputStream oos = null;
        
        try {
            oos = new ObjectOutputStream(new FileOutputStream(f));
            for (Event event : Elist) 
                oos.writeObject(event);            
        }
        catch(NotSerializableException e) {
            System.out.println("Something is not serializable");
        }        
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if (oos != null) {
                try {
                    oos.close();
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }        
        //Clear();
    }

    private void Clear() {
        Elist.clear();
    }
    
}