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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Zooty
 */
public class LogReportsController implements Initializable, DBConnection {

    @FXML
    private TableView<LogViewerController.LVEvent> tvFLogs;
    private final ObservableList<LogViewerController.LVEvent> data
            = FXCollections.observableArrayList();
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
    private TableView<DA> tvDA;
    @FXML
    private TableColumn<DA, String> tcDAID;
    @FXML
    private TableColumn<DA, String> tcDAName;
    @FXML
    private TableColumn<DA, String> tcDACount;
    @FXML
    private ComboBox<String> cbPersonFilter;
    @FXML
    private ComboBox<String> cbTypes;
    @FXML
    private ComboBox<String> cbRelation;
    @FXML
    private TextField tfAmount;
    @FXML
    private RadioButton rbPercent;
    private String PrevName = "All";
    @FXML
    private TreeView<String> tvVisitsTree;
    @FXML
    private Label lMostVisitedRoom;
    @FXML
    private Label lAverageVisits;
    @FXML
    private RadioButton rbCor;

    private class DateEnter {

        private final String date;
        private int enters = 1;

        public DateEnter(String room) {
            this.date = room;
        }

        public String getDate() {
            return date;
        }

        public int getEnters() {
            return enters;
        }

        public void inc() {
            enters++;
        }

        @Override
        public String toString() {
            return date;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 89 * hash + Objects.hashCode(this.date);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final DateEnter other = (DateEnter) obj;
            return Objects.equals(this.date, other.date);
        }
    }
    private ArrayList<DateEnter> roomvisits; //This makes no sense, and super confusing, I know! (I'm just lazy..)
    private int maxEnterCorPos = 0, maxEnterCorNeg = 0, sumEnterCorPos = 0, sumEnterCorNeg = 0;
    private String maxEnterRoomNameCorPos = "Nobody entered anywhere",
            maxEnterRoomNameCorNeg = "Nobody entered anywhere";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshLogs();
        cbRelation.getItems().add("TOP");
        cbRelation.getItems().add("LEAST");
        cbRelation.getSelectionModel().select(0);
        refreshDA();
        RefreshVisits();
    }

    public class DA {

        private String ID;
        private String name;
        private String Count;

        public DA(String ID, String name, String Count) {
            this.ID = ID;
            this.name = name;
            this.Count = Count;
        }

        public String getID() {
            return ID;
        }

        public String getName() {
            return name;
        }

        public String getCount() {
            return Count;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCount(String Count) {
            this.Count = Count;
        }
    }

    @FXML
    private void refreshLogs() {
        int selNames = cbPersonFilter.getSelectionModel().getSelectedIndex();        
        cbPersonFilter.getItems().clear();
        cbPersonFilter.getItems().add("All");
        Set<String> types = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)) {
            ResultSet rs = conn.createStatement().executeQuery(SQLSELECTLOGSPEOPLE1);
            while (rs.next()) {
                //cbPersonFilter.getItems().add("ID:" + rs.getString(2) + ", " + rs.getString(1));
                cbPersonFilter.getItems().add(rs.getString(1));
            }
            cbPersonFilter.getSelectionModel().select(selNames == -1 ? 0 : selNames);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Officelog");
            alert.setHeaderText("SQL Error");
            alert.setContentText("There was an error connecting to the database");
            alert.showAndWait();
        }
        if (cbTypes.getSelectionModel().getSelectedIndex() == -1) {
            cbTypes.getItems().add("All");
            cbTypes.getSelectionModel().select(0);
        }
        String selTypes = cbTypes.getSelectionModel().getSelectedItem();
        String where1 = cbPersonFilter.getSelectionModel().getSelectedItem().equals("All")
                ? "" : "AND Name = '" + cbPersonFilter.getSelectionModel().getSelectedItem() + "' ";
        String where2 = cbTypes.getSelectionModel().getSelectedItem().equals("All")
                || !cbPersonFilter.getSelectionModel().getSelectedItem().equals(PrevName)
                ? "" : "AND Type = '" + cbTypes.getSelectionModel().getSelectedItem() + "'";
        if (where2.equals("")) {
            cbTypes.getSelectionModel().select("All");
        }
        data.clear();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)) {
            ResultSet rs = conn.createStatement().executeQuery(SQLSELECTPEOPLE2 + where1 + "\n" + where2);
            tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
            tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
            if (!cbPersonFilter.getSelectionModel().getSelectedItem().equals(PrevName) || PrevName.equals("All")) {
                types.clear();
                cbTypes.getItems().clear();
                cbTypes.getItems().add("All");
                cbTypes.getSelectionModel().select(0);
            }
            while (rs.next()) {
                //System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));                
                //tvFLogs.getItems().add(new LogViewerController.LVEvent(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));                
                data.add(new LogViewerController.LVEvent(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                types.add(rs.getString(2));
            }
            //System.out.println(tvFLogs.getItems());
            tvFLogs.getItems().setAll(data);
            if (!cbPersonFilter.getSelectionModel().getSelectedItem().equals(PrevName) || PrevName.equals("All")) {
                cbTypes.getItems().addAll(types);
            }
            if (types.contains(selTypes)) {
                cbTypes.getSelectionModel().select(selTypes);
            }
            PrevName = cbPersonFilter.getSelectionModel().getSelectedItem();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Officelog");
            alert.setHeaderText("SQL Error");
            alert.setContentText("There was an error connecting to the database");
            alert.showAndWait();
        }
    }

    @FXML
    private void refreshDA() {
        int top;
        String Rel = cbRelation.getValue().equals("TOP") ? "DESC" : "ASC";
        String Perc = rbPercent.isSelected() ? " PERCENT" : "";
        tvDA.getItems().clear();
        try {
            top = Integer.parseInt(tfAmount.getText());
        } catch (NumberFormatException e) {
            top = 100;
            tfAmount.setText("100");
        }
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)) {
            ResultSet rs = conn.createStatement().executeQuery(
                    SQLSELECTLOGSPEOPLE2FIRST + top + Perc + SQLSELECTLOGSPEOPLE2SECOND + Rel);
            tcDAID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            tcDAName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcDACount.setCellValueFactory(new PropertyValueFactory<>("Count"));
            while (rs.next()) {
                tvDA.getItems().add(new DA(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Officelog");
            alert.setHeaderText("SQL Error");
            alert.setContentText("There was an error connecting to the database");
            alert.showAndWait();
        }
    }

    @FXML
    private void RefreshVisits() {
        maxEnterCorNeg = 0; maxEnterCorPos = 0; sumEnterCorNeg = 0; sumEnterCorPos = 0;
        roomvisits = new ArrayList<>();
        class EnteredLogs {

            private final Date date;
            private final int ID;
            private final String Name;
            private final String room;

            public EnteredLogs(Date date, int ID, String Name, String room) {
                this.date = date;
                this.ID = ID;
                this.Name = Name;
                this.room = room;
            }

            public Date getDate() {
                return date;
            }

            public int getID() {
                return ID;
            }

            public String getName() {
                return Name;
            }

            public String getRoom() {
                return room;
            }

            @Override
            public String toString() {
                return "EnteredLogs{" + "Name=" + Name + "(" + ID + "), room=" + room + '}';
            }

        }

        class PersonEnter {

            private final String PersonName;
            private final ArrayList<DateEnter> dEnters = new ArrayList<>();

            public PersonEnter(String PersonName) {
                this.PersonName = PersonName;
            }

            public String getPersonName() {
                return PersonName;
            }

            public ArrayList<DateEnter> getdEnters() {
                return dEnters;
            }

            @Override
            public int hashCode() {
                int hash = 7;
                hash = 23 * hash + Objects.hashCode(this.PersonName);
                return hash;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final PersonEnter other = (PersonEnter) obj;
                return Objects.equals(this.PersonName, other.PersonName);
            }
        }

        ArrayList<String> Rooms = new ArrayList<>();
        ArrayList<EnteredLogs> logs = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSW)) {
            ResultSet rs = conn.createStatement().executeQuery(SQLSELECTROOMS2);
            while (rs.next()) {
                Rooms.add(rs.getString(1));
                roomvisits.add(new DateEnter(rs.getString(1)));
            }
            rs = conn.createStatement().executeQuery(SQLSELECTLOGSPEOPLE3);
            while (rs.next()) {
                logs.add(new EnteredLogs(rs.getDate(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
                for (DateEnter roomvisit : roomvisits) {
                    if (roomvisit.date.equals(rs.getString(4))) {
                        roomvisit.inc();
                    }
                }
            }

            TreeItem<String> root = new TreeItem<>("Office");
            root.setExpanded(true);
            for (String room : Rooms) {
                ArrayList<PersonEnter> PENodeModel = new ArrayList<>();
                TreeItem<String> roomItem = new TreeItem<>(room);
                root.getChildren().add(roomItem);
                for (EnteredLogs log : logs) {
                    if (log.getRoom().equals(roomItem.getValue())) {
                        PersonEnter pe = new PersonEnter(log.getName());
                        if (PENodeModel.contains(pe)) {
                            for (PersonEnter personEnter : PENodeModel) {
                                if (personEnter.equals(pe)) {
                                    DateEnter de = new DateEnter(log.getDate().toString());
                                    if (personEnter.getdEnters().contains(de)) {
                                        for (DateEnter dateEnter : personEnter.getdEnters()) {
                                            if (dateEnter.equals(de)) {
                                                dateEnter.inc();
                                            }
                                        }
                                    } else {
                                        personEnter.getdEnters().add(de);
                                    }
                                }
                            }
                        } else {
                            PENodeModel.add(pe);
                            pe.getdEnters().add(new DateEnter(log.getDate().toString()));
                        }
                    }
                }
                for (PersonEnter personEnter : PENodeModel) {
                    TreeItem<String> PEItem = new TreeItem<>(personEnter.getPersonName());
                    roomItem.getChildren().add(PEItem);
                    for (DateEnter dateEnter : personEnter.getdEnters()) {
                        PEItem.getChildren().add(new TreeItem<>(dateEnter.getDate() + " (" + dateEnter.getEnters() + ")"));
                    }
                }

            }
            tvVisitsTree.setRoot(root);
            CorRefresh();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Officelog");
            alert.setHeaderText("SQL Error");
            alert.setContentText("There was an error connecting to the database");
            alert.showAndWait();
        }
    }

    @FXML
    private void CorRefresh() {
        maxEnterCorNeg = 0; maxEnterCorPos = 0; sumEnterCorNeg = 0; sumEnterCorPos = 0;
        for (DateEnter roomvisit : roomvisits) {
            if (roomvisit.date.equals("R17") || roomvisit.date.equals("R18") || roomvisit.date.equals("R19")) {
                if (roomvisit.getEnters() - 1 > maxEnterCorPos) {
                    maxEnterCorPos = roomvisit.getEnters() - 1;
                    maxEnterRoomNameCorPos = roomvisit.getDate() + " (" + maxEnterCorPos + ")";
                }
            } else if (roomvisit.getEnters() - 1 > maxEnterCorNeg) {
                maxEnterCorNeg = roomvisit.getEnters() - 1;
                maxEnterRoomNameCorNeg = roomvisit.getDate() + " (" + maxEnterCorNeg + ")";
                sumEnterCorNeg += roomvisit.getEnters();
            }
            sumEnterCorPos += roomvisit.getEnters();
        }
        if (rbCor.isSelected()) {
            lMostVisitedRoom.setText(maxEnterRoomNameCorPos);
            lAverageVisits.setText("" + ((float) sumEnterCorPos / roomvisits.size()));
        } else {
            lMostVisitedRoom.setText(maxEnterRoomNameCorNeg);
            lAverageVisits.setText("" + ((float) sumEnterCorNeg / (roomvisits.size()-3)));
        }
    }
}
