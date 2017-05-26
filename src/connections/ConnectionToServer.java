package connections;

import Messages.Moving;
import Messages.PersonTemplate;
import Messages.RoomTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import officelog.model.Employee;
import officelog.model.Person;
import officelog.model.Room;
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

    public static void initClient() {
        objMapper.registerModule(new JsonOrgModule());
        try {
            client = IO.socket(SERVER);
        } catch (URISyntaxException ex) {
            System.out.println("Uri syntax is wrong: " + ex.getMessage());
        }
        System.out.println("Connecting to server...");

        client.connect();
        if (client.connected()) {
            System.out.println("Connected.");
        }
        addListeners();       
        
    }
    
    public static void enter(int ID, String room){
        client.emit("enterrequest", objMapper.convertValue(new Moving(ID, room), JSONObject.class));
    }

    public static Set<Person> fetcfPeople(ArrayList<Room> off) throws InterruptedException {
        office = off;
        client.emit("fetchpeople", null, (Ack) (Object[] pplack) -> {
            System.out.println("reading reply..");
            JSONArray json = (JSONArray) pplack[0];
            for (int i = 0; i < json.length(); i++) {
                try {
                    peopleTmpl.add(objMapper.convertValue(json.getJSONObject(i), PersonTemplate.class));
                } catch (JSONException ex) {
                    System.out.println("Can't read data from server.");
                }
            }
            System.out.println(peopleTmpl);
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
                }catch(IOException ex){
                    System.out.println("Can't read data from server.");
                }
            }
            System.out.println("out");
            System.out.println(peopleSet);
            pplready=true;            
        });
        while(!pplready)
            Thread.sleep(100);
        pplready = false;
        return peopleSet;
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
                if(room.getNeighbors().size()>2)
                    System.out.println(room);
            }
//            System.out.println("ˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇ");

            rready = true;
        });

        while (!rready) {
            Thread.sleep(100);
        }
        rready = false;
//        System.out.println("SIZE: " + rooms.size());
        return rooms;
    }

    public static void disconnect() {
        client.disconnect();
    }

    private static void addListeners() {
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
            System.out.println(peopleTmpl);
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
                }catch(IOException ex){
                    System.out.println("Can't read data from server.");
                }
            }
            //System.out.println("out");
            //System.out.println(peopleSet);
            pplready=true; 
        });
    }

}
