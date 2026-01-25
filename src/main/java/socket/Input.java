package socket;

/**
 * Encapsulates the user input for better processing.
 *
 * @param command Command enum corresponding to the user input.
 * @param parsed Parsed arguments, split by parameter.
 */
public record Input(Command command, String[] parsed) {}
