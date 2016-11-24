package officelog;
/**
 * @author Szandi, Zooty
 */
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Officelog extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{        
        //<editor-fold defaultstate="collapsed" desc="comment">
        /*       
        Button btn = new Button();
        btn.setText("Do the thing");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                //Do the thing
                DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                Date d = new Date();
                System.out.println(dateFormat.format(d));
                
            }
        });
        
        StackPane root = new StackPane();        
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Test");
        primaryStage.setScene(scene);
        primaryStage.show();
        */
        //</editor-fold>
        
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Officelog");
        primaryStage.setResizable(false);//gombok szétesnek
        primaryStage.sizeToScene();
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        //Model model = new Model();        
        launch(args);
    }
    
}
