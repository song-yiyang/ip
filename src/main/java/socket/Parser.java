package socket;

import java.util.Scanner;

/**
 * Represents a parser that reads and makes sense of user input.
 */
public class Parser {
    private final Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Parses the next user input for downstream code.
     *
     * @return Array of Strings, each element corresponding to a different parameter.
     * @throws IllegalArgumentException If the command is unrecognized.
     * @throws SocketException If parameters are invalid (i.e. command is recognized but malformed).
     */
    public Input parseInput() throws IllegalArgumentException, SocketException {
        String input = scanner.next();
        try {
            Command command = Command.valueOf(input.toUpperCase());
            String[] parsed = new String[3];

            switch (command) {
            case MARK, UNMARK, DELETE, TODO, FIND -> parsed[0] = scanner.nextLine().strip();
            case DEADLINE -> {
                parsed = scanner.nextLine().strip().split(" /by ");
                if (parsed.length != 2) {
                    throw new SocketException("Invalid parameters. Usage: deadline <description> /by <deadline>");
                }
            }
            case EVENT -> {
                parsed = scanner.nextLine().strip().split(" /(from|to) ");
                if (parsed.length != 3) {
                    throw new SocketException("Invalid parameters. Usage: event <description>"
                            + " /from <date> /to <date>");
                }
            }
            default -> { }
            }
            return new Input(command, parsed);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unrecognised command.");
        }
    }
}
