import com.sun.mail.imap.protocol.MessageSet;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;

import static javafx.application.Application.launch;

public class Controller
{

    Email email;
    Phone phone;
    MessageBox msgBox;
    OrderIOManager io;
    private userUI ui;
    @FXML
    private TextField orderNum;

    @FXML
    private TextField phoneNum;

    @FXML
    private TextField emailAdd;

    @FXML
    private Button send;

    @FXML
    private Label orders;

    public Controller(){
        System.out.println("controller created!");
        email = new Email();
        phone = new Phone();
        msgBox = new MessageBox();
        io = new OrderIOManager(msgBox);
        io.readHashMap();
    };
    private void initalize(){};

    public void setApp(userUI ui){
        this.ui = ui;
    }

    public void handleSubmitButton(ActionEvent event){
        send.setDefaultButton(true);
        System.out.println("Send Button Pressed!");
        orders.setText("" + msgBox.size());
        try {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Invalid entry!");

            if(orderNum.getText().isEmpty())
            {
                                alert.show();
                                orderNum.clear();
            }
            else{
                String orderNumStr = orderNum.getText();
                int orderNumPars = Integer.parseInt(orderNumStr);

                if(!orderNumStr.matches("0|[1-9]\\d+") || orderNumStr.length() > 3)
                {
                    alert.show();
                    orderNum.clear();
                    return;
                }
                if(phoneNum.getText().isEmpty() && emailAdd.getText().isEmpty()){
                    alert.show();
                    return;
                }
                if(!emailAdd.getText().isEmpty() && phoneNum.getText().isEmpty()) {
                    String emailReceive = emailAdd.getText();
                    email.sendInitial(emailReceive);
                    msgBox.putMessage(orderNumPars, emailReceive, "");
                    emailAdd.clear();
                }
                else if(!phoneNum.getText().isEmpty() && emailAdd.getText().isEmpty()){
                    String phoneReceive = phoneNum.getText();
                    //beginning of thread
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
                                phone.sendInitial(phoneReceive);
                                System.out.println("order message sent");
                            }catch (Exception e) {
                                System.out.println(name + "Messaging exception ");
                            }
                        }
                    }
                    new MyThread(orderNumPars + "SendMessageUT");
                    // end of thread
                    msgBox.putMessage(orderNumPars, "", phoneReceive);
                    phoneNum.clear();
                } else if(!phoneNum.getText().isEmpty() && !emailAdd.getText().isEmpty()){
                    String emailReceive = emailAdd.getText();
                    //int orderNumPars = Integer.parseInt(orderNum.getText());
                    String phoneReceive = phoneNum.getText();
                    email.sendInitial(emailReceive);
                    //beginning of thread
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
                                phone.sendInitial(phoneReceive);
                                System.out.println("order message sent");
                            }catch (Exception e) {
                                System.out.println(name + "Messaging exception ");
                            }
                        }
                    }
                    new MyThread(orderNumPars + "SendMessageUT");
                    // end of thread
                    msgBox.putMessage(orderNumPars, emailReceive, phoneReceive);
                    emailAdd.clear();
                    phoneNum.clear();
                }
                orderNum.clear();
                io.writeHashMap(false);
            }
            //            else if(!phoneNum.getText().isEmpty() && emailAdd.getText().isEmpty()){
//                String phoneReceive = phoneNum.getText();
//                int orderNumPars = Integer.parseInt(orderNum.getText());
//                //phone.sendInitial(phoneReceive);
//                msgBox.putMessage(orderNumPars, "", phoneReceive);
//                phoneNum.clear();
//
//           }



        } catch (Exception e) {
            System.out.println("PROBLEM IN retrieve EMAIL!");
            e.printStackTrace();
        }
    }
    public static void main(String[]args) {

    }
}
