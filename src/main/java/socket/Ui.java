package socket;

import socket.task.Task;

/**
 * Deals with interactions with the user.
 */
public class Ui {
    private static final String LOGO = """
            Hello! I'm Socket
            What can I do for you?
            Usage:
            list
            mark <task index>
            unmark <task index>
            delete <task index>
            todo <name>
            deadline <name> /by <date in yyyy-mm-dd>
            event <name> /from <date in yyyy-mm-dd> /to <date in yyyy-mm-dd>
            bye""";

    /**
     * Prints the welcome screen upon startup.
     *
     * @return Welcome string.
     */
    public String printWelcome() {
        return Ui.LOGO;
    }

    /**
     * Prints the full descriptive list of tasks given in an array of strings.
     *
     * @param tasks A variable number of strings of printable tasks.
     * @return String of list of tasks.
     */
    public String printTaskList(String ... tasks) {
        return "Here are the tasks in your list:\n\t" + String.join("\n\t", tasks);
    }

    /**
     * Prints a message informing a specific task has been marked as completed.
     *
     * @param task Task that was marked as completed.
     * @return Message of task marked.
     */
    public String printTaskDone(Task task) {
        return "Nice! I've marked this task as done:\n\t" + task.toString();
    }

    /**
     * Prints a message informing a specific task has been marked as incomplete.
     *
     * @param task Task that was marked as incomplete.
     * @return Message of task unmarked.
     */
    public String printTaskUndone(Task task) {
        return "OK, I've marked this task as not done yet:\n\t" + task.toString();
    }

    /**
     * Prints a message informing a specific task has been added and the current number of tasks.
     *
     * @param tas TaskAndSize record, containing added task and new total number of tasks.
     * @return Message of task added.
     */
    public String printAddedTask(TaskAndSize tas) {
        return "OK. I've added this task:\n\t" + tas.task() + "\nNow you have " + tas.size() + " tasks in the list.";
    }

    /**
     * Prints a message informing a specific task has been deleted and the current number of tasks.
     *
     * @param tas TaskAndSize record, containing deleted task and new total number of tasks.
     * @return Message of task deleted.
     */
    public String printDeletedTask(TaskAndSize tas) {
        return "OK. I've removed this task:\n\t" + tas.task() + "\nNow you have " + tas.size() + " tasks in the list.";
    }

    /**
     * Prints a list of tasks that were a result of a matching-substring search.
     *
     * @param tasks A variable number of strings of printable tasks matching search keyword.
     * @return String of list of matching tasks.
     */
    public String printMatchingTasks(String ... tasks) {
        return "Here are the matching tasks in your list:\n\t" + String.join("\n\t", tasks);
    }
}
