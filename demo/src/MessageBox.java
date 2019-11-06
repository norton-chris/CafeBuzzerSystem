//holds the phone number and/or email of a user
//holds flags to tell if users want email, text, or both.
public class MessageBox {

    private String email;
    private String phoneNum;
    private boolean pNumFlag = false;
    private boolean emailFlag = false;

    public MessageBox(String em, String pNum) {
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
