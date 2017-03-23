package officelog.control;

import officelog.view.Language;
import officelog.model.People;
import officelog.model.Person;
import officelog.model.Event;
import officelog.model.Model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import officelog.DBConnection;
import org.w3c.dom.NodeList;

/**
 * Controller for the main window.
 *
 * @author Szandi, Zooty
 */
public class dashboardController implements Initializable, DBConnection {

    private final Model model = new Model();
    private static Person selectedPerson;
    private String SaveString;
    private String labelString;
    private String avgmoveString;
    private String errorString;
    private String avgerrorString;
    private String wipelogtitleString;
    private String wipelogString;
    private String suspGood;
    private String suspPers;
    private String failString;
    private String failsString;
    private String suspmissing;
    private String mostsusptitleString;
    private String saveinttitleString;
    private String saveintString;
    private String saveinterrorString;
    private String madeby;

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
    private final ButtonRoom[] allRooms = new ButtonRoom[20];
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
    MenuItem miAddPerson;
    @FXML
    Label lbSelected;
    @FXML
    Menu mLogs;
    @FXML
    Menu mEdit;
    @FXML
    Menu mHelp;
    @FXML
    MenuItem miModifyPerson;
    @FXML
    MenuItem miDeletePerson;
    @FXML
    MenuItem miSavePerson;
    @FXML
    MenuItem miOpenPerson;
    @FXML
    MenuItem miLanguage;
    @FXML
    MenuItem miFeedback;
    @FXML
    MenuItem miCopyRight;
    @FXML
    MenuItem miLogOpen;
    @FXML
    MenuItem miLogDel;
    @FXML
    MenuItem miSetSave;
    @FXML
    MenuItem miMostSusp;
    @FXML
    MenuItem miAvgMove;
    Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(5000),
            action -> model.getEventList().Save()));

    /**
     * Handles all actions on the main form.
     *
     * @param event the event that called this action.
     */
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() instanceof ButtonRoom) {
            if (((ButtonRoom) (event.getSource())).getRoom().isOpen()
                    || selectedPerson.isAllowed(((ButtonRoom) (event.getSource())).getRoom())) {
                if (((ButtonRoom) (event.getSource())).getRoom().getMaxPeople() == 0
                        || (((ButtonRoom) (event.getSource())).getRoom().getMaxPeople()
                        > ((ButtonRoom) (event.getSource())).getRoom().getBtnRoom().getPplHere())) {
                    ((ButtonRoom) (event.getSource())).Enter(selectedPerson);
                    model.getEventList().addEvent(
                            new Event("Entered", selectedPerson, ((ButtonRoom) (event.getSource())).getRoom()));
                    EnableNeighburs();
                } else {
                    model.getEventList().addEvent(
                            new Event("No more place", selectedPerson, ((ButtonRoom) (event.getSource())).getRoom()));
                    System.out.println("Sorry, we are full :(");
                }
            } else {
                model.getEventList().addEvent(
                        new Event("Acces denied", selectedPerson, ((ButtonRoom) (event.getSource())).getRoom()));
                //System.out.println("GTFO");
            }
            //lbSelected.setText("TODO: " + selectedPerson.getName());
            //System.out.println(((ButtonRoom) (event.getSource())));
        }

        if (event.getSource() instanceof ButtonPerson) {
            //System.out.println(((ButtonPerson)(event.getSource())).getPerson());
            if (((ButtonPerson) (event.getSource())).isPlus()) {
                selectedPerson = ((ButtonPerson) (event.getSource())).getPerson();
                PersonSelecter(event);
                //Thread.currentThread().suspend();
                lbSelected.setText(labelString + ": " + selectedPerson.getName());
                EnableNeighburs();
            } else {
                selectedPerson = ((ButtonPerson) (event.getSource())).getPerson();
                lbSelected.setText(labelString + ": " + selectedPerson.getName());
                EnableNeighburs();
            }
        }

        if (event.getSource() == miAddPerson) {
            try {
                FXMLLoader LoadAddPerson = new FXMLLoader(getClass().getResource("/officelog/view/AddPerson.fxml"));
                System.out.println(LoadAddPerson.getResources());
                Parent AddPersonWindow = (Parent) LoadAddPerson.load();
                Stage stageAP = new Stage();
                stageAP.initModality(Modality.WINDOW_MODAL);
                stageAP.initOwner(((Node) R1).getScene().getWindow());
                stageAP.setScene(new Scene(AddPersonWindow));
                stageAP.setTitle("Officelog");
                stageAP.setResizable(false);
                stageAP.show();
            } catch (IOException ex) {
                System.out.println("Could not load AddPerson.fxml");
            }
        }

        if (event.getSource() == miModifyPerson) {
            FXMLLoader LoadModPerson = new FXMLLoader(getClass().getResource("/officelog/view/ModifyPerson.fxml"));
            try {
                Parent ModPersonWindow = (Parent) LoadModPerson.load();
                Stage stageMP = new Stage();
                stageMP.initModality(Modality.WINDOW_MODAL);
                stageMP.initOwner(((Node) R1).getScene().getWindow());
                stageMP.setMinHeight(666);
                stageMP.setScene(new Scene(ModPersonWindow));
                stageMP.showAndWait();
            } catch (Exception ex) {
                ;
            }
        }

        if (event.getSource() == miDeletePerson) {
            FXMLLoader LoadDelPerson = new FXMLLoader(getClass().getResource("/officelog/view/PersonDeleter.fxml"));
            try {
                Parent DelPersonWindow = (Parent) LoadDelPerson.load();
                Stage stageDP = new Stage();
                stageDP.initModality(Modality.WINDOW_MODAL);
                stageDP.initOwner(((Node) R1).getScene().getWindow());
                stageDP.setResizable(false);
                stageDP.setTitle("officelog");
                stageDP.setScene(new Scene(DelPersonWindow));
                stageDP.show();
                for (ButtonRoom allRoom : allRooms) {
                    allRoom.setDisable(true);
                }
            } catch (IOException ex) {
                ;
            }
        }

        if (event.getSource() == miSavePerson) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("dat file (*.dat)", "*.dat"));
            File file = fileChooser.showSaveDialog(R1.getScene().getWindow());
            ObjectOutputStream oos = null;
            if (file != null) {
                try {
                    oos = new ObjectOutputStream(new FileOutputStream(file));
                    oos.writeObject(model.getPeople());
                } catch (NotSerializableException e) {
                    System.out.println("Something is not serializable");
                    System.out.println(e.getMessage());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                } finally {
                    try {
                        oos.close();
                    } catch (IOException ex) {
                        System.out.println("Can't close stuff! " + ex.getMessage());
                    }
                }
            }
        }

        if (event.getSource() == miOpenPerson) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterDAT = new FileChooser.ExtensionFilter("dat files (*.dat)", "*.DAT");
            fileChooser.getExtensionFilters().add(extFilterDAT);
            File file = fileChooser.showOpenDialog(R1.getScene().getWindow());
            ObjectInputStream ois = null;
            if (file != null) {
                try {
                    ois = new ObjectInputStream(new FileInputStream(file));
                    model.setPeople((People) (ois.readObject()));
                    selectedPerson = null;
                    lbSelected.setText(labelString + ": ");
                    for (ButtonRoom allRoom : allRooms) {
                        allRoom.setDisable(true);
                    }
                } catch (NotSerializableException e) {
                    System.out.println("Something is not serializable");
                    System.out.println(e.getMessage());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                } catch (ClassNotFoundException ex) {
                    System.out.println("wrong .dat file");
                } finally {
                    try {
                        ois.close();
                    } catch (IOException ex) {
                        System.out.println("Can't close stuff! " + ex.getMessage());
                    }
                }
            }
