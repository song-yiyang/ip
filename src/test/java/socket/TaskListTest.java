package socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import socket.task.Task;
import socket.task.Todo;

public class TaskListTest {
    private ArrayList<Task> generate(int n) throws SocketException {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            tasks.add(new Todo("dummy todo"));
        }
        return tasks;
    }

    @Test
    public void normalTest() throws SocketException {
        TaskList taskList = new TaskList(this.generate(6));
        for (int i = 1; i <= 6; i++) {
            assertEquals(i - 1, taskList.getIndex(String.valueOf(i), ""));
        }
    }

    @Test
    public void outOfBoundTest() throws SocketException {
        TaskList taskList = new TaskList(this.generate(6));
        String msg = "Task index needs to be at least 1.";
        for (int i = -2; i <= 0; i++) {
            int finalI = i;
            SocketException e = assertThrows(SocketException.class, () ->
                    taskList.getIndex(String.valueOf(finalI), ""));
            assertEquals(msg, e.getMessage());
        }

        msg = "You only have 6 tasks!";
        for (int i = 7; i <= 9; i++) {
            int finalI = i;
            SocketException e = assertThrows(SocketException.class, () ->
                    taskList.getIndex(String.valueOf(finalI), ""));
            assertEquals(msg, e.getMessage());
        }
    }

    @Test
    public void nanTest() throws SocketException {
        TaskList taskList = new TaskList(this.generate(6));
        String msg = "Invalid parameters. Usage: msg <index>";
        SocketException e = assertThrows(SocketException.class, () ->
                taskList.getIndex("jashfkj", "msg"));
        assertEquals(msg, e.getMessage());

        e = assertThrows(SocketException.class, () -> taskList.getIndex("1hi", "msg"));
        assertEquals(msg, e.getMessage());
    }
}
