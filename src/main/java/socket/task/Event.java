package socket.task;

import java.time.LocalDate;

import socket.SocketException;

/**
 * Represents an Event task.
 */
public class Event extends Task {
    private final LocalDate from;
    private final LocalDate to;

    public Event(String description, String from, String to) throws SocketException {
        this(description, false, from, to);
    }

    /**
     * Initializes a new Event task.
     *
     * @param description Event description or name.
     * @param isDone Boolean indicated whether it is completed (true) or not.
     * @param from Event from date-string.
     * @param to Event to date-string.
     * @throws SocketException If event start date is after end date.
     */
    public Event(String description, boolean isDone, String from, String to) throws SocketException {
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
                + Task.saveDate(this.from) + " | " + Task.saveDate(this.to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + Task.displayDate(this.from)
                + " to: " + Task.displayDate(this.to) + ")";
    }
}
