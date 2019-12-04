import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.*;

public class userUI extends Application {
    private Stage stage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage stage){
        this.stage = stage;
        this.stage.setTitle("Cafe Notification System");

        layout();
//        showController();
    }

    public void layout(){
        try{
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("userUIsheet.fxml"));
            rootLayout =  (BorderPane) load.load();

            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void showController() {
        try{
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("userUIsheet.fxml"));
        AnchorPane overview = (AnchorPane) load.load();
        rootLayout.setCenter(overview);


        Controller controller = load.getController();
        controller.setApp(this);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[]args){
        launch(args);
    }
}
