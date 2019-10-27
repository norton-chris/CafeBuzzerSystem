import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EmailTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @Test
    void sendMail() throws MessagingException {
        Email email = new Email();
        email.sendMail("dummybuzzer@gmail.com");
    }

    @Test
    void sendMail1() throws MessagingException {
        Email email = new Email();
        assertThrows(
                SendFailedException.class,
                () -> email.sendMail("dummybuzzer"));
    }

    @Test
    void sendMail2() throws MessagingException {
        Email email = new Email();
        assertThrows(
                SendFailedException.class,
                () -> email.sendMail("cnortonmtu.edu"));
    }

    @Test
    void main() {

    }
}