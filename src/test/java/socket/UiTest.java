package socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import socket.task.Todo;

public class UiTest {
    @Test
    public void printInfoTest() {
        Ui ui = new Ui();
        String expected = """
            Hello! I'm Socket
            What can I do for you?
            List of commands:
            help
            list
            mark <task index>
            unmark <task index>
            delete <task index>
            todo <name>
            deadline <name> /by <date in yyyy-mm-dd>
            event <name> /from <date in yyyy-mm-dd> /to <date in yyyy-mm-dd>
            bye""";
        assertEquals(expected, ui.printInfo());
    }

    @Test
    public void printTaskListTest() {
        Ui ui = new Ui();
        String[] tasks = {"task 1", "task 2"};
        String expected = "Here are the tasks in your list:\n\ttask 1\n\ttask 2";
        assertEquals(expected, ui.printTaskList(tasks));
    }

    @Test
    public void printTaskDoneTest() throws SocketException {
        Ui ui = new Ui();
        Todo todo = new Todo("test todo");
        todo.markAsDone();
        String expected = "Nice! I've marked this task as done:\n\t" + todo.toString();
        assertEquals(expected, ui.printTaskDone(todo));
    }

    @Test
    public void printTaskUndoneTest() throws SocketException {
        Ui ui = new Ui();
        Todo todo = new Todo("test todo");
        String expected = "OK, I've marked this task as not done yet:\n\t" + todo.toString();
        assertEquals(expected, ui.printTaskUndone(todo));
    }

    @Test
    public void printAddedTaskTest() throws SocketException {
        Ui ui = new Ui();
        Todo todo = new Todo("test todo");
        TaskAndSize tas = new TaskAndSize(todo, 1);
        String expected = "OK. I've added this task:\n\t" + todo.toString() + "\nNow you have 1 tasks in the list.";
        assertEquals(expected, ui.printAddedTask(tas));
    }

    @Test
    public void printDeletedTaskTest() throws SocketException {
        Ui ui = new Ui();
        Todo todo = new Todo("test todo");
        TaskAndSize tas = new TaskAndSize(todo, 0);
        String expected = "OK. I've removed this task:\n\t" + todo.toString() + "\nNow you have 0 tasks in the list.";
        assertEquals(expected, ui.printDeletedTask(tas));
    }

    @Test
    public void printMatchingTasksTest() {
        Ui ui = new Ui();
        String[] tasks = {"matching task 1", "matching task 2"};
        String expected = "Here are the matching tasks in your list:\n\tmatching task 1\n\tmatching task 2";
        assertEquals(expected, ui.printMatchingTasks(tasks));
    }
}
