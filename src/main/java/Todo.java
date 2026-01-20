public class Todo extends Task {
    public Todo(String description) throws SocketException {
        super(description);
        if (description.isEmpty()) {
            throw new SocketException("Description of a Todo cannot be empty.");
        }
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
