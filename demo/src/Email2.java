
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email2{
    public static void main(String[] args) {
        // Recipient's email ID needs to be mentioned.
        String to = "adsaffor@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "adsaffor@gmail.com";
        final String username = "adsaffor";//change accordingly
        final String password = "`1BackUp";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Testing Subject");

            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
            ((MimeBodyPart) messageBodyPart).setText( "<html>\n" +
                    "<head>\n" +
                    "<title>Cafe Notification Email</title>\n" +
                    "<style>\n" +
                    "body{\n" +
                    "background-image: url('C:\\Users\\antho\\Documents\\GitHub\\CafeBuzzerSystem\\demo\\src\\cnii.png');\n" +
                    "background-repeat: no-repeat;\n" +
                    "background-size: cover;\n" +
                    "background-attachment: fixed;\n" +
                    "background-size: 80% 80%\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body> <img src='cnii.jpg'>\n" +
                    "</body>\n" +
                    "</html>","text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(
                    "C:\\Users\\antho\\Documents\\GitHub\\CafeBuzzerSystem\\demo\\src\\cnii.png");

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");

            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);
            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}