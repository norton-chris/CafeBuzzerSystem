import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.Set;

public class MessageBoxTest {

    @Test
    void testKeyList() throws MessagingException {
        MessageBox mb = new MessageBox();
        mb.putMessage(1, "eedolson", "6165668263");
        mb.putMessage(2, "cnorton", "6169637757");
        mb.putMessage(3, "edolson@macatawaponyclub.org", "616566000");
        mb.putMessage(4, "", "6169637757");
        mb.putMessage(5, "lstone", "");
        Set<Integer> set = mb.getKeys();
        Set<Integer> actual = new HashSet<>();
        actual.add(1);
        actual.add(2);
        actual.add(3);
        actual.add(4);
        actual.add(5);
        assertEquals(actual, set);
    }

    @Test
    void testGetEmailPhone1() {
        MessageBox mb = new MessageBox();
        mb.putMessage(1, "eedolson", "6165668263");
        mb.putMessage(2, "cnorton", "6169637757");
        mb.putMessage(3, "edolson@macatawaponyclub.org", "616566000");
        mb.putMessage(4, "", "6169637757");
        mb.putMessage(5, "lstone", "");
        String[] result = mb.getEmailPhone(1);
        assertEquals("eedolson", result[0]);
        assertEquals("6165668263", result[1]);
        result = mb.getEmailPhone(5);
        assertEquals("lstone", result[0]);
        assertEquals(null, result[1]);
    }

    @Test
    void testGetEmailPhone2() {
        MessageBox mb = new MessageBox();
        mb.putMessage(1, "", "");
        mb.putMessage(2, "cnorton", "6169637757");
        mb.putMessage(3, "edolson@macatawaponyclub.org", "616566000");
        mb.putMessage(4, "", "6169637757");
        mb.putMessage(5, "lstone", "");
        String[] result = mb.getEmailPhone(1);
        assertEquals("eedolson", result[0]);
        assertEquals("6165668263", result[1]);
        result = mb.getEmailPhone(5);
        assertEquals("lstone", result[0]);
        assertEquals(null, result[1]);
    }

    @Test
    void testClearOrders1() {
        MessageBox mb = new MessageBox();
        mb.putMessage(1, "eedolson", "6165668263");
        mb.putMessage(2, "cnorton", "6169637757");
        mb.putMessage(3, "edolson@macatawaponyclub.org", "616566000");
        mb.putMessage(4, "", "6169637757");
        mb.putMessage(5, "lstone", "");
        mb.clearOrders();
        assertEquals(0, mb.getKeys().size());
    }

    @Test
    void testRemoveMessage1() {
        MessageBox mb = new MessageBox();
        mb.putMessage(1, "eedolson", "6165668263");
        mb.putMessage(2, "cnorton", "6169637757");
        assertEquals(2, mb.getKeys().size());
        mb.removeMessage(2);
        assertEquals(1, mb.getKeys().size());
    }

    @Test
    void testOrderNumberAlreadyExists1() {
        MessageBox mb = new MessageBox();
        mb.putMessage(1, "eedolson", "6165668263");
        mb.putMessage(2, "cnorton", "6169637757");
        assertTrue(mb.orderNumberAlreadyExists(1));
        assertFalse(mb.orderNumberAlreadyExists(3));
    }
}