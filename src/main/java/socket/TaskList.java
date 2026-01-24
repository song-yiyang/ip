package socket;

import java.util.ArrayList;

import socket.task.Deadline;
import socket.task.Event;
import socket.task.Task;
import socket.task.Todo;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }

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

    public Task markAsDone(String[] parsed) throws SocketException {
        int index = this.getIndex(parsed[0], "mark");
        this.tasks.get(index).markAsDone();
        return this.tasks.get(index);
    }

    public Task markAsUndone(String[] parsed) throws SocketException {
        int index = getIndex(parsed[0], "unmark");
        this.tasks.get(index).unmark();
        return this.tasks.get(index);
    }

    public TaskAndSize addTodo(String[] parsed) throws SocketException {
        Todo todo = new Todo(parsed[0]);
        this.tasks.add(todo);
        return new TaskAndSize(todo, this.tasks.size());
    }

    public TaskAndSize addDeadline(String[] parsed) throws SocketException {
        Deadline deadline = new Deadline(parsed[0], parsed[1]);
        this.tasks.add(deadline);
        return new TaskAndSize(deadline, this.tasks.size());
    }

    public TaskAndSize addEvent(String[] parsed) throws SocketException {
        Event event = new Event(parsed[0], parsed[1], parsed[2]);
        this.tasks.add(event);
        return new TaskAndSize(event, this.tasks.size());
    }

    public TaskAndSize deleteTask(String[] parsed) throws SocketException {
        int index = this.getIndex(parsed[0], "delete");
        Task task = this.tasks.remove(index);
        return new TaskAndSize(task, this.tasks.size());
    }

    public String[] printTasks() {
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < this.tasks.size(); i++) {
            out.add('\t' + String.valueOf(i + 1) + ". " + this.tasks.get(i));
        }
        return out.toArray(new String[0]);
    }

    public String[] printMatchingTasks(String mask) {
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < this.tasks.size(); i++) {
            if (this.tasks.get(i).getDescription().contains(mask)) {
                out.add('\t' + String.valueOf(i + 1) + ". " + this.tasks.get(i));
            }
        }
        return out.toArray(new String[0]);
    }
}
