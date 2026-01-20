import java.util.Scanner;

public class Socket {
    private static final String HLINE = "____________________________________________________________";
    private static final String LOGO = "Hello! I'm Socket\n\tWhat can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";

    private static int numTasks = 0;
    private static final Task[] tasks = new Task[100];

    public static void main(String[] args) {
        System.out.println('\t' + Socket.HLINE);
        System.out.println('\t' + Socket.LOGO);
        System.out.println('\t' + Socket.HLINE);
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.next();

            System.out.println('\t' + Socket.HLINE);
            switch (input) {
            case "bye" -> {
                System.out.println('\t' + Socket.GOODBYE);
                System.out.println('\t' + Socket.HLINE);
                System.out.println();
                System.exit(0);
            }
            case "list" -> {
                for (int i = 0; i < Socket.numTasks; i++) {
                    System.out.println('\t' + String.valueOf(i + 1) + ". " + Socket.tasks[i]);
                }
            }
            case "mark" -> {
                int index = scanner.nextInt()-1;
                if (index >= Socket.numTasks) {
                    System.out.println("\tYou only have " + String.valueOf(Socket.numTasks) + " tasks!");
                } else {
                    Socket.tasks[index].markAsDone();
                    System.out.println("\tNice! I've marked this task as done:");
                    System.out.println("\t" + Socket.tasks[index]);
                }
            }
            case "unmark" -> {
                int index = scanner.nextInt()-1;
                if (index >= Socket.numTasks) {
                    System.out.println("\tYou only have " + String.valueOf(Socket.numTasks) + " tasks!");
                } else {
                    Socket.tasks[index].unmark();
                    System.out.println("\tOK, I've marked this task as not done yet:");
                    System.out.println("\t" + Socket.tasks[index]);
                }
            }
            case "add" -> {
                Socket.tasks[Socket.numTasks++] = new Task(scanner.nextLine().strip());
                System.out.println("\tadded: " + Socket.tasks[Socket.numTasks-1]);
            }
            default -> {
                System.out.println("\tUnrecognised command");
            }
            }

            System.out.println('\t' + Socket.HLINE);
            System.out.println();
        }
    }
}
