package socket.task;

import socket.SocketException;

/**
 * Represents a Todo task.
 */
public class Todo extends Task {
    public Todo(String description) throws SocketException {
        this(description, false);
    }

    /**
     * Initializes a new Todo task.
     *
     * @param description Task description or name.
     * @param isDone Boolean indicated whether it is completed (true) or not.
     * @throws SocketException If description is empty.
     */
    public Todo(String description, boolean isDone) throws SocketException {
        super(description, isDone);
        if (description.isEmpty()) {
            throw new SocketException("Description of a Todo cannot be empty.");
        }
    }

    @Override
    public String toSaveString() {
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
