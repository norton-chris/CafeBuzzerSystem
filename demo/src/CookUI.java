import javafx.application.Application;
import javafx.application.Platform;
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
import javax.mail.SendFailedException;
import java.util.Timer;
import java.util.TimerTask;

public class CookUI extends Application {

    GridPane pane = new GridPane();
    MessageBox msg = new MessageBox();
    TextField orderNumber = new TextField();
    Object[] array = msg.getKeys().toArray();
    Text error = new Text();
    OrderIOManager io = new OrderIOManager(msg);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        try {
            Email email = new Email();
            Phone phone = new Phone();

            // Create the Text
            Text title = new Text("Cafe Notification System");
            title.setFont(Font.font(18));
            Text text = new Text("Enter Order Number: ");
            Text orderArray = new Text("Order numbers in Queue: ");
            VBox root = new VBox();


            // Make Text box for entering order number
            orderNumber.setPrefColumnCount(3);
            orderNumber.setPrefWidth(3);

            // TESTING ORDER NUMBERS
            // Test values in HashMap
//            for(int i = 0; i < 5; i++){
//                msg.putMessage(i, "cnorton@mtu.edu", "6129637757");
//            }

            updateGridPane();

            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            updateGridPane();
                        }
                    }, 0, 60000);

            // Create send button
            Button send = new Button("Send");
            send.setDefaultButton(true); // set so that enter will press the send button
            send.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    pane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 0);
                    System.out.println("Send Button Pressed!");
                    int order = -1;
                    try {
                        System.out.println(order = Integer.parseInt(orderNumber.getText()));
                        error.setFill(Color.TRANSPARENT);
                    } catch (NumberFormatException e) {
                        System.out.println("Please only enter numbers");
                        error.setText("Please only enter numbers, re-enter number");
                        error.setFill(Color.RED);
                    }

                    updateGridPane();

                    String printOrder = orderNumber.getText();
                    System.out.println("Order number is: " + printOrder);
                    // then check if the order number is in the HashMap
                    // if it is check for !null email and/or phone number
                    try {
                        String[] emailphone = msg.getEmailPhone(order, true);
                        io.writeHashMap(false);
                        System.out.println("in the first try");
                        if (emailphone[0] != null)
                            System.out.println("in first if");
                            email.sendOrderReady(emailphone[0]);
                        if (emailphone[1] != null) {
                            class MyThread implements Runnable { // using thread to avoid unresponsive gui
                                String name;
                                Thread t;
                                MyThread (String threadname){
                                    name = threadname;
                                    t = new Thread(this, name);
                                    t.start();
                                }
                                public void run() {
                                    try {
                                        phone.sendOrderReady(emailphone[1]);
                                        System.out.println("order message sent");
                                    }catch (Exception e) {
                                        System.out.println(name + "Messaging exception");
                                    }
                                }
                            }
                            new MyThread(printOrder + "SendMessage");
                        }
                        if (emailphone[0] == null && emailphone [1] == null) {
                            System.out.println("Order number doesn't exist");
                            error.setText("Order number " + order + " does not exist, please make sure you type the correct number");
                            error.setFill(Color.RED);
                        }

                        updateGridPane();
                        orderNumber.setText("");
                    } catch (SendFailedException e){
                        System.out.println("email send failed");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e){
                        System.out.println("Order number doesn't exist");
                        error.setText("Order number " + order + " does not exist, please make sure you type the correct number\nNull pointer");
                        error.setFill(Color.RED);
                        orderNumber.setText("");
                        e.printStackTrace();
                    }
                }
            });

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
            e.printStackTrace();
        }
    }

    public void updateGridPane(){
        pane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 0);

        // Display order numbers in the HashMap
        msg = io.readHashMap();
        array = msg.getKeys().toArray(); // instead of this get order numbers from the HashMap
        for (int x = 0; x < array.length; x++) { // add map from Data class
            Label label = new Label(array[x].toString() + " ");
            pane.addColumn(x, label);
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
