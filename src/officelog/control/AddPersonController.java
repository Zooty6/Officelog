package officelog.control;

import officelog.view.Language;
import officelog.model.Room;
import officelog.model.Model;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import officelog.model.Event;
import org.w3c.dom.NodeList;

/**
 * FXML Controller class
 *
 * @author Zooty
 */
public class AddPersonController implements Initializable {

    private static Model model;
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
    @FXML
    private ImageView ivIcon;
    private BufferedImage NewImg = null;
    private String ErrorTitle;
    private String ErrorName;
    private String ErrorJob;
    private String ErrorPic;
    private String ErrorOpen;
    
    /**
     * Handles actions on the GUI.
     * 
     * @param event 
     */
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
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null)
                try {
                    if(ImageIO.read(selectedFile).getWidth() == ImageIO.read(selectedFile).getHeight()){
                        NewImg = ImageIO.read(selectedFile);
                        ivIcon.setImage(SwingFXUtils.toFXImage(NewImg, null));
                    }
                    else{
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Officelog");
                        alert.setHeaderText(ErrorTitle);
                        alert.setContentText(ErrorPic);                        
                        alert.showAndWait();
                    }                    
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Officelog");
                alert.setHeaderText(ErrorTitle);
                alert.setContentText(ErrorOpen);
                alert.showAndWait();
            }
        }
        if (event.getSource() == btnCancel){
            ((Stage)(btnCancel.getScene().getWindow())).close();
        }
        if (event.getSource() == btnSubmit) {
            int newPersonID;
            if ("".equals(tfName.getText())) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Officelog");
                alert.setHeaderText(ErrorTitle);
                alert.setContentText(ErrorName);
                alert.showAndWait();
            } else if (cbEmp.selectedProperty().get()) {
                if (tfJob.getText().equals("")) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Officelog");
                    alert.setHeaderText(ErrorTitle);
                    alert.setContentText(ErrorJob);
                    alert.showAndWait();
                } else {
                    Room[] per = new Room[lvRightItems.size()];
                    int i = 0;
                    for (Room room : lvRightItems) {
                        per[i++] = room;                        
                    }
                    if (NewImg == null) {
                        newPersonID = model.getPeople().addEmployee(tfName.getText(), tfJob.getText(), per);
                        model.getPeople().getPerson(newPersonID).setLocation(model.getRoom("Outside"));
                        model.getEventList().addEvent(new Event("New Person", model.getPeople().getPerson(newPersonID)));
                        ((Stage)(btnSubmit.getScene().getWindow())).close();
                    }else{
                        newPersonID = model.getPeople().addEmployee(tfName.getText(), NewImg, tfJob.getText(), per);
                        model.getPeople().getPerson(newPersonID).setLocation(model.getRoom("Outside"));
                        model.getEventList().addEvent(new Event("New Person", model.getPeople().getPerson(newPersonID)));
                        ((Stage)(btnSubmit.getScene().getWindow())).close();
                    }
                }
            }else{// if Person (not employee)
                
                if(NewImg == null){
                    newPersonID = model.getPeople().addPerson(tfName.getText());
                    model.getPeople().getPerson(newPersonID).setLocation(model.getRoom("Outside"));
                    model.getEventList().addEvent(new Event("New Person", model.getPeople().getPerson(newPersonID)));
                    ((Stage)(btnSubmit.getScene().getWindow())).close();
                }else{
                    newPersonID = model.getPeople().addPerson(tfName.getText(),NewImg);
                    model.getPeople().getPerson(newPersonID).setLocation(model.getRoom("Outside"));
                    model.getEventList().addEvent(new Event("New Person", model.getPeople().getPerson(newPersonID)));
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
            lvLeftItems.add(room);
        }
        lvLeftItems.remove(model.getRoom("Outside"));
        try {
            NewImg = ImageIO.read(new File("icons\\Default.png"));
            ivIcon.setImage(SwingFXUtils.toFXImage(NewImg,null));
        } catch (IOException e) {
            System.out.println("failed to load default icon");
        }        
        
        try {            
            NodeList nList = Language.getLang().getElementsByTagName("newperson").item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                if (nList.item(i).hasAttributes()) {
                    switch (nList.item(i).getAttributes().getNamedItem("name").getNodeValue()) {
                        case "nameString":
                            lbName.setText(nList.item(i).getTextContent() + ":");
                            break;
                        case "selimgString":
                            btnSelPic.setText(nList.item(i).getTextContent());
                            break;
                        case "employeeString":
                            cbEmp.setText(nList.item(i).getTextContent());
                            break;
                        case "leftString":
                            lbLeft.setText(nList.item(i).getTextContent());
                            break;
                        case "rightString":
                            lbRight.setText(nList.item(i).getTextContent());
                            break;
                        case "cancelString":
                            btnCancel.setText(nList.item(i).getTextContent());
                            break;
                        case "submitString":
                            btnSubmit.setText(nList.item(i).getTextContent());
                            break;
                        case "jobString":
                            lbJob.setText(nList.item(i).getTextContent()+':');
                            break;
                        case "ErrorTitle":
                            ErrorTitle = nList.item(i).getTextContent();
                            break;
                        case "ErrorName":
                            ErrorName = nList.item(i).getTextContent();
                            break;
                        case "ErrorJob":
                            ErrorJob = nList.item(i).getTextContent();
                            break;
                        case "ErrorPic":
                            ErrorPic = nList.item(i).getTextContent();
                            break;
                        case "ErrorOpen":
                            ErrorOpen = nList.item(i).getTextContent();
                            break;
                    }
                }
            }
        }catch(Exception e){
            Alert lalert = new Alert(Alert.AlertType.ERROR);        
            lalert.setTitle("Officelog");
            lalert.setHeaderText("Fatal Error");
            lalert.setContentText("Could not load Language file");
            lalert.showAndWait();
            throw e;
        }
    }  

    public static void setModel(Model model) {
        AddPersonController.model = model;
    }
}