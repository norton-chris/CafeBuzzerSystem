import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Phone {

    private static ArrayList<String> carriers = new ArrayList<String>();
    int status =0;

    public int getStatus()
    {
        return status;
    }

    public String parsePhoneNumber(String input) throws BadPhoneNumberException{
        String chunks[] = input.split("[ ,./)(-]+");
        String out = "";
        for(String c: chunks) {
            out += c;
        }

        if (out.length() != 10) {
            throw new BadPhoneNumberException(out + " is not a valid phone number!");
        }

        return out;
    }

    public void sendInitial(String recipient) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "dumberbuzzer@gmail.com";
        String passWord = "-0987654321qw"; // Enter password for this to work

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, passWord);
            }
        });
        try {
            recipient = parsePhoneNumber(recipient);
        } catch (BadPhoneNumberException e) {
            e.printStackTrace();
        }
        fillCarrierArray();
        for (int i = 0; i < carriers.size(); i ++) {
            String recipientWithAt = recipient + carriers.get(i);
            Message message = initialMessage(session, myEmail, recipientWithAt);
            Transport.send(message);
            System.out.println("Initial Notification sent for" + carriers.get(i));
        }
        status = 1;
    }

    public void sendOrderReady(String recipient) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "dumberbuzzer@gmail.com";
        String passWord = "-0987654321qw"; // Enter password for this to work

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, passWord);
            }
        });
        try {
            recipient = parsePhoneNumber(recipient);
        } catch (BadPhoneNumberException e) {
            e.printStackTrace();
        }
        fillCarrierArray();
        for (int i = 0; i < carriers.size(); i ++) {
            String recipientWithAt = recipient + carriers.get(i);
            Message message1 = orderMessage(session, myEmail, recipientWithAt);
            Transport.send(message1);
            System.out.println("Order Notification sent for " + carriers.get(i));
        }
        carriers.clear();
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
        //phone.sendMail("6124815809");
        boolean invalid = true;
        while(invalid) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your phone number: ");
                String input = scanner.nextLine();
                phone.sendInitial(input);
                invalid = false;
            } catch (SendFailedException e) {
                System.out.println("Please enter a valid phone number");
            }
        }
        System.out.println("Text Message Sent!");
    }

    private static void fillCarrierArray() {
        carriers.add("@vtext.com"); // Erin
        carriers.add("@mms.att.net"); // Chris
        carriers.add("@messaging.sprintpcs.com");
        carriers.add("@tmomail.net"); // Luis
        carriers.add("@sms.mycricket.com"); // Kid in our class
        carriers.add("@mmst5.tracfone.com"); // Dr Walker
    }
}
