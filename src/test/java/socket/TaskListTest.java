package socket;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import socket.task.Deadline;
import socket.task.Event;
import socket.task.Task;
import socket.task.Todo;

public class TaskListTest {
    private ArrayList<Task> generate(int n) throws SocketException {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            tasks.add(new Todo("dummy todo " + i));
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

    @Test
    public void addTest() throws SocketException {
        TaskList taskList = new TaskList(new ArrayList<>());

        // Test addTodo
        String[] todoParsed = {"new todo"};
        TaskAndSize todoResult = taskList.addTodo(todoParsed);
        assertEquals(1, todoResult.size());
        assertTrue(todoResult.task() instanceof Todo);
        assertEquals("new todo", todoResult.task().getDescription());

        // Test addDeadline
        String[] deadlineParsed = {"new deadline", "2024-01-01"};
        TaskAndSize deadlineResult = taskList.addDeadline(deadlineParsed);
        assertEquals(2, deadlineResult.size());
        assertTrue(deadlineResult.task() instanceof Deadline);
        assertEquals("new deadline", deadlineResult.task().getDescription());

        // Test addEvent
        String[] eventParsed = {"new event", "2024-01-01", "2024-01-02"};
        TaskAndSize eventResult = taskList.addEvent(eventParsed);
        assertEquals(3, eventResult.size());
        assertTrue(eventResult.task() instanceof Event);
        assertEquals("new event", eventResult.task().getDescription());
    }

    @Test
    public void deleteTest() throws SocketException {
        TaskList taskList = new TaskList(this.generate(5));
        TaskAndSize result = taskList.deleteTask("3");
        assertEquals(4, result.size());
        assertEquals("dummy todo 2", result.task().getDescription());
        assertEquals(4, taskList.getTaskList().size());
    }

    @Test
    public void printTest() throws SocketException {
        TaskList taskList = new TaskList(this.generate(3));
        String[] expected = {
            "\t1. [T][ ] dummy todo 0",
            "\t2. [T][ ] dummy todo 1",
            "\t3. [T][ ] dummy todo 2"
        };
        assertArrayEquals(expected, taskList.printTasks());
    }

    @Test
    public void printMatchingTest() throws SocketException {
        TaskList taskList = new TaskList(this.generate(3));
        taskList.addTodo(new String[]{"another one"});
        String[] expected = {
            "\t1. [T][ ] dummy todo 0",
            "\t2. [T][ ] dummy todo 1",
            "\t3. [T][ ] dummy todo 2"
        };
        assertArrayEquals(expected, taskList.printMatchingTasks("dummy"));

        String[] expected2 = {
            "\t4. [T][ ] another one"
        };
        assertArrayEquals(expected2, taskList.printMatchingTasks("another"));
    }
}
