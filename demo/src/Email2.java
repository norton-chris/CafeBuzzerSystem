import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
/**
 * This class is used to send email with image.
 * @author codesjava
 */
public class Email2 {
    final String senderEmailId = "dummybuzzer@gmail.com";
    final String senderPassword = "rezzuB6!";
    final String emailSMTPserver = "smtpout.secureserver.net";

    public Email2(String receiverEmail,
                  String subject, String messageText, String imagePath) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(properties, auth);

            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(senderEmailId));
            emailMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail));
            emailMessage.setSubject(subject);

            MimeBodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(messageText);

            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            DataSource source = new FileDataSource("C:\\Users\\chris\\Documents\\CafeBuzzerSystem\\demo\\src\\cnii.png");
            messageBodyPart2.setDataHandler(new DataHandler(source));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
            emailMessage.setContent(multipart);

            Transport.send(emailMessage);

            System.out.println("Email send successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in sending email.");
        }
    }

    private class SMTPAuthenticator extends
            javax.mail.Authenticator {
        public PasswordAuthentication
        getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmailId,
                    senderPassword);
        }
    }

    public static void main(String[] args) {
        new Email2("cnorton@mtu.edu", "Test Email",
                "Hi, This is a test email with image.", "C:\\Users\\chris\\Documents\\CafeBuzzerSystem\\demo\\src\\cnii.png");
    }
}