package officelog;
/**
 * @author Zooty
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Event {
    final private String Type;
    final private Date EventDate;
    final private Person Who;
    final private Room Where;

    public Event(String Type, Person Who, Room Where) {
        this.Type = Type;
        this.Who = Who;
        this.Where = Where;
        EventDate = new Date();
    }
    
    public Event(String Type) {
        // For system events.
        this.Type = Type;
        this.Who = null;
        this.Where = null;
        EventDate = new Date();
    }

    public Event(String Type, Person Who) {
        //For Person specific events (if needed).
        this.Type = Type;        
        this.Who = Who;
        this.Where = null;
        EventDate = new Date();
    }

    public Event(String Type, Room Where) {
        //For Room specific events (if needed).
        this.Type = Type;
        this.Where = Where;
        this.Who = null;
        EventDate = new Date();
    }

    public String getType() {
        return Type;
    }

    public Date getEventDate() {
        return EventDate;
    }

    public Person getWho() {
        return Who;
    }

    public Room getWhere() {
        return Where;
    }
    
    @Override
    public String toString(){
        //DOES NOT HANDLE ROOM AND PERSON SPECIFIC EVENTS YET!
        String msg="Something went wrong. This event isn't defined.";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if(Who == null && Where == null)
            msg = dateFormat.format(EventDate)+": "+Type;
        if(Who == null && Where != null)
            ;//TODO Room specific event
        if(Who != null && Where == null)
            ;//TODO Person specific event
        if(Who != null && Where != null)
            msg = dateFormat.format(EventDate)+": "+Type+": "+Where+": "+Who;
        return msg;
    }
}
