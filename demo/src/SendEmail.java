import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendEmail {

    public static void sendMail(String recipient) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "adsaffor@mtu.edu";
        String passWord = ""; // Enter password for this to work

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, passWord);
            }
        });

        Message message = prepareMessage(session, myEmail, recipient);
        Message message1 = orderMessage(session, myEmail, recipient);
        Transport.send(message);
        System.out.println("Notification sent!");
        try {
            Thread.sleep(15000);
        } catch (Exception e){
            System.exit(-1);
        }
        Transport.send(message1);
        System.out.println("Notification sent!");
    }

    private static Message prepareMessage(Session session, String myEmail, String recepient) throws MessagingException {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress((myEmail)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("[Prototype]Order Notification");
            message.setText("Your order has been received by the Cafe Notification Center!\n\nYou will receive another notification when your order is ready.");
            return message;
        }
        catch(AddressException ex){
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private static Message orderMessage(Session session, String myEmail, String recepient) throws MessagingException {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress((myEmail)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("[Prototype]Order Notification");
            message.setText("Your order is ready to be picked up.");
            return message;
        }
        catch(AddressException ex){
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static void main(String[]args) throws MessagingException {
        SendEmail.sendMail("cnorton@mtu.edu");
    }
}