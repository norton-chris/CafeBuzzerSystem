import java.io.Serializable;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

//holds the phone number and/or email of a user
//holds flags to tell if users want email, text, or both.
public class MessageBox extends Observable {

    public class Message {
        private String email;
        private String phoneNum;
        private boolean pNumFlag = false;
        private boolean emailFlag = false;

        public Message(String em, String pNum) {
            email = em;
            if (em.length() != 0) {
                emailFlag = true;
            }
            phoneNum = pNum;
            if (pNum.length() != 0) {
                pNumFlag = true;
            }
        }

        public boolean hasEmail() {
            return emailFlag;
        }

        public boolean hasPhone() {
            return pNumFlag;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneNum() {
            return phoneNum;
        }
    }

    private HashMap<Integer, Message> orders;

    public MessageBox() {
        orders = new HashMap<>();
    }

    //inserts a new message into the box
    public Message putMessage(int orderNum, String email, String pNum) {
        Message newOrder = new Message(email, pNum);
        orders.put(orderNum, newOrder);
        //get the observer's attention
        setChanged();
        notifyObservers();
        return newOrder;
    }

    //removes and returns a message from the box
    public Message removeMessage(int orderNum) {
        Message toRemove = orders.remove(orderNum);
        //get the observer's attention
        setChanged();
        notifyObservers();
        return toRemove;
    }

    public void clearOrders() {
        orders = new HashMap<Integer, Message>();
    }

    public HashMap<Integer, Message> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<Integer, Message> newOrders) {
        orders = newOrders;
    }

    public Set<Integer> getKeys(){
        try {
            return orders.keySet();
        } catch (NullPointerException e){
            System.err.println("There are no orders numbers in the HashMap");
        }
        return null; // Shouldn't hit this line if there are order numbers in the hashmap
    }

    //helper method for email and phone classes to get relevant information?
    //unsure if necessary
    public String[] getEmailPhone(int orderNumber) throws NullPointerException{
        String[] emailphone = new String[2];
        Message m = orders.get(orderNumber);
        if(m == null)
            throw new NullPointerException();
        if (m.emailFlag)
            emailphone[0] = m.getEmail();
        if (m.pNumFlag)
            emailphone[1] = m.getPhoneNum();
        orders.remove(orderNumber);
        //get the observer's attention
        setChanged();
        notifyObservers();
        return emailphone;
    }

    public boolean orderNumberAlreadyExists(int newOrderNum) {
        return orders.containsKey(newOrderNum);
    }
}