package socket.task;

import java.time.LocalDate;

/**
 * Represents a Deadline task.
 */
public class Deadline extends Task {
    private final LocalDate by;

    public Deadline(String description, String by) {
        this(description, false, by);
    }

    /**
     * Initializes a new Deadline task.
     *
     * @param description Deadline description or name.
     * @param isDone Boolean indicated whether it is completed (true) or not.
     * @param by Deadline due date date-string.
     */
    public Deadline(String description, boolean isDone, String by) {
        super(description, isDone);
        this.by = Task.parse(by);
    }

    @Override
    public String toSaveString() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + Task.saveDate(this.by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + Task.displayDate(this.by) + ")";
    }
}
