import java.util.Scanner;

public class Parser {
    private final Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    public Input parseInput() throws IllegalArgumentException, SocketException {
        String input = scanner.next();
        Command command = Command.valueOf(input.toUpperCase());
        String[] parsed = new String[3];

        switch (command) {
            case BYE, LIST -> {}
            case MARK, UNMARK, DELETE, TODO -> parsed[0] = scanner.nextLine().strip();
            case DEADLINE -> {
                parsed = scanner.nextLine().strip().split(" /by ");
                if (parsed.length != 2) {
                    throw new SocketException("Invalid parameters. Usage: deadline <description> /by <deadline>");
                }
            }
            case EVENT -> {
                parsed = scanner.nextLine().strip().split(" /(from|to) ");
                if (parsed.length != 3) {
                    throw new SocketException("Invalid parameters. Usage: event <description>" +
                            " /from <date> /to <date>");
                }
            }
            default -> throw new IllegalArgumentException();
        }

        return new Input(command, parsed);
    }
}
