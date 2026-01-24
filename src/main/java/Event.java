import java.time.LocalDate;

public class Event extends Task {
    private final LocalDate from;
    private final LocalDate to;

    public Event(String description, String from, String to) throws SocketException {
        this(description, false, from, to);
    }

    public Event(String description, boolean isDone, String from, String to) throws SocketException{
        super(description, isDone);
        this.from = Task.parse(from);
        this.to = Task.parse(to);

        if (this.from.isAfter(this.to)) {
            throw new SocketException("Event start date should not be after event end date.");
        }
    }

    @Override
    public String toSaveString() {
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description + " | "
                + Task.saveDate(this.from)+ " | " + Task.saveDate(this.to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + Task.displayDate(this.from)
                + " to: " + Task.displayDate(this.to) + ")";
    }
}