//            for (Person allPpl : model.getPeople().getIPeople()) {
//                System.out.println(allPpl);
//            }
            FixNewModel();
        }

        if (event.getSource() == miLanguage) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterXML = new FileChooser.ExtensionFilter("xml files (*.dat)", "*.XML");
            fileChooser.getExtensionFilters().add(extFilterXML);
            fileChooser.setInitialDirectory(new File("lang"));
            File f = fileChooser.showOpenDialog(R1.getScene().getWindow());
            if (f != null) {
                Language.load(f);
                LoadLanguage();
            }

        }

        if (event.getSource() == miCopyRight) {
            Alert copyright = new Alert(Alert.AlertType.INFORMATION);
            copyright.setTitle("Officelog");
            copyright.setHeaderText(madeby + ":");
            copyright.setContentText("Mészáros Szandra \nKecskeméthy Zoltán");
            copyright.show();
        }

        if (event.getSource() == miFeedback) {
            Alert copyright = new Alert(Alert.AlertType.INFORMATION);
            copyright.setTitle("Officelog");
            copyright.setHeaderText("Feedback");
            copyright.setContentText("szandra.meszaros@codevision.hu");
            copyright.show();
        }

        if (event.getSource() == miLogOpen) {
            try {
                Parent OpenLogwindow = FXMLLoader.load(getClass().getResource("/officelog/view/LogViewer.fxml"));
                //Parent OpenLogWindow = OpenLog.load();
                Stage stageOL = new Stage();
                stageOL.setScene(new Scene(OpenLogwindow));
                stageOL.setTitle("Officelog");
                stageOL.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
                
        }

        if (event.getSource() == miLogDel) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Officelog");
            alert.setHeaderText(wipelogtitleString);
            alert.setContentText(wipelogString);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                model.getEventList().Clear();
            }
        }

        if (event.getSource() == miSetSave) {
            TextInputDialog dialog = new TextInputDialog("5");
            dialog.setTitle("Officelog");
            dialog.setHeaderText(saveinttitleString);
            dialog.setContentText(saveintString + ":");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int sec = Integer.parseInt(result.get());
                    if (sec < 5 || sec > 60) {
                        throw new IllegalArgumentException("out of [5,60]");
                    }
                    timeline.stop();
                    timeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(sec), action -> model.getEventList().Save()));
                    timeline.play();
                } catch (Exception e) {
                    Alert copyright = new Alert(Alert.AlertType.ERROR);
                    copyright.setTitle("Officelog");
                    copyright.setHeaderText(errorString);
                    copyright.setContentText(saveinterrorString);
                    copyright.show();
                }
            }
        }
        if (event.getSource() == miMostSusp) {
            class ferret {

                //Ferrets are cute! http://i.imgur.com/JXUbIdk.mp4
                int id;
                int n;

                public ferret(int id, int n) {
                    this.id = id;
                    this.n = n;
                }
            }
            ArrayList<ferret> foundlist = new ArrayList<>();
            for (Event thisevent : model.getEventList().getElist()) {
                boolean found = false;
                if (thisevent.getType().equals("Acces denied")) {
                    for (ferret catsnake : foundlist) {
                        if (catsnake.id == thisevent.getWho().getID()) {
                            found = true;
                            catsnake.n++;
                            break;
                        }
                    }
                    if (!found) {
                        foundlist.add(new ferret(thisevent.getWho().getID(), 1));
                    }
                }
            }
            String msg = suspGood;
            int baddudemistakes;
            int baddude;
            if (!foundlist.isEmpty()) {
                baddudemistakes = 0;
                baddude = 0;
                for (ferret catsnake : foundlist) {
                    if (baddudemistakes < catsnake.n) {
                        baddude = catsnake.id;
                        baddudemistakes = catsnake.n;
                    }
                }
                try {
                    if(baddudemistakes>1)
                        msg = suspPers + ": \n" + model.getPeople().getPerson(baddude)
                            + " (" + baddudemistakes +" "+ failsString + ")";
                    else
                        msg = suspPers + ": \n" + model.getPeople().getPerson(baddude)
                            + " (" + baddudemistakes +" "+ failString + ")";
                } catch (NullPointerException e) {
                    msg = suspmissing;
                }
            }
            Alert MSPersonAlert = new Alert(Alert.AlertType.INFORMATION);
            MSPersonAlert.setTitle("Officelog");
            MSPersonAlert.setHeaderText(mostsusptitleString);
            MSPersonAlert.setContentText(msg);
            MSPersonAlert.show();
        }

        if (event.getSource() == miAvgMove) {
            int movenmbr = 0;
            for (Event thisevent : model.getEventList().getElist()) {
                if (thisevent.getType().equals("Entered")) {
                    movenmbr++;
                }
            }
            if (movenmbr > 0 && model.getPeople().getNumberOfPpl() > 0) {
                Alert AVGMoveAlert = new Alert(Alert.AlertType.INFORMATION);
                AVGMoveAlert.setTitle("Officelog");
                AVGMoveAlert.setHeaderText(avgmoveString + ":");
                AVGMoveAlert.setContentText(Double.toString((double) movenmbr / model.getPeople().getNumberOfPpl()));
                AVGMoveAlert.show();
            } else {
                Alert AVGMoveAlert = new Alert(Alert.AlertType.ERROR);
                AVGMoveAlert.setTitle("Officelog");
                AVGMoveAlert.setHeaderText(errorString);
                AVGMoveAlert.setContentText(avgerrorString);
                AVGMoveAlert.show();
            }
        }
    }

    /**
     * Enables all ButtonRooms that the selectedPerson can attend to enter. Call this method after a
     * Person moved or a new Person is selected.
     */
    private void EnableNeighburs() {
        for (ButtonRoom allRoom : allRooms) {
            if (allRoom.getRoom().isNeighbor(selectedPerson.getLocation())) {
                allRoom.setDisable(false);
            } else {
                allRoom.setDisable(true);
            }
        }
    }

    public static void setSelectedPerson(Person selPerson) {
        selectedPerson = selPerson;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
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

        R1.setSubButtons(new ButtonPerson[]{r1a, r1b, r1c, r1d});
        for (ButtonPerson BP : R1.getSubButtons()) {
            BP.setPerson(null);
        }
        R2.setSubButtons(new ButtonPerson[]{r2a, r2b, r2c, r2d});
        for (ButtonPerson BP : R2.getSubButtons()) {
            BP.setPerson(null);
        }
        R3.setSubButtons(new ButtonPerson[]{r3a, r3b, r3c, r3d});
        for (ButtonPerson BP : R3.getSubButtons()) {
            BP.setPerson(null);
        }
        R4.setSubButtons(new ButtonPerson[]{r4a, r4b, r4c, r4d});
        for (ButtonPerson BP : R4.getSubButtons()) {
            BP.setPerson(null);
        }
        R5.setSubButtons(new ButtonPerson[]{r5a, r5b, r5c, r5d});
        for (ButtonPerson BP : R5.getSubButtons()) {
            BP.setPerson(null);
        }
        R6.setSubButtons(new ButtonPerson[]{r6a, r6b, r6c, r6d});
        for (ButtonPerson BP : R6.getSubButtons()) {
            BP.setPerson(null);
        }
        R7.setSubButtons(new ButtonPerson[]{r7a, r7b, r7c});
        for (ButtonPerson BP : R7.getSubButtons()) {
            BP.setPerson(null);
        }
        R8.setSubButtons(new ButtonPerson[]{r8a, r8b, r8c, r8d});
        for (ButtonPerson BP : R8.getSubButtons()) {
            BP.setPerson(null);
        }
        R9.setSubButtons(new ButtonPerson[]{r9a, r9b, r9c});
        for (ButtonPerson BP : R9.getSubButtons()) {
            BP.setPerson(null);
        }
        R10.setSubButtons(new ButtonPerson[]{r10a, r10b});
        for (ButtonPerson BP : R10.getSubButtons()) {
            BP.setPerson(null);
        }
        R11.setSubButtons(new ButtonPerson[]{r11a, r11b});
        for (ButtonPerson BP : R11.getSubButtons()) {
            BP.setPerson(null);
        }
        R12.setSubButtons(new ButtonPerson[]{r12a, r12b, r12c});
        for (ButtonPerson BP : R12.getSubButtons()) {
            BP.setPerson(null);
        }
        R13.setSubButtons(new ButtonPerson[]{r13a, r13b});
        for (ButtonPerson BP : R13.getSubButtons()) {
            BP.setPerson(null);
        }
        R14.setSubButtons(new ButtonPerson[]{r14a, r14b});
        for (ButtonPerson BP : R14.getSubButtons()) {
            BP.setPerson(null);
        }
        R15.setSubButtons(new ButtonPerson[]{r15a, r15b});
        for (ButtonPerson BP : R15.getSubButtons()) {
            BP.setPerson(null);
        }
        R16.setSubButtons(new ButtonPerson[]{r16a, r16b, r16c, r16d});
        for (ButtonPerson BP : R16.getSubButtons()) {
            BP.setPerson(null);
        }
        R17.setSubButtons(new ButtonPerson[]{r17a, r17b, r17c, r17d});
        for (ButtonPerson BP : R17.getSubButtons()) {
            BP.setPerson(null);
        }
        R18.setSubButtons(new ButtonPerson[]{r18a, r18b, r18c, r18d});
        for (ButtonPerson BP : R18.getSubButtons()) {
            BP.setPerson(null);
        }
        R19.setSubButtons(new ButtonPerson[]{r19a, r19b, r19c, r19d});
        for (ButtonPerson BP : R19.getSubButtons()) {
            BP.setPerson(null);
        }
        R20.setSubButtons(new ButtonPerson[]{r20a});
        for (ButtonPerson BP : R20.getSubButtons()) {
            BP.setPerson(null);
        }
        allRooms[0] = R1;
        allRooms[1] = R2;
        allRooms[2] = R3;
        allRooms[3] = R4;
        allRooms[4] = R5;
        allRooms[5] = R6;
        allRooms[6] = R7;
        allRooms[7] = R8;
        allRooms[8] = R9;
        allRooms[9] = R10;
        allRooms[10] = R11;
        allRooms[11] = R12;
        allRooms[12] = R13;
        allRooms[13] = R14;
        allRooms[14] = R15;
        allRooms[15] = R16;
        allRooms[16] = R17;
        allRooms[17] = R18;
        allRooms[18] = R19;
        allRooms[19] = R20;

        //</editor-fold>
        //RoomsReload.load(model.getOffice());
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        Language.load("lang\\En.xml");
        PersonSelecterController.LinkToSelectedPerson(lbSelected);
        AddPersonController.setModel(model);
        ModifyPersonController.setModel(model);
        PersonDeleterController.setModel(model);
        LogViewerController.setElist(model.getEventList().getElist());
        LoadLanguage();
//        //TEST
//        model.getPeople().getPerson(model.getPeople().addPerson("Test Elek")).setLocation(model.getRoom("Outside")); //*.*
//        model.getPeople().getPerson(model.getPeople().addPerson("Test Elek1")).setLocation(model.getRoom("R6"));
//        model.getPeople().getPerson(model.getPeople().addPerson("Test Elek2")).setLocation(model.getRoom("R6"));
//        model.getPeople().getPerson(model.getPeople().addPerson("Test Elek3")).setLocation(model.getRoom("R19"));
//        model.getPeople().getPerson(model.getPeople().addPerson("Test Elek4")).setLocation(model.getRoom("R19"));
//        Room[] tmpPerm = {model.getRoom("R2"), model.getRoom("R3"), model.getRoom("R4"), model.getRoom("R5"),
//            model.getRoom("R7"), model.getRoom("R8"), model.getRoom("R9"), model.getRoom("R10"),
//            model.getRoom("R12"), model.getRoom("R13"), model.getRoom("R14"),
//            model.getRoom("R15"), model.getRoom("R16")};
//        try {
//            model.getPeople().getPerson(model.getPeople().addEmployee("Boss Olok", ImageIO.read(new File("icons\\Boss.png")), "Boss", tmpPerm
//            )).setLocation(model.getRoom("R2"));
//        } catch (IOException e) {
//            System.out.println("failed to load an icon");
//        }
//        tmpPerm = null;
//        //\TEST        
    }

    private void PersonSelecter(ActionEvent event) {
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("/officelog/view//PersonSelecter.fxml"));
            PersonSelecterController.setSelectedList(((ButtonPerson) (event.getSource())).getPerson().getLocation().getBtnRoom().getPplList());
            Parent Window = (Parent) load.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
            stage.setScene(new Scene(Window));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Can't load PersonSelecter.fxml");
        }
    }

    /**
     * Sets every component's text string according to the selected language on this window.
     */
    private void LoadLanguage() {
        try {
            NodeList nList = Language.getLang().getElementsByTagName("Room");
            NodeList miList = null;
            NodeList sList = null;
            for (ButtonRoom allRoom : allRooms) {
                allRoom.setDisable(true);
                for (int i = 0; i < nList.getLength(); i++) {
                    //System.out.println(rList.item(i).getAttributes().getNamedItem("name").getNodeValue());
                    if (nList.item(i).getAttributes().getNamedItem("name").getNodeValue().equals(allRoom.getRoom().getName())) {
                        allRoom.getRoom().setLanguageName(nList.item(i).getTextContent());
                    }
                }
                if (allRoom.getRoom().getLanguageName().length() > 8) {
                    allRoom.setText(allRoom.getRoom().getLanguageName().replaceFirst(" ", "\n"));
                } else {
                    allRoom.setText(allRoom.getRoom().getLanguageName());
                }
            }

            nList = Language.getLang().getElementsByTagName("dashboard").item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                if (nList.item(i).getNodeName().equals("label")) {
                    lbSelected.setText(nList.item(i).getTextContent()+':');
                    labelString = nList.item(i).getTextContent();
                }
                if (nList.item(i).getNodeName().equals("MenuItems")) {
                    miList = nList.item(i).getChildNodes();
                }
                if (nList.item(i).getNodeName().equals("Strings")) {
                    sList = nList.item(i).getChildNodes();
                }
            }
            nList = null;
            for (int i = 0; i < miList.getLength(); i++) {
                if (miList.item(i).hasAttributes()) {
                    switch (miList.item(i).getAttributes().getNamedItem("name").getNodeValue()) {
                        case "logs":
                            mLogs.setText(miList.item(i).getTextContent());
                            break;
                        case "openlog":
                            miLogOpen.setText(miList.item(i).getTextContent());
                            break;
                        case "deletelog":
                            miLogDel.setText(miList.item(i).getTextContent());
                            break;
                        case "mostsusp":
                            miMostSusp.setText(miList.item(i).getTextContent());
                            break;
                        case "avgmov":
                            miAvgMove.setText(miList.item(i).getTextContent());
                            break;
                        case "setsavfrq":
                            miSetSave.setText(miList.item(i).getTextContent());
                            break;
                        case "edit":
                            mEdit.setText(miList.item(i).getTextContent());
                            break;
                        case "add":
                            miAddPerson.setText(miList.item(i).getTextContent());
                            break;
                        case "modify":
                            miModifyPerson.setText(miList.item(i).getTextContent());
                            break;
                        case "deleteperson":
                            miDeletePerson.setText(miList.item(i).getTextContent());
                            break;
                        case "openperson":
                            miOpenPerson.setText(miList.item(i).getTextContent());
                            break;
                        case "save":
                            miSavePerson.setText(miList.item(i).getTextContent());
                            break;
                        case "help":
                            mHelp.setText(miList.item(i).getTextContent());
                            break;
                        case "lang":
                            miLanguage.setText(miList.item(i).getTextContent());
                            break;
                        case "feedback":
                            miFeedback.setText(miList.item(i).getTextContent());
                            break;
                        case "copy":
                            miCopyRight.setText(miList.item(i).getTextContent());
                            break;
                        case "madeby":
                            madeby = miList.item(i).getTextContent();
                            break;    
                    }
                }
            }
            for (int i = 0; i < sList.getLength(); i++) {
                if (miList.item(i).hasAttributes()) {
                    switch (sList.item(i).getAttributes().getNamedItem("name").getNodeValue()) {
                        case "save":
                            SaveString = sList.item(i).getTextContent();
                            break;
                        case "avgmoveString":
                            avgmoveString = sList.item(i).getTextContent();
                            break;
                        case "errorString":
                            errorString = sList.item(i).getTextContent();
                            break;
                        case "avgerrorString":
                            avgerrorString = sList.item(i).getTextContent();
                            break;
                        case "wipelogtitleString":
                            wipelogtitleString = sList.item(i).getTextContent();
                            break;
                        case "wipelogString":
                            wipelogString = sList.item(i).getTextContent();
                            break;
                        case "suspGood":
                            suspGood = sList.item(i).getTextContent();
                            break;
                        case "suspPers":
                            suspPers = sList.item(i).getTextContent();
                            break;
                        case "failString":
                            failString = sList.item(i).getTextContent();
                            break;
                        case "failsString":
                            failsString = sList.item(i).getTextContent();
                            break;
                        case "suspmissing":
                            suspmissing = sList.item(i).getTextContent();
                            break;
                        case "mostsusptitleString":
                            mostsusptitleString = sList.item(i).getTextContent();
                            break;
                        case "saveinttitleString":
                            saveinttitleString = sList.item(i).getTextContent();
                            break;
                        case "saveintString":
                            saveintString = sList.item(i).getTextContent();
                            break;
                        case "madeby":
                            madeby = sList.item(i).getTextContent();
                            break;
                        case "saveinterrorString":
                            saveinterrorString = sList.item(i).getTextContent();
                            break;                        
                    }
                }
            }
            System.out.println();
        } catch (Exception e) {
            if (Language.getSrc().equals("lang\\En.xml")) {                
                Alert lalert = new Alert(Alert.AlertType.ERROR);
                lalert.setTitle("Officelog");
                lalert.setHeaderText("Fatal Error");
                lalert.setContentText("Could not load Language file");
                lalert.showAndWait();
                throw e;
            }else{
                e.printStackTrace();
                Language.load("lang\\En.xml");
                LoadLanguage();
            }            
        }
    }

    private void FixNewModel() {
        for (ButtonRoom RoomButton : allRooms) {
            RoomButton.clear();
            for (Person person : model.getPeople().getIPeople()) {
                if (person.getLocation().getName().equals(RoomButton.getRoom().getName())) {
                    person.setLocation(RoomButton.getRoom());
                }
            }
        }
    }
}
//↑ This is a slide. Weeeeeee!
