package officelog;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class for selecting a Person from many
 *
 * @author Zooty
 */
public class PersonSelecterController implements Initializable {
    

    private static String idString = "id: ";
    private static String nameString = "name: ";            
    private static ArrayList<Person> SelectedList = new ArrayList<>();
    @FXML
    private Button btOk;
    @FXML
    private Button btCancel;
    @FXML
    private ListView<String> lvPlus;
    
    /**
     * Handles actions on the GUI.
     * 
     * @param event 
     */
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource().equals(btOk)){
            for (Person person : SelectedList)
                if ((idString + person.getID() + " " + nameString + person.getName()).equals(
                        lvPlus.getSelectionModel().getSelectedItem()))
                    dashboardController.setSelectedPerson(person);            
            ((Stage)(btCancel.getScene().getWindow())).close();
        }
        if(event.getSource().equals(btCancel)){            
           ((Stage)(btCancel.getScene().getWindow())).close();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //selectedPerson = null;
        int i=0;
        ObservableList<String> lvStrings = FXCollections.observableArrayList();
        for (Person person : SelectedList) {
            System.out.println(i++ +": " + person);
            lvStrings.add(idString + person.getID() + " " + nameString + person.getName());
            lvPlus.setItems(lvStrings);            
        }
        lvPlus.getSelectionModel().select(0);        
    }    
    
    public static void setSelectedList(ArrayList<Person> List) {
        SelectedList = List;
    }
    
    
}
