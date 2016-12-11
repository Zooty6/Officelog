package officelog;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * @author Zooty
 */
public class ButtonRoom extends Button implements Serializable{
    private int PplHere; 
    private ButtonPerson[] SubRooms;
    private final ArrayList<Person> pplList = new ArrayList<>();
    private Room room;

    public ButtonRoom() {
        super();
        SubRooms = null;
        PplHere = 0;
    }

    public ButtonRoom(String text) {
        super(text);
        SubRooms = new ButtonPerson[SubRooms.length];
        PplHere = 0;
    }

    public ButtonRoom(String text, Node graphic) {
        super(text, graphic);
        SubRooms = new ButtonPerson[SubRooms.length];
        PplHere = 0;
    }

    public int getMaxSubBtn() {
        return SubRooms.length;
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

    public ArrayList<Person> getPplList() {
        return pplList;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setSubRooms(ButtonPerson[] SubRooms) {        
        this.SubRooms = SubRooms;
    }   

    public void setPpplHere(int PpplHere) {
        this.PplHere = PpplHere;
    }    
    
    public void clear(){
        pplList.clear();
        PplHere = 0;
        redraw();
    }
    
    public void Enter (Person person){
        person.getLocation().getBtnRoom().leave(person);
        person.setLocation(room);
        redraw();
    }
     
    public void leave (Person person){
        pplList.remove(person);
        PplHere--;
        redraw();
    }
    
    public void addPerson(Person newPerson){
        pplList.add(newPerson);
        PplHere++;
        redraw();
    }

    public void redraw(){
        if (PplHere<=SubRooms.length){
            SubRooms[SubRooms.length-1].setPlus(false);
            int i;
            for (i = 0; i < PplHere; i++) 
                SubRooms[i].setPerson(pplList.get(i));            
            while(i<SubRooms.length)
                SubRooms[i++].setPerson(null);
        }
        else{ //PplHere>MaxSubBtn
            for (int i = 0; i < SubRooms.length; i++) 
                SubRooms[i].setPerson(pplList.get(i));            
            SubRooms[SubRooms.length-1].setPlus(true);            
            /*
            int i=0;
            for (Person nextperson : pplList) {
                SubRooms[i++].setPerson(nextperson);
                while(i<MaxSubBtn)
                    SubRooms[i++].setPerson(null);
            
            }*/
        }                 
    }

    @Override
    public String toString() {
        return "Room " + room.getName() + "; pplList=" + pplList;
    }
    
}
