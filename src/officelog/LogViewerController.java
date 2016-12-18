package officelog;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.NodeList;

/**
 * FXML Controller class
 *
 * @author Zooty
 */
public class LogViewerController implements Initializable {

    @FXML
    private TableView<LVEvent> tvLogs;
    @FXML
    private TableColumn<LVEvent, String> tc1;
    @FXML
    private TableColumn<LVEvent, String> tc2;
    @FXML
    private TableColumn<LVEvent, String> tc3;
    @FXML
    private TableColumn<LVEvent, String> tc4;
    @FXML
    private TableColumn<LVEvent, String> tc5;
    private static List<Event> Elist;    
    private final ObservableList<LVEvent> data =
            FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");        
        for (Event event : Elist) {
            if(event.getWho() == null && event.getWhere() == null){
                data.add(new LVEvent(dateFormat.format(event.getEventDate()), event.getType(), 
                    "", "", ""));
            }else if(event.getWho() == null){
                data.add(new LVEvent(dateFormat.format(event.getEventDate()), event.getType(), 
                    "", "", event.getWhere().toString()));
                }else if (event.getWhere() == null){
                    data.add(new LVEvent(dateFormat.format(event.getEventDate()), event.getType(), 
                            Integer.toString(event.getWho().getID()), event.getWho().getName(), ""));
                }else{
                    data.add(new LVEvent(dateFormat.format(event.getEventDate()), event.getType(), 
                            Integer.toString(event.getWho().getID()), event.getWho().getName(), event.getWhere().toString()));                    
                }
        }
        tc1.setCellValueFactory(new PropertyValueFactory<>("date"));
        tc2.setCellValueFactory(new PropertyValueFactory<>("type"));
        tc3.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc4.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc5.setCellValueFactory(new PropertyValueFactory<>("room"));  
        tvLogs.setItems(data);  
        try {
            NodeList nList = Language.getLang().getElementsByTagName("logviewer").item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                if (nList.item(i).hasAttributes()) {
                    switch (nList.item(i).getAttributes().getNamedItem("name").getNodeValue()) {
                        case "tc1":
                            tc1.setText(nList.item(i).getTextContent());
                            break;
                        case "tc2":
                            tc2.setText(nList.item(i).getTextContent());
                            break;
                        case "tc3":
                            tc3.setText(nList.item(i).getTextContent());
                            break;
                        case "tc4":
                            tc4.setText(nList.item(i).getTextContent());
                            break;
                        case "tc5":
                            tc5.setText(nList.item(i).getTextContent());
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

    public static void setElist(List<Event> Elist) {
        LogViewerController.Elist = Elist;
    }    
    
    public static class LVEvent{
        private final SimpleStringProperty  date;
        private final SimpleStringProperty  type;
        private final SimpleStringProperty  id;
        private final SimpleStringProperty  name;
        private final SimpleStringProperty  room;

        public LVEvent(String date, String type, 
                String id, String name, String room) {
            this.date = new SimpleStringProperty(date);
            this.type = new SimpleStringProperty(type);
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.room = new SimpleStringProperty(room);
        }

        public String getDate() {
            return date.get();
        }

        public String getType() {
            return type.get();
        }

        public String getId() {
            if(!id.isEmpty().get())
                return "ID: " + id.get();
            else
                return "";
        }

        public String getName() {
            return name.get();
        }

        public String getRoom() {
            return room.get();
        }
    }
}
