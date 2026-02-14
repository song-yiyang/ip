package socket;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Is the entry point for the Socket chatbot.
 * This class houses the main functionality.
 */
public class Socket {
    private final Ui ui;
    private final Storage storage;
    private final Parser parser;
    private final TaskList taskList;

    private Command commandType = Command.UNKNOWN;

    /**
     * Simple initializer that initializes all variables: ui, storage, parser and taskList.
     */
    public Socket() {
        ui = new Ui();
        storage = new Storage();
        parser = new Parser();
        taskList = new TaskList(storage.loadTasks());
    }

    /**
     * Prints the welcome statement.
     *
     * @return Welcome-string of Socket chatbot.
     */
    public String getWelcome() {
        return ui.printInfo();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String inputString) {
        String output;
        commandType = Command.UNKNOWN;

        try {
            Input input = parser.parseInput(new Scanner(inputString));
            commandType = input.command();
            String[] parsed = input.parsed();

            if (commandType == Command.BYE) {
                System.exit(0);
            }

            output = switch (commandType) {
            case HELP -> ui.printInfo();
            case LIST -> ui.printTaskList(taskList.printTasks());
            case MARK -> ui.printTaskDone(taskList.markAsDone(parsed[0]));
            case UNMARK -> ui.printTaskUndone(taskList.markAsUndone(parsed[0]));
            case TODO -> ui.printAddedTask(taskList.addTodo(parsed));
            case DEADLINE -> ui.printAddedTask(taskList.addDeadline(parsed));
            case EVENT -> ui.printAddedTask(taskList.addEvent(parsed));
            case DELETE -> ui.printDeletedTask(taskList.deleteTask(parsed[0]));
            case FIND -> ui.printMatchingTasks(taskList.printMatchingTasks(parsed[0]));
            default -> "This should not happen. hmmm";
            };

            storage.saveTasks(taskList);
            // For convenience, because the only command that doesn't need to save is list
        } catch (IllegalArgumentException | SocketException e) {
            output = e.getMessage();
        } catch (DateTimeParseException e) {
            output = "Date format not recognised. Please use yyyy-mm-dd format";
        }
        return output;
    }

    /**
     * Simple getter for commandType.
     *
     * @return commandType of the last command.
     */
    public Command getCommandType() {
        return commandType;
    }
}
