import java.util.Scanner;

public class Socket {
    private static final String hline = "____________________________________________________________";
    private static final String logo = "Hello! I'm Socket\n\tWhat can I do for you?";
    private static final String goodbye = "Bye. Hope to see you again soon!";

    private static void printText(String text) {
        System.out.println('\t' + Socket.hline);
        System.out.println('\t' + text);
        System.out.println('\t' + Socket.hline);
        System.out.println();
    }

    public static void main(String[] args) {
        Socket.printText(Socket.logo);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                Socket.printText(Socket.goodbye);
                break;
            } else {
                Socket.printText(input);
            }
        }
    }
}
