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

    Email email = new Email();
    Phone phone = new Phone();
    MessageBox msgBox = new MessageBox();
//    OrderIOManager io = new OrderIOManager();
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
    };
    private void initalize(){
    };

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

                if(!orderNumStr.matches("0|[1-9]\\d+"))
                {
                    alert.show();
                    orderNum.clear();
                    return;
                }
                if(phoneNum.getText().isEmpty() && emailAdd.getText().isEmpty()){
                    alert.show();
                    return;
                }
                if(!emailAdd.getText().isEmpty()) {
                    String emailReceive = emailAdd.getText();
                    email.sendInitial(emailReceive);
                    msgBox.putMessage(orderNumPars, emailReceive, "");
                    emailAdd.clear();
                }
                if(!phoneNum.getText().isEmpty()){
                    String phoneRecieve = phoneNum.getText();
                    phone.sendInitial(phoneRecieve);
                    msgBox.putMessage(orderNumPars, "", phoneRecieve);
                    phoneNum.clear();
                }
                orderNum.clear();
            }
        } catch (Exception e) {
            System.out.println("PROBLEM IN retrieve EMAIL!");
            e.printStackTrace();
        }
    }
    public static void main(String[]args) {

    }
}
