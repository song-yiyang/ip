package socket.task;

import java.time.LocalDate;

public class Deadline extends Task {
    private final LocalDate by;

    public Deadline(String description, String by) {
        this(description, false, by);
    }

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
