package socket.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import socket.SocketException;

public class TodoTest {
    @Test
    public void normalTest() throws SocketException {
        Todo todo = new Todo("test todo");
        assertEquals("[T][ ] test todo", todo.toString());
        assertEquals("T | 0 | test todo", todo.toSaveString());
    }

    @Test
    public void emptyDescriptionTest() {
        SocketException e = assertThrows(SocketException.class, () -> new Todo(""));
        assertEquals("Description of a Todo cannot be empty.", e.getMessage());
    }
}
