package socket;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Socket {
    private static Ui ui;
    private static Storage storage;
    private static Parser parser;
    private static TaskList taskList;

    public static void main(String[] args) {
        Socket.ui = new Ui();
        Socket.storage = new Storage();
        Socket.parser = new Parser(new Scanner(System.in));
        Socket.taskList = new TaskList(Socket.storage.loadTasks());

        Socket.ui.printWelcome();

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
                case LIST -> ui.printTaskList(Socket.taskList);
                case MARK -> ui.printTaskDone(Socket.taskList.markAsDone(parsed));
                case UNMARK -> ui.printTaskUndone(Socket.taskList.markAsUndone(parsed));
                case TODO -> ui.printAddedTask(Socket.taskList.addTodo(parsed));
                case DEADLINE -> ui.printAddedTask(Socket.taskList.addDeadline(parsed));
                case EVENT -> ui.printAddedTask(Socket.taskList.addEvent(parsed));
                case DELETE -> ui.printDeletedTask(Socket.taskList.deleteTask(parsed));
                default -> ui.print("This should not happen. hmmm");
                }

                Socket.storage.saveTasks(Socket.taskList);
                // For convenience, because the only command that doesn't need to save is list
            } catch (IllegalArgumentException | SocketException e) {
                ui.print(e.getMessage());
            } catch (DateTimeParseException e) {
                ui.print("Date format not recognised. Please use yyyy-mm-dd format");
            } finally {
                System.out.println();
            }
        }
    }
}
