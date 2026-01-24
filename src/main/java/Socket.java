import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Socket {
    private static final String HLINE = "____________________________________________________________";
    private static final String LOGO = """
            \tHello! I'm Socket
            \tWhat can I do for you?
            \tUsage:
            \tlist
            \tmark <task index>
            \tunmark <task index>
            \ttodo <name>
            \tdeadline <name> /by <date in yyyy-mm-dd>
            \tevent <name> /from <date in yyyy-mm-dd> /to <date in yyyy-mm-dd>
            \tbye""";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";

    private static final String SAVE_PATH = "./data/tasklist.txt";

    private static final ArrayList<Task> tasks = new ArrayList<>();

    private static void printAddedTask() {
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t\t" + Socket.tasks.get(Socket.tasks.size() - 1));
        System.out.println("\tNow you have " + Socket.tasks.size() + " tasks in the list.");
    }

    private static int getIndex(Scanner scanner, String msg) throws SocketException {
        try {
            String indexString = scanner.nextLine().strip();
            int index = Integer.parseInt(indexString) - 1;
            if (index >= Socket.tasks.size()) {
                throw new SocketException("You only have " + Socket.tasks.size() + " tasks!");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new SocketException("Invalid parameters. Usage: " + msg + " <index>");
        }
    }

    private static void loadTasks() {
        try {
            Path path = Paths.get(Socket.SAVE_PATH);
            if (Files.exists(path)) {
                Files.readAllLines(path).forEach(saveString -> {
                    String[] comps = saveString.split(" \\| ");

                    try {
                        switch (saveString.charAt(0)) {
                            case 'T' -> Socket.tasks.add(new Todo(comps[2], (comps[1].equals("1"))));
                            case 'D' -> Socket.tasks.add(new Deadline(comps[2], comps[1].equals("1"), comps[3]));
                            case 'E' -> Socket.tasks.add(new Event(comps[2], comps[1].equals("1"), comps[3], comps[4]));
                        }
                    } catch (SocketException e) {
                        System.out.println("This should not happen: " + e);
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    private static void saveTasks() {
        try {
            Path path = Paths.get(Socket.SAVE_PATH);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

            Files.write(path, Socket.tasks.stream().map(Task::toSaveString).toList());
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public static void main(String[] args) {
        System.out.println('\t' + Socket.HLINE);
        System.out.println(Socket.LOGO);
        System.out.println('\t' + Socket.HLINE);
        System.out.println();

        Socket.loadTasks();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.next();

            System.out.println('\t' + Socket.HLINE);

            try {
                Command command = Command.valueOf(input.toUpperCase());
                switch (command) {
                case BYE -> {
                    System.out.println('\t' + Socket.GOODBYE);
                    System.out.println('\t' + Socket.HLINE);
                    System.out.println();
                    System.exit(0);
                }
                case LIST -> {
                    System.out.println("\tHere are the tasks in your list:");
                    for (int i = 0; i < Socket.tasks.size(); i++) {
                        System.out.println('\t' + String.valueOf(i + 1) + ". " + Socket.tasks.get(i));
                    }
                }
                case MARK -> {
                    int index = getIndex(scanner, "mark");
                    Socket.tasks.get(index).markAsDone();
                    System.out.println("\tNice! I've marked this task as done:");
                    System.out.println("\t\t" + Socket.tasks.get(index));
                }
                case UNMARK -> {
                    int index = getIndex(scanner, "unmark");
                    Socket.tasks.get(index).unmark();
                    System.out.println("\tOK, I've marked this task as not done yet:");
                    System.out.println("\t\t" + Socket.tasks.get(index));
                }
                case TODO -> {
                    Socket.tasks.add(new Todo(scanner.nextLine().strip()));
                    Socket.printAddedTask();
                }
                case DEADLINE -> {
                    String[] info = scanner.nextLine().strip().split(" /by ");
                    if (info.length != 2) {
                        throw new SocketException("Invalid parameters. Usage: deadline <description> /by <deadline>");
                    }
                    Socket.tasks.add(new Deadline(info[0], info[1]));
                    Socket.printAddedTask();
                }
                case EVENT -> {
                    String[] info = scanner.nextLine().strip().split(" /(from|to) ");
                    if (info.length != 3) {
                        throw new SocketException("Invalid parameters. Usage: event <description>" +
                                " /from <date> /to <date>");
                    }
                    Socket.tasks.add(new Event(info[0], info[1], info[2]));
                    Socket.printAddedTask();
                }
                case DELETE -> {
                    int index = getIndex(scanner, "delete");
                    Task task = Socket.tasks.remove(index);
                    System.out.println("\tNoted. I've removed this task:");
                    System.out.println("\t\t" + task);
                    System.out.println("\tNow you have " + Socket.tasks.size() + " tasks in the list.");
                }
                default -> System.out.println("\tThis should not happen. hmmm");
                }

                saveTasks(); // For convenience, because the only command that doesn't need to save is list
            } catch (IllegalArgumentException e) {
                System.out.println("\tUnrecognised command.");
            } catch (SocketException e) {
                System.out.println("\t" + e.getMessage());
            } catch (DateTimeParseException e) {
                System.out.println("\t Date format not recognised. Please use yyyy-mm-dd format");
            } finally {
                System.out.println('\t' + Socket.HLINE);
                System.out.println();
            }
        }
    }
}
