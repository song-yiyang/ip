package socket.task;

import socket.SocketException;

public class Todo extends Task {
    public Todo(String description) throws SocketException {
        this(description, false);
    }

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
