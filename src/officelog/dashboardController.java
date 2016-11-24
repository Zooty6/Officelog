package officelog;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;

/**
 *
 * @author Szandi
 */
public class dashboardController implements Initializable {
    
    @FXML
    private MenuBar mb;
    @FXML
    private Button btn;
    @FXML
    private Button btn_boss;    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        Alert copyright=new Alert(Alert.AlertType.INFORMATION);
        copyright.setTitle("");
        copyright.setHeaderText("Készítették");
        copyright.setContentText("Kecskeméthy Zoltán \nMészáros Szandra");
        copyright.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
