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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.mail.MessagingException;

import static javafx.application.Application.launch;

public class UserUI extends Application
{
    @Override
    public void start(Stage stage)
    {
        final int HEIGHT = 808;
        final int WIDTH = 600;
        Email email = new Email();
        Phone phone = new Phone();

        VBox vbox = new VBox();
        stage.setTitle("Cafe Notification System");
        Text prompt = new Text();

        TextField phoneNumTxt = new TextField();
        TextField emailTxt = new TextField();
        Button send = new Button("Send");

        Label emailLabel = new Label("Email: ");
        Label phoneLabel = new Label("Phone Number: ");

        send.setDefaultButton(true); // set so that enter will press the send button
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Send Button Pressed!");
                try {
                    if(!emailTxt.getText().isEmpty()) {
                        String emailReceive = emailTxt.getText();
                        email.sendInitial(emailReceive);
                    }
                    if(!phoneNumTxt.getText().isEmpty()){
                        Integer phoneReceive = Integer.parseInt(phoneNumTxt.getText());

                    }
                    vbox.getChildren().clear();
                    Text loading = new Text( WIDTH/2, HEIGHT/2, "Sending your notification!");
                    loading.setTextAlignment(TextAlignment.CENTER);
                    loading.setFont(new Font("Helvetica", 35));
                    vbox.getChildren().addAll(loading);

                } catch (Exception e) {
                    System.out.println("PROBLEM IN retrieve EMAIL!");
                    e.printStackTrace();
                }
            }
        });

        vbox.getChildren().addAll(emailLabel, emailTxt, send, phoneLabel, phoneNumTxt);
        Scene scene = new Scene(vbox, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();

        }
    public static void main(String[]args)
    {
        launch(args);
    }
}

