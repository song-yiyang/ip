import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    protected final String description;
    protected boolean isDone;
    protected static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MMM d yyyy");
    protected static final DateTimeFormatter savePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Task(String description) {
        this(description, false);
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
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
        return date.format(Task.savePattern);
    }

    protected static String displayDate(LocalDate date) {
        return date.format(Task.pattern);
    }

    public abstract String toSaveString();

    @Override
    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.description;
    }
}
