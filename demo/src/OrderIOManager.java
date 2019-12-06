import org.junit.jupiter.api.Order;

import java.io.*;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class OrderIOManager {

    private static final String filePath = "orders.buz";
    private File hashFile;

    private MessageBox messageBox;

    public OrderIOManager (MessageBox mBox) {
        messageBox = mBox;
    }

    public MessageBox readHashMap() {

        //make sure message box is empty
        messageBox.clearOrders();
        String entry = "";
        try {
            FileReader bufFeed = new FileReader(filePath);
            BufferedReader fileIn = new BufferedReader(bufFeed);

            while ( (entry = fileIn.readLine()) != null ) {
                String[] elements = entry.split(" ");
                messageBox.putMessage(Integer.parseInt(elements[0]), elements[1], elements[2]);
                System.out.println("Order number: " + elements[0] + " Email: " + elements[1] + " Phone: " + elements[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //update contents of messageBox
        return messageBox;
    }

    public void writeHashMap() {

        try {

            FileWriter fileOut = new FileWriter(filePath);
            System.out.println("made a new filewriter");
            fileOut.flush();
            for (int k : messageBox.getKeys()) {
                String[] contacts = messageBox.getEmailPhone(k);
                fileOut.write(k + " " + contacts[0] + " " + contacts[1] + "\n");
                System.out.println("writing order: ");
                System.out.println(k + " " + contacts[0] + " " + contacts[1]);
            }
            fileOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String args[]) {
        MessageBox box = new MessageBox();
        box.putMessage(45, "testmail@mtu.edu", "7158675309");
        //box.putMessage(69, "cnorton@mtu.edu", "7158675309");
        OrderIOManager io = new OrderIOManager(box);
        File hashFile = new File(filePath);
        try {

            io.writeHashMap();
            MessageBox newBox = io.readHashMap();
            System.out.println(newBox.removeMessage(45).getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
