package officelog;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * @author Zooty
 */
public class ButtonRoom extends Button{
    private int MaxSubBtn;
    private int PplHere; 
    private ButtonPerson[] SubRooms;
    private final ArrayList<Person> pplList = new ArrayList<>();
    private Room room;

    public ButtonRoom() {
        super();
        SubRooms = new ButtonPerson[MaxSubBtn];
        PplHere = 0;
    }

    public ButtonRoom(String text) {
        super(text);
        SubRooms = new ButtonPerson[MaxSubBtn];
        PplHere = 0;
    }

    public ButtonRoom(String text, Node graphic) {
        super(text, graphic);
        SubRooms = new ButtonPerson[MaxSubBtn];
        PplHere = 0;
    }

    public int getMaxSubBtn() {
        return MaxSubBtn;
    }

    public int getPplHere() {
        return PplHere;
    }

    public ButtonPerson[] getSubRooms() {        
        return SubRooms;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setSubRooms(ButtonPerson[] SubRooms) {
        MaxSubBtn = SubRooms.length;
        this.SubRooms = SubRooms;
    }
    
    public void setMaxSubBtn(int MaxSubBtn) {
        this.MaxSubBtn = MaxSubBtn;
        SubRooms = new ButtonPerson[MaxSubBtn];
    }

    public void setPpplHere(int PpplHere) {
        this.PplHere = PpplHere;
    }    
    
    public void Enter (Person person){
        pplList.add(person);
        if(PplHere<MaxSubBtn)
            SubRooms[PplHere++].setPerson(person);
        person.getLocation().getBtnRoom().leave(person);
        person.setLocation(room);
        redraw();
    }
     
    public void leave (Person person){
        pplList.remove(person);
        PplHere--;        
        redraw();
        }

    public void redraw() {
        if (PplHere<=MaxSubBtn){
            SubRooms[MaxSubBtn-1].setPlus(false);
            int i;
            for (i = 0; i < PplHere; i++) 
                SubRooms[i].setPerson(pplList.get(i));            
            while(i<MaxSubBtn)
                SubRooms[i++].setPerson(null);
        }
        else{ //PplHere>MaxSubBtn
            for (int i = 0; i < SubRooms.length; i++) {
                SubRooms[i].setPerson(pplList.get(i));               
            }
            SubRooms[MaxSubBtn-1].setPlus(true);            
            /*
            int i=0;
            for (Person nextperson : pplList) {
                SubRooms[i++].setPerson(nextperson);
                while(i<MaxSubBtn)
                    SubRooms[i++].setPerson(null);
            
            }*/
        }                 
    }
}
