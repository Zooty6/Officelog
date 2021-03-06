package connections;

import Messages.Moving;
import Messages.PersonTemplate;
import Messages.RoomTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import officelog.control.dashboardController;
import officelog.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Zooty
 */
public class ConnectionToServer {

    private static final String SERVER = "http://localhost:8012";
    public static Socket client;
    private static final ObjectMapper objMapper = new ObjectMapper();
    private static boolean rready = false;
    private static boolean pplready = false;
    private static final Set<Person> peopleSet = new HashSet<>();
    private static final ArrayList<PersonTemplate> peopleTmpl = new ArrayList<>();
    private static ArrayList<Room> office = null;
    private static Model model;
//    private static Timeline timeline = new Timeline(new KeyFrame(
//            Duration.millis(3000),
//            action -> client.emit(Socket.EVENT_PING, "Ping")));

    public static void initClient() {
        IO.Options o = new IO.Options();
        o.forceNew = false;
        objMapper.registerModule(new JsonOrgModule());
        try {
            client = IO.socket(SERVER, o);
        } catch (URISyntaxException ex) {
            System.out.println("Uri syntax is wrong: " + ex.getMessage());
        }

        if (client.connected()) {
            System.out.println("Connected.");
        }
        addListeners();
    }

    public static void setModel(Model model) {
        ConnectionToServer.model = model;
    }

    public static void enter(int ID, String room) {
        client.emit("enterrequest", objMapper.convertValue(new Moving(ID, room), JSONObject.class));
    }

    public static void delPerson(int id) {
        PersonTemplate pt = new PersonTemplate();
        pt.setID(id);
        client.emit("delrequest", objMapper.convertValue(pt, JSONObject.class));
    }

    public static void addPerson(PersonTemplate personTemplate) {
        //System.out.println("req addperson");
        client.emit("addperson", objMapper.convertValue(personTemplate, JSONObject.class));
    }

    public static Set<Person> fetcfPeople(ArrayList<Room> off) throws InterruptedException {
        office = off;
        client.emit("fetchpeople", null, (Ack) (Object[] pplack) -> {
//            System.out.println("reading reply..");
            JSONArray json = (JSONArray) pplack[0];
            for (int i = 0; i < json.length(); i++) {
                try {
                    peopleTmpl.add(objMapper.convertValue(json.getJSONObject(i), PersonTemplate.class));
                } catch (JSONException ex) {
                    System.out.println("Can't read data from server.");
                }
            }
//            System.out.println(peopleTmpl);
            Room room = null;
            for (PersonTemplate personTemplate : peopleTmpl) {
                for (Room room1 : office) {
                    if (personTemplate.getLocationName().equals(room1.getName())) {
                        room = room1;
                    }
                }
                try {
                    if (personTemplate.getJob() == null) {
                        peopleSet.add(new Person(personTemplate, room));
                    } else {
                        peopleSet.add(new Employee(personTemplate, room, office));
                    }
                } catch (IOException ex) {
                    System.out.println("Can't read data from server.");
                }
            }
//            System.out.println("out");
//            System.out.println(peopleSet);
            pplready = true;
        });
        while (!pplready) {
            Thread.sleep(100);
        }
        pplready = false;
        return new HashSet<Person>(peopleSet);
    }

