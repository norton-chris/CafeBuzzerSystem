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

    public HashMap<Integer, MessageBox.Message> readHashMap() {
        HashMap<Integer, MessageBox.Message> rMap = null;
        try {
            messageBox.setOrders((HashMap<Integer, MessageBox.Message>) objIn.readObject());
            objIn = new ObjectInputStream(fileIn);
            rMap = (HashMap<Integer, MessageBox.Message>) objIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //update contents of messageBox
        messageBox.setOrders(rMap);

        return rMap;
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

    public static void main (String args[]) {
        MessageBox box = new MessageBox();
        box.putMessage(45, "lsstone@mtu.edu", "7158675309");
        File hashFile = new File(filePath);
        try {
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(hashFile));

            HashMap<Integer, MessageBox.Message> m = (HashMap<Integer, MessageBox.Message>) objIn.readObject();
            System.out.println(m.get(45).getEmail());
            System.out.println(m.get(45).getPhoneNum());

            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(hashFile));
            objOut.writeObject(box.getOrders());
            objOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
