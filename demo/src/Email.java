import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Email {

    /**
     * Sets up email services
     * then actually sends the email
     *
     * @param recipient 
     * @throws MessagingException
     */
    public void sendMail(String recipient) throws MessagingException, SendFailedException {
        try {
            Properties properties = new Properties();

            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            String myEmail = "dummybuzzer@gmail.com";
            String passWord = "rezzuB6!"; // Enter password for this to work

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myEmail, passWord);
                }
            });

            Message message = initialMessage(session, myEmail, recipient);
            Message message1 = orderMessage(session, myEmail, recipient);
            Transport.send(message);
            System.out.println("Notification sent!");

            Thread.sleep(15000);

            Transport.send(message1);
            System.out.println("Notification sent!");
        } catch (SendFailedException e){
            throw new SendFailedException();
        } catch (AuthenticationFailedException e){
            System.out.println("Email or password is not incorrect");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
    }

    /**
     * Sets up the email sent initially to the customer.
     *
     * @param session
     * @param myEmail
     * @param recepient
     * @return
     * @throws MessagingException
     */
    private static Message initialMessage(Session session, String myEmail, String recepient) throws MessagingException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress((myEmail)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("[Prototype]Order Notification");
            message.setText("Your order has been received by the Cafe Notification Center!\n\nYou will receive another notification when your order is ready.");
            return message;
        } catch (AddressException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Sets up the email sent when the order is done.
     *
     * @param session
     * @param myEmail
     * @param recepient
     * @return
     * @throws MessagingException
     */
    private static Message orderMessage(Session session, String myEmail, String recepient) throws MessagingException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress((myEmail)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("[Prototype]Order Notification");
            message.setText("Your order is ready to be picked up.");
            return message;
        } catch (AddressException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String[] args) throws MessagingException {
        Email email = new Email();
        boolean invalid = true;
        while(invalid) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your email: ");
                String input = scanner.nextLine();
                email.sendMail(input);
                invalid = false;
            } catch (SendFailedException e) {
                System.out.println("Please enter a valid email address");
            }
        }
    }
}