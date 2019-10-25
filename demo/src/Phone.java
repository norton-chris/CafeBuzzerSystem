public class Phone {

    private String parsePhoneNumber(String input) {
        String chunks[] = input.split("[ ,./)(-]+");
        String out = "";
        for(String c: chunks) {
            out += c;
        }
        return out;
    }

    public static void main(String args[]) {
        Phone phone = new Phone();
        System.out.println(phone.parsePhoneNumber("715-944-9124"));
        System.out.println(phone.parsePhoneNumber("(715)944-9124"));
        System.out.println(phone.parsePhoneNumber("715 944 9124"));
    }
}
