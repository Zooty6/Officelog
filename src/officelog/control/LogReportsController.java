package officelog.control;

import connections.DBConnection;
import static connections.DBConnection.PASSW;
import static connections.DBConnection.URL;
import static connections.DBConnection.USER;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Zooty
 */
public class LogReportsController implements Initializable, DBConnection {

    @FXML
    private TabPane tpMainPane;
    @FXML
    private Tab tFilteredLogs;
    @FXML
    private TableView<LogViewerController.LVEvent> tvFLogs;
    private final ObservableList<LogViewerController.LVEvent> data =
            FXCollections.observableArrayList();
    @FXML
    private TableColumn<LogViewerController.LVEvent, String> tcDate;
    @FXML
    private TableColumn<LogViewerController.LVEvent, String> tcType;
    @FXML
    private TableColumn<LogViewerController.LVEvent, String> tcID;
    @FXML
    private TableColumn<LogViewerController.LVEvent, String> tcName;
    @FXML
    private TableColumn<LogViewerController.LVEvent, String> tcRoom;
    @FXML
    private ComboBox<String> cbPersonFilter;
    @FXML
    private ImageView ivPic;
    @FXML
    private ListView<?> lvDAList;
    @FXML
    private ComboBox<?> cbRelation;
    @FXML
    private Button btRefreshDA;
    @FXML
    private TextField tfAmount;
    @FXML
    private RadioButton rbPercent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPersonFilter.getItems().add("");
        cbPersonFilter.getSelectionModel().select(0);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)) {
            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT Name, ID \n"
                    + "FROM Logs l, People p\n"
                    + "Where l.PersonID = p.ID\n"
                    + "GROUP BY Name, ID");
            while (rs.next()) {
                //cbPersonFilter.getItems().add("ID:" + rs.getString(2) + ", " + rs.getString(1));
                cbPersonFilter.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Officelog");
            alert.setHeaderText("SQL Error");
            alert.setContentText("There was an error connecting to the database");
            alert.showAndWait();
        }
        refresh();
    }

    
        
    
    
    private void refresh() {
        String where = cbPersonFilter.getSelectionModel().getSelectedItem().equals("") ? 
                "" : "AND Name = '" + cbPersonFilter.getSelectionModel().getSelectedItem() + "'";
        data.clear();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)) {
            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT Date, Type, ID, Name, l.Loc \n"
                    + "FROM Logs l, People p\n"
                    + "Where l.PersonID = p.ID \n"
                    + where);            
            tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
            tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
            while (rs.next()) {
                //System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));                
                //tvFLogs.getItems().add(new LogViewerController.LVEvent(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));                
                data.add(new LogViewerController.LVEvent(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            //System.out.println(tvFLogs.getItems());
            tvFLogs.getItems().setAll(data);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Officelog");
            alert.setHeaderText("SQL Error");
            alert.setContentText("There was an error connecting to the database");
            alert.showAndWait();
        }
    }
    
    
    @FXML
    private void cbAction (ActionEvent event) {
        //System.out.println(cbPersonFilter.getSelectionModel().getSelectedItem());
        refresh();
    }

}
