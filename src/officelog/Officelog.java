package officelog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Officelog extends Application {
    
    @Override
    public void start(Stage primaryStage) {
               
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
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
