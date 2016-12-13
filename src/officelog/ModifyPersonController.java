package officelog;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Zooty, Szandi
 */
public class ModifyPersonController implements Initializable {

    @FXML
    private ListView<Person> lvPeople;
    private ObservableList<Person> lvPeopleItems = FXCollections.observableArrayList();
    ;
    @FXML
    private Label lbID;
    @FXML
    private Label lbPersonID;
    @FXML
    private Label lbName;
    @FXML
    private Label lbPersonName;
    @FXML
    private Button btnChangePic;
    @FXML
    private ImageView ivPic;
    @FXML
    private ListView<Room> lvLeft;
    private ObservableList<Room> lvLeftItems = FXCollections.observableArrayList();
    ;
    @FXML
    private ListView<Room> lvRight;
    private ObservableList<Room> lvRightItems = FXCollections.observableArrayList();
    ;
    @FXML
    private Button btnToRight;
    @FXML
    private Button btnToLeft;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSubmit;
    @FXML
    private Label lbLeft;
    @FXML
    private Label lbRight;
    @FXML
    private Label lbJob;
    @FXML
    private TextField tfPersonJob;
    private static Model model;
    private BufferedImage NewImg;

    @FXML
    private void handleClickAction(MouseEvent arg0) {
        arg0 = null; //Please don't use arg0
        System.out.println(lvPeople.getSelectionModel().getSelectedItem());
        lbPersonID.setText(Integer.toString(lvPeople.getSelectionModel().getSelectedItem().getID()));
        lbPersonName.setText(lvPeople.getSelectionModel().getSelectedItem().getName());
        ivPic.setImage(SwingFXUtils.toFXImage(lvPeople.getSelectionModel().getSelectedItem().getPic(), null));
        NewImg = lvPeople.getSelectionModel().getSelectedItem().getPic();
        if (lvPeople.getSelectionModel().getSelectedItem() instanceof Employee) {
            btnToLeft.setDisable(false);
            btnToRight.setDisable(false);
            tfPersonJob.setDisable(false);
            lvLeft.setDisable(false);
            lvRight.setDisable(false);
            lvLeftItems.clear();
            lvRightItems.clear();
            tfPersonJob.setText(((Employee) (lvPeople.getSelectionModel().getSelectedItem())).getJob());
            for (Room oneRoom : model.getOffice()) {
                if (((Employee) (lvPeople.getSelectionModel().getSelectedItem())).getPermissions().contains(oneRoom)) {
                    if (!oneRoom.getName().equals("Outside")) {
                        lvRightItems.add(oneRoom);
                    }
                } else {
                    if (!oneRoom.getName().equals("Outside")) {
                        lvLeftItems.add(oneRoom);
                    }
                }
            }
        } else {
            btnToLeft.setDisable(true);
            btnToRight.setDisable(true);
            tfPersonJob.setText("");
            tfPersonJob.setDisable(true);
            lvLeft.setDisable(true);
            lvRight.setDisable(true);
            lvLeftItems.clear();
            lvRightItems.clear();
        }
    }

    @FXML
    private void handleAction(ActionEvent event) {
        if (event.getSource() == btnToRight) {
            lvRightItems.addAll(lvLeft.getSelectionModel().getSelectedItems());
            lvLeftItems.removeAll(lvLeft.getSelectionModel().getSelectedItems());
        }
        if (event.getSource() == btnToLeft) {
            lvLeftItems.addAll(lvRight.getSelectionModel().getSelectedItems());
            lvRightItems.removeAll(lvRight.getSelectionModel().getSelectedItems());
        }
        if (event.getSource() == btnChangePic) {
            FileChooser fileChooser = new FileChooser();
            //FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            ArrayList<String> ext = new ArrayList<>();
            ext.add("*.JPG");
            ext.add("*.PNG");
            FileChooser.ExtensionFilter ImageExt = new FileChooser.ExtensionFilter("Image files", ext);
            ext = null;
            fileChooser.getExtensionFilters().addAll(ImageExt);
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                try {
                    if (ImageIO.read(selectedFile).getWidth() == ImageIO.read(selectedFile).getHeight()) {
                        NewImg = ImageIO.read(selectedFile);
                        ivPic.setImage(SwingFXUtils.toFXImage(NewImg, null));
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Look, an Error Dialog");
                        alert.setContentText("Icon is not NxN!");
                        alert.showAndWait();
                    }
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Look, an Error Dialog");
                    alert.setContentText("Ooops, there was an error!");
                    alert.showAndWait();
                }
            }
        }
        if (event.getSource() == btnCancel) {
            ((Stage) (btnCancel.getScene().getWindow())).close();
        }
        if (event.getSource() == btnSubmit) {
            if (tfPersonJob.getText().equals("") && !tfPersonJob.disableProperty().get()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText("no jub");
                alert.showAndWait();
            }else{
                (lvPeople.getSelectionModel().getSelectedItem()).setPic(NewImg);
                if ((lvPeople.getSelectionModel().getSelectedItem()) instanceof Employee){
                    ((Employee)(lvPeople.getSelectionModel().getSelectedItem())).setJob(tfPersonJob.getText());
                    ((Employee)(lvPeople.getSelectionModel().getSelectedItem())).getPermissions().clear();
                    for (Room room : lvRightItems) {
                        ((Employee)(lvPeople.getSelectionModel().getSelectedItem())).getPermissions().add(room);
                    }
                }
                lvPeople.getSelectionModel().getSelectedItem().getLocation().getBtnRoom().redraw();
                model.getEventList().addEvent(new Event("Person modified", lvPeople.getSelectionModel().getSelectedItem()));
                ((Stage) (btnSubmit.getScene().getWindow())).close();
            }                
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (model.getPeople().getNumberOfPpl() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("no people");
            alert.showAndWait();
            ((Stage) (btnCancel.getScene().getWindow())).close();
        } else {
            for (Person person : model.getPeople().getIPeople()) {
                lvPeopleItems.add(person);
            }
            lvPeople.setItems(lvPeopleItems);
            lvPeople.getSelectionModel().select(0);
            handleClickAction(null);
            lvLeft.setItems(lvLeftItems);
            lvRight.setItems(lvRightItems);
            lvRight.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            lvLeft.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    public static void setModel(Model model) {
        ModifyPersonController.model = model;
    }

}
