package socket;

import socket.task.Task;

/**
 * Deals with interactions with the user.
 */
public class Ui {
    private static final String H_LINE = "____________________________________________________________";
    private static final String LOGO = """
            \tHello! I'm Socket
            \tWhat can I do for you?
            \tUsage:
            \tlist
            \tmark <task index>
            \tunmark <task index>
            \tdelete <task index>
            \ttodo <name>
            \tdeadline <name> /by <date in yyyy-mm-dd>
            \tevent <name> /from <date in yyyy-mm-dd> /to <date in yyyy-mm-dd>
            \tbye""";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";

    /**
     * Prints the welcome screen upon startup.
     */
    public void printWelcome() {
        System.out.println('\t' + Ui.H_LINE);
        System.out.println(Ui.LOGO);
        System.out.println('\t' + Ui.H_LINE);
        System.out.println();
    }

    /**
     * Prints the goodbye message before exiting the chatbot.
     */
    public void printBye() {
        System.out.println('\t' + Ui.H_LINE);
        System.out.println('\t' + Ui.GOODBYE);
        System.out.println('\t' + Ui.H_LINE);
        System.out.println();
    }

    /**
     * Prints any one-line string enclosed in the default style of 2 lines.
     *
     * @param str String to be printed.
     */
    public void print(String str) {
        System.out.println('\t' + Ui.H_LINE);
        System.out.println('\t' + str);
        System.out.println('\t' + Ui.H_LINE);
    }

    /**
     * Prints the full descriptive list of tasks given in an array of strings.
     *
     * @param tasks String array of printable tasks.
     */
    public void printTaskList(String[] tasks) {
        System.out.println('\t' + Ui.H_LINE);
        System.out.println("\tHere are the tasks in your list:");
        for (String task : tasks) {
            System.out.println('\t' + task);
        }
        System.out.println('\t' + Ui.H_LINE);
    }

    /**
     * Prints a message informing a specific task has been marked as completed.
     *
     * @param task Task that was marked as completed.
     */
    public void printTaskDone(Task task) {
        System.out.println('\t' + Ui.H_LINE);
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t\t" + task);
        System.out.println('\t' + Ui.H_LINE);
    }

    /**
     * Prints a message informing a specific task has been marked as incomplete.
     *
     * @param task Task that was marked as incomplete.
     */
    public void printTaskUndone(Task task) {
        System.out.println('\t' + Ui.H_LINE);
        System.out.println("\tOK, I've marked this task as not done yet:");
        System.out.println("\t\t" + task);
        System.out.println('\t' + Ui.H_LINE);
    }

    /**
     * Prints a message informing a specific task has been added and the current number of tasks.
     *
     * @param tas TaskAndSize record, containing added task and new total number of tasks.
     */
    public void printAddedTask(TaskAndSize tas) {
        System.out.println('\t' + Ui.H_LINE);
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t\t" + tas.task());
        System.out.println("\tNow you have " + tas.size() + " tasks in the list.");
        System.out.println('\t' + Ui.H_LINE);
    }

    /**
     * Prints a message informing a specific task has been deleted and the current number of tasks.
     *
     * @param tas TaskAndSize record, containing deleted task and new total number of tasks.
     */
    public void printDeletedTask(TaskAndSize tas) {
        System.out.println('\t' + Ui.H_LINE);
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t\t" + tas.task());
        System.out.println("\tNow you have " + tas.size() + " tasks in the list.");
        System.out.println('\t' + Ui.H_LINE);
    }

    /**
     * Prints a list of tasks that were a result of a matching-substring search.
     *
     * @param tasks String array of printable tasks.
     */
    public void printMatchingTasks(String[] tasks) {
        System.out.println('\t' + Ui.H_LINE);
        System.out.println("\tHere are the matching tasks in your list:");
        for (String task : tasks) {
            System.out.println('\t' + task);
        }
        System.out.println('\t' + Ui.H_LINE);
    }
}
