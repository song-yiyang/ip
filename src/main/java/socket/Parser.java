package socket;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents a parser that reads and makes sense of user input.
 */
public class Parser {
    private Scanner scanner;

    public Parser() { }

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Parses the next user input, using the default scanner during initialization
     * Dummy redirection function so that ParserTest works without modification.
     *
     * @return Array of Strings, each element corresponding to a different parameter.
     * @throws IllegalArgumentException If the command is unrecognized.
     * @throws SocketException If parameters are invalid (i.e. command is recognized but malformed).
     */
    public Input parseInput() throws IllegalArgumentException, SocketException {
        return parseInput(scanner);
    }

    /**
     * Parses the next user input for downstream code.
     *
     * @param scanner Scanner object that contains the user input.
     * @return Array of Strings, each element corresponding to a different parameter.
     * @throws IllegalArgumentException If the command is unrecognized.
     * @throws SocketException If parameters are invalid (i.e. command is recognized but malformed).
     */
    public Input parseInput(Scanner scanner) throws IllegalArgumentException, SocketException {
        String input = scanner.next();
        try {
            Command command = Command.valueOf(input.toUpperCase());
            String[] parsed = new String[3];

            switch (command) {
            case MARK, UNMARK, DELETE, TODO, FIND -> {
                try {
                    parsed[0] = scanner.nextLine().strip();
                } catch (NoSuchElementException e) {
                    throw new SocketException("Incomplete arguments. Please provide text or index.");
                }
            }
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
