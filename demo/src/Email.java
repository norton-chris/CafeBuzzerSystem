import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Email {

    public int status = 0;
    protected String email = "dumberbuzzer@gmail.com";
    protected String password = "-0987654321qw";



    public int getStatus()
    {
        return status;
    }
    /**
     * Sets up email services
     * then actually sends the email
     *
     * @param recipient
     * @throws MessagingException
     */
    public void sendInitial(String recipient) throws MessagingException, SendFailedException {
        try {
            Properties properties = new Properties();

            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });

            recipient = parseEmail(recipient);
            Message message = initialMessage(session, email, recipient);
            Transport.send(message);
            status = 1;
            System.out.println("Notification sent!");


        } catch (SendFailedException e){
            throw new SendFailedException();
        } catch (AuthenticationFailedException e){
            System.out.println("Email or password is incorrect");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
    }

    private static Message initialMessage(Session session, String myEmail, String recepient) throws MessagingException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recepient));
            message.setSubject("Cafe Notification System");
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<img src=\"cid:image\">", "text/html");
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(
                    "C:\\Users\\antho\\Documents\\GitHub\\CafeBuzzerSystem\\demo\\src\\cnii.jpg");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            return message;
        } catch (AddressException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void sendOrderReady(String recipient) throws MessagingException, SendFailedException {
        try {
            Properties properties = new Properties();

            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });

            recipient = parseEmail(recipient);
            Message message1 = orderMessage(session, email, recipient);
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
                input = email.parseEmail(input);
                email.sendInitial(input);
                invalid = false;
            } catch (SendFailedException e) {
                System.out.println("Please enter a valid email address");
            }
        }
    }

    public String parseEmail(String email) {
        if (!email.contains("@"))
            return email + "@mtu.edu";
        return email;
    }
}
