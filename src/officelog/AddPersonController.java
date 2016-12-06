package officelog;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Zooty
 */
public class AddPersonController implements Initializable {

    private static Model model;
    @FXML
    private AnchorPane MainWindow;
    @FXML
    private Label lbName;
    @FXML
    private TextField tfName;
    @FXML
    private Button btnSelPic;
    @FXML
    private CheckBox cbEmp;
    @FXML
    private ListView<Room> lvLeft;
    private ObservableList<Room> lvLeftItems = FXCollections.observableArrayList();
    @FXML
    private ListView<Room> lvRight;
    ObservableList<Room> lvRightItems = FXCollections.observableArrayList();
    @FXML
    private Button btnToLeft;
    @FXML
    private Button btnToRight;
    @FXML
    private Label lbLeft;
    @FXML
    private Label lbRight;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSubmit;
    @FXML
    private TextField tfJob;
    @FXML
    private Label lbJob;
    private BufferedImage NewImg = null;
    
    @FXML
    private void handleAction(ActionEvent event) {
        if (event.getSource() == cbEmp){
            lvLeft.setVisible(cbEmp.selectedProperty().get());
            lvRight.setVisible(cbEmp.selectedProperty().get());
            btnToRight.setVisible(cbEmp.selectedProperty().get());
            btnToLeft.setVisible(cbEmp.selectedProperty().get());
            lbLeft.setVisible(cbEmp.selectedProperty().get());
            lbRight.setVisible(cbEmp.selectedProperty().get());
            lbJob.setVisible(cbEmp.selectedProperty().get());
            tfJob.setVisible(cbEmp.selectedProperty().get());
        }
        if (event.getSource() == btnToRight){
            lvRightItems.addAll(lvLeft.getSelectionModel().getSelectedItems());
            lvLeftItems.removeAll(lvLeft.getSelectionModel().getSelectedItems());
        }
        if (event.getSource() == btnToLeft){            
            lvLeftItems.addAll(lvRight.getSelectionModel().getSelectedItems());
            lvRightItems.removeAll(lvRight.getSelectionModel().getSelectedItems());
        }
        if (event.getSource() == btnSelPic){
            NewImg = null; //TODO: set image
        }
        if (event.getSource() == btnCancel){
            ((Stage)(btnCancel.getScene().getWindow())).close();
        }
        if (event.getSource() == btnSubmit) {
            if ("".equals(tfName.getText())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText("Ooops, there was an error!");
                alert.showAndWait();
            } else if (cbEmp.selectedProperty().get()) {
                if (tfJob.getText().equals("")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Look, an Error Dialog");
                    alert.setContentText("Ooops, there was an error!");
                    alert.showAndWait();
                } else {
                    Room[] per = new Room[lvRightItems.size()];
                    int i = 0;
                    for (Room room : per) {
                        per[i] = lvRightItems.get(i);
                        i++;
                    }
                    if (NewImg == null) {
                        model.getPeople().getPerson(model.getPeople().addEmployee(tfName.getText(), tfJob.getText(), per)).setLocation(model.getRoom("Outside"));
                        ((Stage)(btnSubmit.getScene().getWindow())).close();
                    }else{
                        model.getPeople().getPerson(model.getPeople().addEmployee(tfName.getText(), NewImg, tfJob.getText(), per)).setLocation(model.getRoom("Outside"));
                        ((Stage)(btnSubmit.getScene().getWindow())).close();
                    }
                }
            }else{// if Person (not employee)
                if(NewImg == null){
                    model.getPeople().getPerson(model.getPeople().addPerson(tfName.getText())).setLocation(model.getRoom("Outside"));
                    ((Stage)(btnSubmit.getScene().getWindow())).close();
                }else{
                    model.getPeople().getPerson(model.getPeople().addPerson(tfName.getText(),NewImg)).setLocation(model.getRoom("Outside"));
                    ((Stage)(btnSubmit.getScene().getWindow())).close();
                }
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvRight.setItems(lvRightItems);
        lvLeft.setItems(lvLeftItems);
        lvRight.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvLeft.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (Room room : model.getOffice()) {
            lvLeftItems.add(room); // TODO language            
        }
        lvLeftItems.remove(model.getRoom("Outside"));
        // TODO language
    }  

    public static void setModel(Model model) {
        AddPersonController.model = model;
    }
}
