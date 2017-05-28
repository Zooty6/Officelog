package officelog.control;

import officelog.model.Room;
import officelog.model.Person;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * @author Zooty
 */
public class ButtonRoom extends Button implements Serializable {

    private int PplHere;
    private ButtonPerson[] SubButtons;
    private final ArrayList<Person> pplList = new ArrayList<>();
    private Room room;

    /**
     * Creates a new ButtonRoom.
     */
    public ButtonRoom() {
        super();
        SubButtons = null;
        PplHere = 0;
    }

    /**
     * Creates a new ButtonRoom.
     */
    public ButtonRoom(String text) {
        super(text);
        SubButtons = new ButtonPerson[SubButtons.length];
        PplHere = 0;
    }

    /**
     * Creates a new ButtonRoom.
     */
    public ButtonRoom(String text, Node graphic) {
        super(text, graphic);
        SubButtons = new ButtonPerson[SubButtons.length];
        PplHere = 0;
    }

    /**
     * Returns the number of subButtons.
     *
     * @return the number of subButtons.
     */
    public int getMaxSubBtn() {
        return SubButtons.length;
    }

    /**
     * Returns how many people are in this Room right now.
     *
     * @return the number of people are in this Room right now.
     */
    public int getPplHere() {
        return PplHere;
    }

    /**
     * Returns the subButtons assigned to this Room.
     *
     * @return the subButtons assigned to this Room.
     */
    public ButtonPerson[] getSubButtons() {
        return SubButtons;
    }

    /**
     * Returns the logical Room that this Button represents.
     *
     * @return the logical Room that this Button represents.
     */
    public Room getRoom() {
        return room;
    }

    public ArrayList<Person> getPplList() {
        return pplList;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setSubButtons(ButtonPerson[] SubButtons) {
        this.SubButtons = SubButtons;
    }

    public void setPpplHere(int PpplHere) {
        this.PplHere = PpplHere;
    }

    public void clear() {
        pplList.clear();
        PplHere = 0;
        redraw();
    }

    public synchronized void Enter(Person person) {
//        System.out.println(person.getName() + "enters to: " + this.room.getName());
        person.getLocation().getBtnRoom().leave(person);
        person.setLocation(room);
        //pplList.add(person);
        redraw();
    }

    public void leave(Person person) {
        pplList.remove(person);
//        System.out.println(person.getName() + "removed from:" + this.room.getName());
        PplHere--;
        redraw();
    }

    public void addPerson(Person newPerson) {
        pplList.add(newPerson);
        PplHere++;
        redraw();
    }

    public void redraw() {
        //System.out.println("redrawing" + this.room.getName());
        Platform.runLater(() -> {
            if (PplHere <= SubButtons.length) {
                SubButtons[SubButtons.length - 1].setPlus(false);
                int i;
                for (i = 0; i < PplHere; i++) {
                    SubButtons[i].setPerson(pplList.get(i));
                }
                while (i < SubButtons.length) {
                    SubButtons[i++].setPerson(null);
                }
            } else { //PplHere>MaxSubBtn
                for (int i = 0; i < SubButtons.length; i++) {
                    SubButtons[i].setPerson(pplList.get(i));
                }
//                System.out.println("+++++++++++++++++++++++++++++++");
                SubButtons[SubButtons.length - 1].setPlus(true);
                /*
            int i=0;
            for (Person nextperson : pplList) {
                SubButtons[i++].setPerson(nextperson);
                while(i<MaxSubBtn)
                    SubButtons[i++].setPerson(null);
            
            }*/
            }
        });
    }

    @Override
    public String toString() {
        return "Room " + room.getName() + "; pplList=" + pplList;
    }

}
