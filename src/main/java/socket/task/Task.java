package socket.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an abstract Task, with support for description/name, completed or not, and date formatting.
 */
public abstract class Task {
    public static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("MMM d yyyy");
    public static final DateTimeFormatter SAVE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected final String description;
    protected boolean isDone;

    /**
     * Initializes a new Task.
     *
     * @param description Task description or name.
     * @param isDone Boolean indicated whether it is completed (true) or not.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public String getDescription() {
        return this.description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    protected static LocalDate parse(String dateString) {
        return LocalDate.parse(dateString);
    }

    protected static String saveDate(LocalDate date) {
        return date.format(Task.SAVE_PATTERN);
    }

    protected static String displayDate(LocalDate date) {
        return date.format(Task.PATTERN);
    }

    public abstract String toSaveString();

    @Override
    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.description;
    }
}
