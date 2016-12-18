package officelog;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.w3c.dom.NodeList;

/**
 * FXML Controller class
 *
 * @author Zooty
 */
public class PersonDeleterController implements Initializable {

    @FXML
    private Button btDelete;
    @FXML
    private Button btCancel;
    @FXML
    private ListView<Person> lvPpl;
    ObservableList<Person> lvPplItems = FXCollections.observableArrayList();
    private static Model model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String ErrorTitle = null;
        String ErrorString = null;
        try {
            NodeList nList = Language.getLang().getElementsByTagName("DelPerson").item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                if (nList.item(i).hasAttributes()) {
                    switch (nList.item(i).getAttributes().getNamedItem("name").getNodeValue()) {
                        case "ErrorTitle":
                            ErrorTitle = nList.item(i).getTextContent();
                            break;
                        case "ErrorString":
                            ErrorString = nList.item(i).getTextContent();
                            break;                        
                        case "cancelString":
                            btCancel.setText(nList.item(i).getTextContent());
                            break;
                        case "submitDel":
                            btDelete.setText(nList.item(i).getTextContent());
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
        lvPplItems.addAll(model.getPeople().getIPeople());
        lvPpl.setItems(lvPplItems);
        if (model.getPeople().getIPeople().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Officelog");
            alert.setHeaderText(ErrorTitle);
            alert.setContentText(ErrorString);
            alert.showAndWait();
            ((Stage) (btCancel.getScene().getWindow())).close();
        }
        lvPpl.getSelectionModel().select(0);
    }

    /**
     * Handles actions on the GUI.
     * 
     * @param event 
     */
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btCancel) {
            ((Stage) (btCancel.getScene().getWindow())).close();
        }
        if (event.getSource() == btDelete) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("TODO");
            alert.setHeaderText("Look, a Confirmation Dialog");
            alert.setContentText("TODO: U want to rekt " + lvPpl.getSelectionModel().getSelectedItem().getName() +
                    " (ID: "+ lvPpl.getSelectionModel().getSelectedItem().getID()+")?");
            if(alert.showAndWait().get() == ButtonType.OK){
                ButtonRoom tmpBtr = lvPpl.getSelectionModel().getSelectedItem().getLocation().getBtnRoom();                
                dashboardController.setSelectedPerson(null);
                model.getPeople().removePerson(lvPpl.getSelectionModel().getSelectedItem());                
                tmpBtr.leave(lvPpl.getSelectionModel().getSelectedItem());
                ((Stage) (btCancel.getScene().getWindow())).close();
            }
        }
    }

    public static void setModel(Model model) {
        PersonDeleterController.model = model;
    }   
    
}
