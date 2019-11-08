import com.sun.javafx.geom.Rectangle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        Stage stage2 = new Stage();
        int status = 0;

        int HEIGHT = 600;
        int WIDTH = 1000;

        Email email = new Email();
        Phone phone = new Phone();

        VBox vbox = new VBox();
        Pane pane = new Pane();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        stage.setTitle("Cafe Notification System");
        stage2.setTitle("Sending Notification!");

        TextField phoneNumTxt = new TextField();

        phoneNumTxt.setMaxSize(600, 100);
        TextField emailTxt = new TextField();
        emailTxt.setMaxSize(600, 100);
        emailTxt.setAlignment(Pos.CENTER);
        Button send = new Button("Send");

        Label emailLabel = new Label("Email: ");
        Label phoneLabel = new Label("Phone Number: ");


        //stage 2 construction
        ImageView loadingGif = new ImageView();
        loadingGif.setImage(new Image("loadingCircles.gif"));
        loadingGif.toBack();
        loadingGif.setLayoutX(45);
        Text loading = new Text("Sending your notification!");
        loading.setLayoutX(105);
        loading.setLayoutY(300);
        loading.setTextAlignment(TextAlignment.CENTER);
        loading.setFont(new Font("Helvetica", 35));

        send.setDefaultButton(true); // set so that enter will press the send button
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Send Button Pressed!");
                try {
                    if(!emailTxt.getText().isEmpty()) {
                        String emailReceive = emailTxt.getText();
                        stage.hide();
                        stage2.show();
                        email.sendInitial(emailReceive);
                    }
                    if(!phoneNumTxt.getText().isEmpty()){
                        String phoneReceive = phoneNumTxt.getText();
                        stage.hide();
                        stage2.show();
                        phone.sendInitial(phoneReceive);
                    }


                    if(email.getStatus()==1)
                    {
                        stage2.hide();
                        stage.show();
                    }
                    else{
                        System.out.println("ERROR IN SENDING EMAIL CAUGHT IN UI");
                    }
//
                    if(phone.getStatus()==1)
                    {
                        stage2.hide();
                        stage.show();
                    }
                    else{
                        System.out.println("ERROR IN SENDING EMAIL CAUGHT IN UI");
                    }


                } catch (Exception e) {
                    System.out.println("PROBLEM IN retrieve EMAIL!");
                    e.printStackTrace();
                }
            }
        });

        vbox.getChildren().addAll(emailLabel, emailTxt, phoneLabel, phoneNumTxt, send);
        pane.getChildren().addAll(loading, loadingGif);

        Scene scene = new Scene(vbox, WIDTH, HEIGHT);
        Scene scene2 = new Scene(pane, 600, 400, Color.BLACK);
        stage.setScene(scene);
        stage2.setScene(scene2);
        stage.show();

        }
    public static void main(String[]args)
    {
        launch(args);
    }
}
