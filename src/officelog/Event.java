package officelog;
/**
 * @author Zooty
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Event {
    private String Type;
    private Date EventDate;
    private Person Who;
    private Room Where;

    public Event(String Type, Person who, Room Where) {
        this.Type = Type;
        this.Who = who;
        this.Where = Where;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(EventDate)+": "+Type+": "+Where+": "+Who;
    }
}
