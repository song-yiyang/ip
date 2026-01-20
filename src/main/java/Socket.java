import java.util.Scanner;

public class Socket {
    private static final String HLINE = "____________________________________________________________";
    private static final String LOGO = "Hello! I'm Socket\n\tWhat can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";

    private static int numTasks = 0;
    private static String[] tasks = new String[100];

    public static void main(String[] args) {
        System.out.println('\t' + Socket.HLINE);
        System.out.println('\t' + Socket.LOGO);
        System.out.println('\t' + Socket.HLINE);
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            System.out.println('\t' + Socket.HLINE);
            if (input.equals("bye")) {
                System.out.println('\t' + Socket.GOODBYE);
                System.out.println('\t' + Socket.HLINE);
                System.out.println();
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < Socket.numTasks; i++) {
                    System.out.println('\t' + String.valueOf(i+1) + ". " + Socket.tasks[i]);
                }
            } else {
                Socket.tasks[Socket.numTasks++] = input;
                System.out.println("\tadded: " + input);
            }

            System.out.println('\t' + Socket.HLINE);
            System.out.println();
        }
    }
}
