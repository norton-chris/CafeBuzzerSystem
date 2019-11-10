import java.io.*;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class OrderIOManager implements Observer {

    private static final String filePath = "orders.buz";
    private File hashFile;
    private FileOutputStream fileOut;
    private FileInputStream fileIn;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    private MessageBox messageBox;

    public OrderIOManager (MessageBox mBox) {
        messageBox = mBox;
        hashFile = new File(filePath);
        try {
            fileIn = new FileInputStream(hashFile);
            fileOut = new FileOutputStream(hashFile);
            objIn = new ObjectInputStream(fileIn);
            objOut = new ObjectOutputStream(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readHashMap() {
        try {
            messageBox.setOrders((HashMap<Integer, MessageBox.Message>) objIn.readObject());
            objIn = new ObjectInputStream(fileIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeHashMap() {
        try {
            objOut.writeObject(messageBox.getOrders());
            objOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