    public static ArrayList<Room> fetchRooms() throws InterruptedException {
        ArrayList<Room> rooms = new ArrayList<>();
        client.emit("fetchrooms", null, (Ack) (Object[] ack) -> {
            JSONArray json = (JSONArray) ack[0];
//            System.out.println(json);
//            System.out.println("------");
//            System.out.println(objMapper.convertValue(ack[0],SimpleMessage.class).getMessage());
//        });
            //System.out.println((String)ack[0]);

            //ArrayList<Friends> friendsList = objectMapper.readValue(result, type);
//            System.out.println(ack);
//            System.out.println(ack[0]);
//            System.out.println(ack[0].getClass());
            List<RoomTemplate> rt = new ArrayList<>();
            for (int i = 0; i < json.length(); i++) {
                try {
//                    System.out.println(json.getJSONObject(i));
//                    System.out.println("+++++++");
//                    System.out.println(objMapper.convertValue(json.getJSONObject(i), RoomTemplate.class).getName());
                    rt.add(objMapper.convertValue(json.getJSONObject(i), RoomTemplate.class));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
            //List<RoomTemplate> myObjects = objMapper.readValue(ack, objMapper.getTypeFactory().constructCollectionType(List.class, RoomTemplate.class));
//            try {
//                rt = objMapper.readValue((JsonParser)ack[0], type);
//            } catch (IOException ex) {
//                Logger.getLogger(ConnectionToServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//                //ArrayList<RoomTemplate> rt = (ArrayList<RoomTemplate>)ack[0];

            for (RoomTemplate roomtmpl : rt) {
                rooms.add(new Room(roomtmpl));
//                System.out.println(roomtmpl.getClass());
            }
            for (Room room : rooms) {
                RoomTemplate roomtmpl = null;
                for (RoomTemplate roomTemplate : rt) {
                    if (room.getName().equals(roomTemplate.getName())) {
                        roomtmpl = roomTemplate;
                    }
                }
                if (roomtmpl != null) {
                    for (String neighbor : roomtmpl.getNeighbors()) {
                        for (Room room1 : rooms) {
                            if (neighbor.equals(room1.getName())) {
                                room.addNeighbor(room1);
                            }
                        }
                    }
                }
//                if(room.getNeighbors().size()>2)
//                    System.out.println(room);
            }
//            System.out.println("ˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇ");

            rready = true;
        });

        while (!rready) {
            Thread.sleep(100);
        }
        rready = false;
//        System.out.println("SIZE: " + rooms.size());
        return new ArrayList<Room>(rooms);
    }

    public static void disconnect() {
        client.disconnect();
    }

    private static synchronized void addListeners() {

        client.on(Socket.EVENT_DISCONNECT, (Object... os) -> {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Officelog");
                alert.setHeaderText("Connection Error");
                alert.setContentText("You have been disconnected from the server! Application will close.");
                alert.showAndWait();
                System.exit(2);
            });
        });

        client.on("message", (Object... os) -> {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Officelog");
                alert.setHeaderText("Server message");
                alert.setContentText((String)os[0]);
                alert.show();
            });
        });

        client.on("delperson", (Object... os) -> {
            PersonTemplate persontmpl = objMapper.convertValue(os[0], PersonTemplate.class);
            int delid = persontmpl.getID();
            System.out.println(delid);
            model.getPeople().removePerson(delid);

//            Person delperson = null;
//            for (Person person : model.getPeople().getIPeople()) {
//                if(delid == person.getID())
//                    delperson = person;                
//            }
//            if(delperson!=null){
//                delperson.getLocation().getBtnRoom().leave(delperson);
//                model.getPeople().removePerson(delperson);
//            }
        });

        client.on("newperson", (Object... os) -> {

            System.out.println("NEWPERSON!!");

            //Platform.runLater(() -> {
            PersonTemplate persontmpl = objMapper.convertValue(os[0], PersonTemplate.class);
            BufferedImage pic = null;
            Person newPerson = null;
            try {
                pic = ImageIO.read(new ByteArrayInputStream(persontmpl.getPic()));
            } catch (IOException ex) {
                System.out.println("Can't read picture.");
            }
            if (persontmpl.getJob() == null) {
                newPerson = new Person(persontmpl.getName(), pic, persontmpl.getID());
            } else {
                Set<Room> per = new HashSet<>();
                for (String string : persontmpl.getPer()) {
                    for (Room room : model.getOffice()) {
                        if (string.equals(room.getName())) {
                            per.add(room);
                        }
                    }
                }
                newPerson = new Employee(persontmpl.getName(), pic,
                        persontmpl.getID(), persontmpl.getJob(), per.toArray(new Room[per.size()]));
                //String Name, BufferedImage Pic, int ID, String Job, Room[] per
            }
            newPerson.setLocation(model.getRoom(persontmpl.getLocationName()));
            model.getPeople().addPerson(newPerson);

            //});
        });

        client.on("entered", (Object... os) -> {
            Moving m = objMapper.convertValue(os[0], Moving.class);
            String roomname = m.getRoom();
            Room destRoom = null;
            ArrayList<Room> off = model.getOffice();
//            System.out.println(off);
            for (Room room : off) {
//                System.out.println(room.getName());
                if (room.getName().equals(roomname)) {
                    destRoom = room;
                }
            }
//            if(destRoom.getName().equals(roomname))
//                System.out.println("yayyy " + destRoom.getName());
            Person person = null;
            for (Person personi : model.getPeople().getIPeople()) {
                if (personi.getID() == m.getPersonID()) {
                    person = personi;
                }
            }
            try {
//                System.out.println(person.getID());
//                System.out.println("I will enter " + destRoom.getBtnRoom().getRoom().getName()+" or" + destRoom.getName());
                if (destRoom == null) {
                    System.out.println("DESTROOM IS NULL");
                }
                if (person == null) {
                    System.out.println("PERSON IS NULL");
                }
                final Person fp = person;
                final Room fr = destRoom;

                fr.getBtnRoom().Enter(fp);
                dashboardController.d.EnableNeighburs();

            } catch (NullPointerException ex) {
                System.out.println("Couldn't find something in the local model..");
            }

        });

        client.on("people", (Object... pplack) -> {
            //System.out.println("reading reply..");
            JSONArray json = (JSONArray) pplack[0];
            for (int i = 0; i < json.length(); i++) {
                try {
                    peopleTmpl.add(objMapper.convertValue(json.getJSONObject(i), PersonTemplate.class));
                } catch (JSONException ex) {
                    System.out.println("Can't read data from server.");
                }
            }
            //System.out.println(peopleTmpl);
            Room room = null;
            for (PersonTemplate personTemplate : peopleTmpl) {
                for (Room room1 : office) {
                    if (personTemplate.getLocationName().equals(room1.getName())) {
                        room = room1;
                    }
                }
                try {
                    if (personTemplate.getJob() == null) {
                        peopleSet.add(new Person(personTemplate, room));
                    } else {
                        peopleSet.add(new Employee(personTemplate, room, office));
                    }
                    //System.out.println(room.getName());
                } catch (IOException ex) {
                    System.out.println("Can't read data from server.");
                }
            }
            //System.out.println("out");
            //System.out.println(peopleSet);
            pplready = true;
        });
    }

    public static void connect() {
        client.connect();
    }

}
