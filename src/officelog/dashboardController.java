package officelog;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author Szandi, Zooty
 */
public class dashboardController implements Initializable {
    
    private final Model model = new Model();
    private Person selectedPerson = null;
   
    //<editor-fold defaultstate="collapsed" desc="linking buttons">
    @FXML
    private ButtonRoom R1;
    @FXML
    private ButtonRoom R2;
    @FXML
    private ButtonRoom R3;
    @FXML
    private ButtonRoom R4;
    @FXML
    private ButtonRoom R5;
    @FXML
    private ButtonRoom R6;
    @FXML
    private ButtonRoom R7;
    @FXML
    private ButtonRoom R8;
    @FXML
    private ButtonRoom R9;
    @FXML
    private ButtonRoom R10;
    @FXML
    private ButtonRoom R11;
    @FXML
    private ButtonRoom R12;
    @FXML
    private ButtonRoom R13;
    @FXML
    private ButtonRoom R14;
    @FXML
    private ButtonRoom R15;
    @FXML
    private ButtonRoom R16;
    @FXML
    private ButtonRoom R17;
    @FXML
    private ButtonRoom R18;
    @FXML
    private ButtonRoom R19;
    @FXML
    private ButtonRoom R20;    
    @FXML
    private ButtonPerson r1a;    
    @FXML
    private ButtonPerson r1b;    
    @FXML
    private ButtonPerson r1c;    
    @FXML
    private ButtonPerson r1d;    
    @FXML
    private ButtonPerson r2a;    
    @FXML
    private ButtonPerson r2b;    
    @FXML
    private ButtonPerson r2c;    
    @FXML
    private ButtonPerson r2d;    
    @FXML
    private ButtonPerson r3a;    
    @FXML
    private ButtonPerson r3b;    
    @FXML
    private ButtonPerson r3c;    
    @FXML
    private ButtonPerson r3d;    
    @FXML
    private ButtonPerson r4a;    
    @FXML
    private ButtonPerson r4b;    
    @FXML
    private ButtonPerson r4c;    
    @FXML
    private ButtonPerson r4d;    
    @FXML
    private ButtonPerson r5a;    
    @FXML
    private ButtonPerson r5b;    
    @FXML
    private ButtonPerson r5c;    
    @FXML
    private ButtonPerson r5d;    
    @FXML
    private ButtonPerson r6a;    
    @FXML
    private ButtonPerson r6b;    
    @FXML
    private ButtonPerson r6c;    
    @FXML
    private ButtonPerson r6d;    
    @FXML
    private ButtonPerson r7a;    
    @FXML
    private ButtonPerson r7b;    
    @FXML
    private ButtonPerson r7c;    
    @FXML
    private ButtonPerson r8a;    
    @FXML
    private ButtonPerson r8b;    
    @FXML
    private ButtonPerson r8c;    
    @FXML
    private ButtonPerson r8d;    
    @FXML
    private ButtonPerson r9a;    
    @FXML
    private ButtonPerson r9b;    
    @FXML
    private ButtonPerson r9c;    
    @FXML
    private ButtonPerson r10a;    
    @FXML
    private ButtonPerson r10b;    
    @FXML
    private ButtonPerson r11a;    
    @FXML
    private ButtonPerson r11b;    
    @FXML
    private ButtonPerson r12a;    
    @FXML
    private ButtonPerson r12b;    
    @FXML
    private ButtonPerson r12c;    
    @FXML
    private ButtonPerson r13a;    
    @FXML
    private ButtonPerson r13b;    
    @FXML
    private ButtonPerson r14a;    
    @FXML
    private ButtonPerson r14b;    
    @FXML
    private ButtonPerson r15a;    
    @FXML
    private ButtonPerson r15b;    
    @FXML
    private ButtonPerson r16a;    
    @FXML
    private ButtonPerson r16b;    
    @FXML
    private ButtonPerson r16c;    
    @FXML
    private ButtonPerson r16d;    
    @FXML
    private ButtonPerson r17a;    
    @FXML
    private ButtonPerson r17b;    
    @FXML
    private ButtonPerson r17c;    
    @FXML
    private ButtonPerson r17d;    
    @FXML
    private ButtonPerson r18a;    
    @FXML
    private ButtonPerson r18b;    
    @FXML
    private ButtonPerson r18c;    
    @FXML
    private ButtonPerson r18d;    
    @FXML
    private ButtonPerson r19a;    
    @FXML
    private ButtonPerson r19b;    
    @FXML
    private ButtonPerson r19c;    
    @FXML
    private ButtonPerson r19d;    
    @FXML
    private ButtonPerson r20a;    
    //</editor-fold>
    
