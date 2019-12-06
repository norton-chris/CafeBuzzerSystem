import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EmailTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @Test
    void sendInitial() throws MessagingException {
        Email email = new Email();
        email.sendInitial("dummybuzzer@gmail.com");
    }

    @Test
    void sendInitial1() throws MessagingException {
        Email email = new Email();
        email.sendInitial("dummybuzzer");
    }

    @Test
    void sendInitial2() throws MessagingException {
        Email email = new Email();
        email.sendInitial("cnortonmtu.edu");
    }

    @Test
    void testEmailParser1 () {
        Email email = new Email();
        String string = email.parseEmail("eedolson");
        assertEquals("eedolson@mtu.edu", string);
    }
    @Test
    void testEmailParser2 () {
        Email email = new Email();
        String string = email.parseEmail("eedolson@gmail.com");
        assertEquals("eedolson@gmail.com", string);
    }

    @Test
    void main() {

    }
}