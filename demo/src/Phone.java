public class Phone {

    private int parsePhoneNumber(String input) {
        String chunks[] = input.split("[ ,./-]+");
        String out = "";
        for(String c: chunks) {
            out += c;
        }
        System.out.println(out);
        return Integer.parseInt(out);
    }

    public static void main(String args[]) {
        Phone phone = new Phone();
        System.out.println(phone.parsePhoneNumber("715-944-9124"));
    }
}
