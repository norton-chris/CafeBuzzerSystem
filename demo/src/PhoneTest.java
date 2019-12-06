import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    Phone phone;

    @BeforeEach
    void setUp() {
        phone = new Phone();

    }

    @Test
    void parseTest1() {
        try {
            assert "9068675309".equals(phone.parsePhoneNumber("9068675309"));
        } catch (BadPhoneNumberException e) {
            assert false;
        }
    }

    @Test
    void parseTest2() {
        try {
            assert "9068675309".equals(phone.parsePhoneNumber("906-867-5309"));
        } catch (BadPhoneNumberException e) {
            assert false;
        }
    }

    @Test
    void parseTest3() {
        try {
            assert "9068675309".equals(phone.parsePhoneNumber("906/867/5309"));
        } catch (BadPhoneNumberException e) {
            assert false;
        }
    }

    @Test
    void parseTest4() {
        try {
            assert "9068675309".equals(phone.parsePhoneNumber("(906)867.5309"));
        } catch (BadPhoneNumberException e) {
            assert false;
        }
    }

    @Test
    void parseTest5() {
        try {
            assert "9068675309".equals(phone.parsePhoneNumber("906 867 5309"));
        } catch (BadPhoneNumberException e) {
            assert false;
        }
    }

    @Test
    void parseTest6() {
        try {
            assert "9068675309".equals(phone.parsePhoneNumber(" 9 068  67 5  309 "));
        } catch (BadPhoneNumberException e) {
            assert false;
        }
    }

    @Test
    void parseTest7() {
        try {
            assert "9068675309".equals(phone.parsePhoneNumber("90 ./-)686.,/75()-309"));
        } catch (BadPhoneNumberException e) {
            assert false;
        }
    }

    @Test
    void sendMail() {
        assertThrows(
                NullPointerException.class,
                () -> phone.sendInitial("999 99999 9999"));

    }
}