import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.mail.MessagingException;

public class CookUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Email email = new Email();
            Phone phone = new Phone();
            MessageBox msg = new MessageBox();

            // Create the Text
            Text title = new Text("Cafe Notification System");
            title.setFont(Font.font(18));
            Text text = new Text("Enter Order Number: ");
            Text error = new Text();
            VBox root = new VBox();

            // Make Text box for entering order number
            TextField orderNumber = new TextField();
            orderNumber.setPrefColumnCount(3);

            // Create send button
            Button send = new Button("Send");
            send.setDefaultButton(true); // set so that enter will press the send button
            send.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Send Button Pressed!");
                    int order = -1;
                    try {
                        order = Integer.valueOf(orderNumber.getText());
                        error.setFill(Color.TRANSPARENT);
                    } catch (NumberFormatException e) {
                        System.out.println("Please only enter numbers");
                        error.setText("Please only enter numbers, re-enter number");
                        error.setFill(Color.RED);
                    }

                    System.out.println("Order number is: " + order);
                    // then check if the order number is in the HashMap
                    // if it is check for !null email and/or phone number
                    try {
                        String[] emailphone = msg.getEmailPhone(order);
                        if (emailphone[0] != null)
                            email.sendOrderReady(emailphone[0]);
                        if (emailphone[1] != null)
                            phone.sendMail(emailphone[1]);
                        if (emailphone[0] == null && emailphone [1] == null) {
                            System.out.println("Order number doesn't exist");
                            error.setText("Order number does not exist, please make sure you type the correct number");
                            error.setFill(Color.RED);
                        }
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e){
                        System.out.println("Order number doesn't exist");
                        error.setText("Order number does not exist, please make sure you type the correct number");
                        error.setFill(Color.RED);
                    }
                }
            });

            // Test values in HashMap
            for(int i = 0; i < 5; i++){
                msg.putMessage(i, "cnorton@mtu.edu", "6129637757");
            }

            // Display order numbers in the HashMap
            Object[] array = msg.getKeys().toArray(); // instead of this get order numbers from the HashMap
            Text orderArray = new Text("Order numbers in Queue: ");
            GridPane pane = new GridPane();
            for (int x = 0; x < array.length; x++) { // add map from Data class
                Label label = new Label(array[x].toString() + " ");
                pane.addColumn(x, label);
            }

            // Setup scene and add everything to it
            root.getChildren().addAll(title, text, orderNumber, send, error, orderArray, pane);
            // Create the Scene
            Scene scene = new Scene(root);

            // Set the Properties of the Stage
            stage.setX(100);
            stage.setY(200);
            stage.setMinHeight(300);
            stage.setMinWidth(400);

            // Add the scene to the Stage
            stage.setScene(scene);
            // Set the title of the Stage
            stage.setTitle("Cafe Buzzer System");
            // Display the Stage
            stage.show();
        }catch (Exception e){
            System.out.println("error");
        }
    }

    // May need this later if keypad doesn't have enter key
//    private void setGlobalEventHandler(Node root) {
//        root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
//            if (ev.getCode() == KeyCode.ENTER) {
//                send.fire();
//                ev.consume();
//            }
//        });
//    }
}
