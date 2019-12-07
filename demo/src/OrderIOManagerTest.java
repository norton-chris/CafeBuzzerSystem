import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderIOManagerTest {

    MessageBox mbox;
    OrderIOManager io;

    @BeforeEach
    void setUp() {
        mbox = new MessageBox();
        io = new OrderIOManager(mbox);

    }

    @Test
    void IOTest1() {
        mbox.putMessage(1, "test@mail.com", "1234567890");
        io.writeHashMap(false);
        mbox.clearOrders();
        io.readHashMap();

        String[] emailPhone = mbox.getEmailPhone(1, false);

        assert emailPhone[0].equals("test@mail.com");
        assert emailPhone[1].equals("1234567890");
    }

    @Test
    void IOTest2() {
        mbox.putMessage(1, "test@mail.com", "1234567890");
        mbox.putMessage(123, "dummy@mail.com", "0987654321");
        io.writeHashMap(false);
        mbox.clearOrders();
        io.readHashMap();

        String[] emailPhone1 = mbox.getEmailPhone(1, false);
        String[] emailPhone2 = mbox.getEmailPhone(123, false);

        assert emailPhone1[0].equals("test@mail.com");
        assert emailPhone1[1].equals("1234567890");
        assert emailPhone2[0].equals("dummy@mail.com");
        assert emailPhone2[1].equals("0987654321");
    }

}
