//program for client application 
import java.rmi.*;
public class ClientRequest
{
    public static void main(String args[]) {
        String answer,value="Reflection in Java";
        try {
            // lookup method to find reference of remote object 
            Search access =
                    (Search)Naming.lookup("rmi://localhost:1900"+
                            "/CafeNotification");
            answer = access.query(value);
            System.out.println("Received " + value +
                    " " + answer+" from Cafe Server");
        }
        catch(Exception ae) {
            System.out.println(ae);
        }
    }
}