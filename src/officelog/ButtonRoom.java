package officelog;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * @author Zooty
 */
public class ButtonRoom extends Button{
    private int MaxSubBtn;
    private int PpplHere;    
    Room room;

    public ButtonRoom() {
        super();
    }

    public ButtonRoom(String text) {
        super(text);
    }

    public ButtonRoom(String text, Node graphic) {
        super(text, graphic);
    }
    
}
