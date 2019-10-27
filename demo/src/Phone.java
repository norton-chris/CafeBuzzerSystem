import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Phone {

    private static ArrayList<String> carriers = new ArrayList<String>();

    public String parsePhoneNumber(String input) {
        String chunks[] = input.split("[ ,./)(-]+");
        String out = "";
        for(String c: chunks) {
            out += c;
        }
        return out;
    }

    public void sendMail(String recipient) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "dummyBuzzer@gmail.com";
        String passWord = "rezzuB6!"; // Enter password for this to work

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, passWord);
            }
        });
        recipient = parsePhoneNumber(recipient);
        fillCarrierArray();
        for (int i = 0; i < carriers.size(); i ++) {
            String recipientWithAt = recipient + carriers.get(i);
            Message message = initialMessage(session, myEmail, recipientWithAt);
            Transport.send(message);
            System.out.println("Initial Notification sent for" + carriers.get(i));
        }
        try {
            Thread.sleep(15000);
        } catch (Exception e){
            System.exit(-1);
        }
        for (int i = 0; i < carriers.size(); i ++) {
            String recipientWithAt = recipient + carriers.get(i);
            Message message1 = orderMessage(session, myEmail, recipientWithAt);
            Transport.send(message1);
            System.out.println("Order Notification sent for " + carriers.get(i));
        }
    }

    /**
     * Sets up the email sent initially to the customer.
     * @param session
     * @param myEmail
     * @param recepient
     * @return
     * @throws MessagingException
     */
    private static Message initialMessage(Session session, String myEmail, String recepient) throws MessagingException {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress((myEmail)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
           // message.setSubject("[Prototype]Order Notification");
            message.setText("Your order has been received by the Cafe Notification Center!\n\nYou will receive another notification when your order is ready.");
            return message;
        }
        catch(AddressException ex){
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Sets up the email sent when the order is done.
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
            //message.setSubject("[Prototype]Order Notification"); // Choose subject or text, don't need both
            message.setText("Your order is ready to be picked up.");
            return message;
        } catch (AddressException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String args[]) throws MessagingException {
       Phone phone = new Phone();
//        System.out.println(phone.parsePhoneNumber("715-944-9124"));
//        System.out.println(phone.parsePhoneNumber("(715)944-9124"));
//        System.out.println(phone.parsePhoneNumber("715 944 9124"));
        phone.sendMail("616-240-8743");
    }

    private static void fillCarrierArray() {
        carriers.add("@vtext.com");
        carriers.add("@mms.att.net");
    }
}
