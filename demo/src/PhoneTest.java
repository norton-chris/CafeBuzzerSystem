import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    Phone phone;

    @BeforeEach
    void setUp() {
        phone = new Phone();

    }

    @Test
    void parseTest1() { assert "9068675309".equals(phone.parsePhoneNumber("9068675309")); }

    @Test
    void parseTest2() {
        assert "9068675309".equals(phone.parsePhoneNumber("906-867-5309"));
    }

    @Test
    void parseTest3() { assert "9068675309".equals(phone.parsePhoneNumber("906/867/5309")); }

    @Test
    void parseTest4() { assert "9068675309".equals(phone.parsePhoneNumber("(906)867.5309")); }

    @Test
    void parseTest5() {
        assert "9068675309".equals(phone.parsePhoneNumber("906 867 5309"));
    }

    @Test
    void parseTest6() {
        assert "9068675309".equals(phone.parsePhoneNumber(" 9 068  67 5  309 "));
    }

    @Test
    void parseTest7() {
        assert "9068675309".equals(phone.parsePhoneNumber("90 ./-)686.,/75()-309"));
    }

    @Test
    void sendMail() {
        try {
            phone.sendMail("999 999 9999");
        } catch (MessagingException e) {
            assert true;
            return;
        }
        assert false;
    }
}