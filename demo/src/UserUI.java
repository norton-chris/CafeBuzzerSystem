import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class UserUI extends Application
{
    @Override
    public void start(Stage stage)
    {
        Email email = new Email();
        Phone phone = new Phone();

        VBox vbox = new VBox();
        stage.setTitle("Cafe Notification System");
        Text prompt = new Text();

        TextField phoneNumTxt = new TextField();
        TextField emailTxt = new TextField();

        Label emailLabel = new Label("Email: ");
        Label phoneLabel = new Label("Phone Number: ");

        try{
            email.sendMail(emailTxt.getText());
        }
        catch(Exception ex){
            System.out.println(ex.getStackTrace());
        }


        vbox.getChildren().addAll(emailLabel, emailTxt, phoneLabel, phoneNumTxt);
        Scene scene = new Scene(vbox, 300, 404);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[]args)
    {
        launch(args);
    }
}
