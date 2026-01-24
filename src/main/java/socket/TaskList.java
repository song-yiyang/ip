package socket;

import java.util.ArrayList;

import socket.task.Deadline;
import socket.task.Event;
import socket.task.Task;
import socket.task.Todo;

/**
 * Stores and maintains a list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Getter for the current list of tasks.
     *
     * @return Current task list.
     */
    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }

    /**
     * Parses a string containing an index into an integer.
     *
     * @param indexString String of the index.
     * @param msg Message corresponding to the parent operation, for customized error message.
     * @return Integer index.
     * @throws SocketException If index is out of bounds, or invalid and cannot be parsed.
     */
    public int getIndex(String indexString, String msg) throws SocketException {
        try {
            int index = Integer.parseInt(indexString) - 1;
            if (index >= this.tasks.size()) {
                throw new SocketException("You only have " + this.tasks.size() + " tasks!");
            }
            if (index < 0) {
                throw new SocketException("Task index needs to be at least 1.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new SocketException("Invalid parameters. Usage: " + msg + " <index>");
        }
    }

    /**
     * Marks a specific task as complete.
     *
     * @param parsed Array whose first element is the index of the task.
     * @return The specific task.
     * @throws SocketException If any exceptions from getIndex are thrown, passing the buck up.
     */
    public Task markAsDone(String[] parsed) throws SocketException {
        int index = this.getIndex(parsed[0], "mark");
        this.tasks.get(index).markAsDone();
        return this.tasks.get(index);
    }

    /**
     * Marks a specific task as incomplete.
     *
     * @param parsed Array whose first element is the index of the task.
     * @return The specific task.
     * @throws SocketException If any exceptions from getIndex are thrown, passing the buck up.
     */
    public Task markAsUndone(String[] parsed) throws SocketException {
        int index = getIndex(parsed[0], "unmark");
        this.tasks.get(index).unmark();
        return this.tasks.get(index);
    }

    /**
     * Adds a new Todo task.
     *
     * @param parsed Array whose first element is the description of the Todo.
     * @return TaskAndSize record of the Todo task and the new total number of tasks.
     * @throws SocketException If any exceptions are thrown from creating the Todo, passing the buck up.
     */
    public TaskAndSize addTodo(String[] parsed) throws SocketException {
        Todo todo = new Todo(parsed[0]);
        this.tasks.add(todo);
        return new TaskAndSize(todo, this.tasks.size());
    }

    /**
     * Adds a new Deadline task.
     *
     * @param parsed Array of {description, deadline date-string}.
     * @return TaskAndSize record of the Deadline task and the new total number of tasks.
     * @throws SocketException If any exceptions are thrown from creating the Deadline, passing the buck up.
     */
    public TaskAndSize addDeadline(String[] parsed) throws SocketException {
        Deadline deadline = new Deadline(parsed[0], parsed[1]);
        this.tasks.add(deadline);
        return new TaskAndSize(deadline, this.tasks.size());
    }

    /**
     * Adds a new Event task.
     *
     * @param parsed Array of {description, from date-string, to date-string}.
     * @return TaskAndSize record of the Event task and the new total number of tasks.
     * @throws SocketException If any exceptions are thrown from creating the Event, passing the buck up.
     */
    public TaskAndSize addEvent(String[] parsed) throws SocketException {
        Event event = new Event(parsed[0], parsed[1], parsed[2]);
        this.tasks.add(event);
        return new TaskAndSize(event, this.tasks.size());
    }

    /**
     * Deletes a task from the task list.
     *
     * @param parsed Array whose first element is the index of the task.
     * @return TaskAndSize record of the task and the new total number of tasks.
     * @throws SocketException If any exceptions are thrown from getIndex, passing the buck up.
     */
    public TaskAndSize deleteTask(String[] parsed) throws SocketException {
        int index = this.getIndex(parsed[0], "delete");
        Task task = this.tasks.remove(index);
        return new TaskAndSize(task, this.tasks.size());
    }

    /**
     * Converts the underlying task list into a printable array of Strings.
     *
     * @return String array containing every task.
     */
    public String[] printTasks() {
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < this.tasks.size(); i++) {
            out.add('\t' + String.valueOf(i + 1) + ". " + this.tasks.get(i));
        }
        return out.toArray(new String[0]);
    }
}
