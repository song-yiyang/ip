package socket.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import socket.SocketException;

public class EventTest {
    @Test
    public void normalTest() throws SocketException {
        Event event = new Event("test event", "2024-01-01", "2024-01-02");
        assertEquals("[E][ ] test event (from: Jan 1 2024 to: Jan 2 2024)", event.toString());
        assertEquals("E | 0 | test event | 2024-01-01 | 2024-01-02", event.toSaveString());
    }

    @Test
    public void invalidDateTest() {
        assertThrows(java.time.format.DateTimeParseException.class, () ->
                new Event("test event", "2024-1-1", "2024-01-02"));
        assertThrows(java.time.format.DateTimeParseException.class, () ->
                new Event("test event", "2024-01-01", "2024-1-2"));
    }
}
