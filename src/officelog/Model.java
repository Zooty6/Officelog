package officelog;

import java.util.ArrayList;

/**
 * @author Zooty
 */
public class Model {
    private People people = new People();
    private EventList eventList = new EventList();
    private ArrayList<Room> office = new ArrayList<>();
    
    public Model(){
        eventList.addEvent(new Event("Officelog has started"));
        BuildOffice();
        
        
    }

    private void BuildOffice() {
        ; //TODO: Build up the model of the Office (add specific Rooms to office)
    }
}
