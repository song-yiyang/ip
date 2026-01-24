import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Socket {
    private static Ui ui;
    private static Storage storage;
    private static Parser parser;

    private static ArrayList<Task> tasks;

    private static int getIndex(String indexString, String msg) throws SocketException {
        try {
            int index = Integer.parseInt(indexString) - 1;
            if (index >= Socket.tasks.size()) {
                throw new SocketException("You only have " + Socket.tasks.size() + " tasks!");
            }
            if (index < 0) {
                throw new SocketException("Task index needs to be at least 1.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new SocketException("Invalid parameters. Usage: " + msg + " <index>");
        }
    }

    public static void main(String[] args) {
        Socket.ui = new Ui();
        Socket.storage = new Storage();
        Socket.parser = new Parser(new Scanner(System.in));

        Socket.ui.printWelcome();
        Socket.tasks = Socket.storage.loadTasks();

        while (true) {
            try {
                Input input = Socket.parser.parseInput();
                Command command = input.command();
                String[] parsed = input.parsed();

                switch (command) {
                case BYE -> {
                    ui.printBye();
                    System.exit(0);
                }
                case LIST -> ui.printTaskList(Socket.tasks);
                case MARK -> {
                    int index = getIndex(parsed[0], "mark");
                    Socket.tasks.get(index).markAsDone();
                    ui.printTaskDone(Socket.tasks.get(index));
                }
                case UNMARK -> {
                    int index = getIndex(parsed[0], "unmark");
                    Socket.tasks.get(index).unmark();
                    ui.printTaskUndone(Socket.tasks.get(index));
                }
                case TODO -> {
                    Socket.tasks.add(new Todo(parsed[0]));
                    ui.printAddedTask(Socket.tasks.get(Socket.tasks.size() - 1), Socket.tasks.size());
                }
                case DEADLINE -> {
                    Socket.tasks.add(new Deadline(parsed[0], parsed[1]));
                    ui.printAddedTask(Socket.tasks.get(Socket.tasks.size() - 1), Socket.tasks.size());
                }
                case EVENT -> {
                    Socket.tasks.add(new Event(parsed[0], parsed[1], parsed[2]));
                    ui.printAddedTask(Socket.tasks.get(Socket.tasks.size() - 1), Socket.tasks.size());
                }
                case DELETE -> {
                    int index = getIndex(parsed[0], "delete");
                    Task task = Socket.tasks.remove(index);
                    ui.printDeletedTask(task, Socket.tasks.size());
                }
                default -> ui.print("This should not happen. hmmm");
                }

                Socket.storage.saveTasks(Socket.tasks);
                // For convenience, because the only command that doesn't need to save is list
            } catch (IllegalArgumentException e) {
                ui.print("Unrecognised command.");
            } catch (SocketException e) {
                ui.print(e.getMessage());
            } catch (DateTimeParseException e) {
                ui.print("Date format not recognised. Please use yyyy-mm-dd format");
            } finally {
                System.out.println();
            }
        }
    }
}