    @FXML
    private void handleButtonAction(ActionEvent event) {        
        if (event.getSource() instanceof ButtonRoom){
            System.out.println(((ButtonRoom)(event.getSource())).getRoom().toString()+" "+((ButtonRoom)(event.getSource())));            
                }
        if (event.getSource() instanceof ButtonPerson){
            System.out.println(((ButtonPerson)(event.getSource())).getPerson());
        }
        /*
        Alert copyright=new Alert(Alert.AlertType.INFORMATION);
        copyright.setTitle("");
        copyright.setHeaderText(R1.getText());
        copyright.setContentText("Kecskeméthy Zoltán \nMészáros Szandra");
        copyright.show();
        */
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //<editor-fold defaultstate="collapsed" desc="linking buttons and Rooms">
        R1.setRoom(model.getRoom("R1"));
        model.getRoom("R1").setBtnRoom(R1);
        R2.setRoom(model.getRoom("R2"));
        model.getRoom("R2").setBtnRoom(R2);
        R3.setRoom(model.getRoom("R3"));
        model.getRoom("R3").setBtnRoom(R3);
        R4.setRoom(model.getRoom("R4"));
        model.getRoom("R4").setBtnRoom(R4);
        R5.setRoom(model.getRoom("R5"));
        model.getRoom("R5").setBtnRoom(R5);
        R6.setRoom(model.getRoom("R6"));
        model.getRoom("R6").setBtnRoom(R6);
        R7.setRoom(model.getRoom("R7"));
        model.getRoom("R7").setBtnRoom(R7);
        R8.setRoom(model.getRoom("R8"));
        model.getRoom("R8").setBtnRoom(R8);
        R9.setRoom(model.getRoom("R9"));
        model.getRoom("R9").setBtnRoom(R9);
        R10.setRoom(model.getRoom("R10"));
        model.getRoom("R10").setBtnRoom(R10);
        R11.setRoom(model.getRoom("R11"));
        model.getRoom("R11").setBtnRoom(R11);
        R12.setRoom(model.getRoom("R12"));
        model.getRoom("R12").setBtnRoom(R12);
        R14.setRoom(model.getRoom("R14"));
        model.getRoom("R14").setBtnRoom(R14);
        R15.setRoom(model.getRoom("R15"));
        model.getRoom("R15").setBtnRoom(R15);
        R16.setRoom(model.getRoom("R16"));
        model.getRoom("R16").setBtnRoom(R16);
        R17.setRoom(model.getRoom("R17"));
        model.getRoom("R17").setBtnRoom(R17);
        R18.setRoom(model.getRoom("R18"));
        model.getRoom("R18").setBtnRoom(R18);
        R19.setRoom(model.getRoom("R19"));
        model.getRoom("R19").setBtnRoom(R19);
        R13.setRoom(model.getRoom("R13"));
        model.getRoom("R13").setBtnRoom(R13);
        R20.setRoom(model.getRoom("Outside"));
        model.getRoom("Outside").setBtnRoom(R20);
        
        R1.setSubRooms(new ButtonPerson[]{r1a,r1b,r1c,r1d});
        R2.setSubRooms(new ButtonPerson[]{r2a,r2b,r2c,r2d});
        R3.setSubRooms(new ButtonPerson[]{r3a,r3b,r3c,r3d});
        R4.setSubRooms(new ButtonPerson[]{r4a,r4b,r4c,r4d});
        R5.setSubRooms(new ButtonPerson[]{r5a,r5b,r5c,r5d});
        R6.setSubRooms(new ButtonPerson[]{r6a,r6b,r6c,r6d});
        R7.setSubRooms(new ButtonPerson[]{r7a,r7b,r7c});
        R8.setSubRooms(new ButtonPerson[]{r8a,r8b,r8c,r8d});
        R9.setSubRooms(new ButtonPerson[]{r9a,r9b,r9c});
        R10.setSubRooms(new ButtonPerson[]{r10a,r10b});
        R11.setSubRooms(new ButtonPerson[]{r11a,r11b});
        R12.setSubRooms(new ButtonPerson[]{r12a,r12b,r12c});
        R13.setSubRooms(new ButtonPerson[]{r13a,r13b});
        R14.setSubRooms(new ButtonPerson[]{r14a,r14b});
        R15.setSubRooms(new ButtonPerson[]{r15a,r15b});
        R16.setSubRooms(new ButtonPerson[]{r16a,r16b,r16c,r16d});
        R17.setSubRooms(new ButtonPerson[]{r17a,r17b,r17c,r17d});
        R18.setSubRooms(new ButtonPerson[]{r18a,r18b,r18c,r18d});
        R19.setSubRooms(new ButtonPerson[]{r19a,r19b,r19c,r19d});
        R20.setSubRooms(new ButtonPerson[]{r20a});
        //</editor-fold>
        //TEST
        model.getPeople().getPerson(model.getPeople().addPerson("Test Elek")).setLocation(model.getRoom("Outside")); //*.*
        

        
        
        //\TEST
        
        // TODO
    }    
    
}
