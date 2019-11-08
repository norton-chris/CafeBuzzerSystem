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
        assertThrows(
                SendFailedException.class,
                () -> email.sendInitial("dummybuzzer"));
    }

    @Test
    void sendInitial2() throws MessagingException {
        Email email = new Email();
        assertThrows(
                SendFailedException.class,
                () -> email.sendInitial("cnortonmtu.edu"));
    }

    @Test
    void main() {

    }
}