package officelog;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents an event we log in our office.
 * 
 * @author Zooty
 */
public class Event implements Serializable{
    /**
     * A description of this specific event.
     */
    final private String Type;
    
    /**
     * THe time when this event happened.
     */
    final private Date EventDate;
    
    /**
     * The Person who invoked this event
     */
    final private Person Who;
    
    /**
     * The room where this event happened.
     */
    final private Room Where;

    /**
     * Creates a new event.
     * 
     * @param Type the type of the event.
     * @param Who the person who invoked this event.
     * @param Where the room where the event happened.
     */
    
    public Event(String Type, Person Who, Room Where) {
        this.Type = Type;
        this.Who = Who;
        this.Where = Where;
        EventDate = new Date();
    }
    
    /**
     * Creates a new event with no Room and no Person.
     * 
     * @param Type the type of the event.
     */
    public Event(String Type) {
        // For system events.
        this.Type = Type;
        this.Who = null;
        this.Where = null;
        EventDate = new Date();
    }

    /**
     * Creates a new Person specific event, without a Room.
     * 
     * @param Type the type of the event.
     * @param Who the person who invoked this event.
     */
    public Event(String Type, Person Who) {
        //For Person specific events (if needed).
        this.Type = Type;        
        this.Who = Who;
        this.Where = null;
        EventDate = new Date();
    }

    /**
     * Creates a new Room specific event not including any Person.
     * 
     * @param Type the type of the event.
     * @param Where the room where the event happened.
     */
    public Event(String Type, Room Where) {
        //For Room specific events (if needed).
        this.Type = Type;
        this.Where = Where;
        this.Who = null;
        EventDate = new Date();
    }

    /**
     * 
     * @return the type of this event.
     */
    public String getType() {
        return Type;
    }

    /**
     * 
     * @return the time when this event happened. 
     */
    public Date getEventDate() {
        return EventDate;
    }

    /**
     * 
     * @return the Person involved in this event. Null if the event is a Room specific event. 
     */
    public Person getWho() {
        return Who;
    }

    /**
     * 
     * @return the room where this event happened. Null if no Person is involved.
     */
    public Room getWhere() {
        return Where;
    }
    
    /**
     * Returns a string about this event with this format: "DATE: TYPE[: WHERE][: WHO]
     * 
     * @return the string of this event.
     */
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
