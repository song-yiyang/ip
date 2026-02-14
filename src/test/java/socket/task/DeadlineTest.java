package socket.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import socket.SocketException;

public class DeadlineTest {
    @Test
    public void normalTest() throws SocketException {
        Deadline deadline = new Deadline("test deadline", "2024-01-01");
        assertEquals("[D][ ] test deadline (by: Jan 1 2024)", deadline.toString());
        assertEquals("D | 0 | test deadline | 2024-01-01", deadline.toSaveString());
    }

    @Test
    public void invalidDateTest() {
        assertThrows(java.time.format.DateTimeParseException.class, () ->
                new Deadline("test deadline", "2024-1-1"));
    }
}
