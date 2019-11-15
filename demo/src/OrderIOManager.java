import org.junit.jupiter.api.Order;

import java.io.*;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class OrderIOManager implements Observer {

    private static final String filePath = "orders.buz";
    private File hashFile;
    private FileWriter fileOut;
    private FileReader bufFeed;
    private BufferedReader fileIn;

    private MessageBox messageBox;

    public OrderIOManager (MessageBox mBox) {
        messageBox = mBox;
        try {
            bufFeed = new FileReader(filePath);
            fileIn = new BufferedReader(bufFeed);
            fileOut = new FileWriter(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MessageBox readHashMap() {
        String entry = "";
        MessageBox newBox = new MessageBox();
        try {
            while ( (entry = fileIn.readLine()) != null ) {
                String[] elements = entry.split(" ");
                newBox.putMessage(Integer.parseInt(elements[0]), elements[1], elements[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //update contents of messageBox
        return newBox;
    }

    public void writeHashMap() {
        try {
            for (int k : messageBox.getKeys()) {
                String[] contacts = messageBox.getEmailPhone(k);
                fileOut.write(k + " " + contacts[0] + " " + contacts[1]);
            }
            fileOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public static void main (String args[]) {
        MessageBox box = new MessageBox();
        box.putMessage(45, "testmail@mtu.edu", "7158675309");
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
