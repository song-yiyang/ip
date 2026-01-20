import java.util.Scanner;

public class Socket {
    private static final String HLINE = "____________________________________________________________";
    private static final String LOGO = "Hello! I'm Socket\n\tWhat can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";

    private static int numTasks = 0;
    private static final Task[] tasks = new Task[100];

    private static void printAddedTask() {
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t\t" + Socket.tasks[Socket.numTasks-1]);
        System.out.println("\tNow you have " + Socket.numTasks + " tasks in the list.");
    }

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
                System.out.println("\tHere are the tasks in your list:");
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
            case "todo" -> {
                Socket.tasks[Socket.numTasks++] = new Todo(scanner.nextLine().strip());
                Socket.printAddedTask();
            }
            case "deadline" -> {
                String[] info = scanner.nextLine().strip().split(" /by ");
                Socket.tasks[Socket.numTasks++] = new Deadline(info[0], info[1]);
                Socket.printAddedTask();
            }
            case "event" -> {
                String[] info = scanner.nextLine().strip().split(" /(from|to) ");
                Socket.tasks[Socket.numTasks++] = new Event(info[0], info[1], info[2]);
                Socket.printAddedTask();
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
