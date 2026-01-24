package socket;

import socket.task.Task;

public class Ui {
    private static final String HLINE = "____________________________________________________________";
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

    public void printWelcome() {
        System.out.println('\t' + Ui.HLINE);
        System.out.println(Ui.LOGO);
        System.out.println('\t' + Ui.HLINE);
        System.out.println();
    }

    public void printBye() {
        System.out.println('\t' + Ui.HLINE);
        System.out.println('\t' + Ui.GOODBYE);
        System.out.println('\t' + Ui.HLINE);
        System.out.println();
    }

    public void print(String str) {
        System.out.println('\t' + Ui.HLINE);
        System.out.println('\t' + str);
        System.out.println('\t' + Ui.HLINE);
    }

    public void printTaskList(TaskList taskList) {
        System.out.println('\t' + Ui.HLINE);
        System.out.println("\tHere are the tasks in your list:");
        for (String task : taskList.printTasks()) {
            System.out.println('\t' + task);
        }
        System.out.println('\t' + Ui.HLINE);
    }

    public void printTaskDone(Task task) {
        System.out.println('\t' + Ui.HLINE);
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t\t" + task);
        System.out.println('\t' + Ui.HLINE);
    }

    public void printTaskUndone(Task task) {
        System.out.println('\t' + Ui.HLINE);
        System.out.println("\tOK, I've marked this task as not done yet:");
        System.out.println("\t\t" + task);
        System.out.println('\t' + Ui.HLINE);
    }

    public void printAddedTask(TaskAndSize tas) {
        System.out.println('\t' + Ui.HLINE);
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t\t" + tas.task());
        System.out.println("\tNow you have " + tas.size() + " tasks in the list.");
        System.out.println('\t' + Ui.HLINE);
    }

    public void printDeletedTask(TaskAndSize tas) {
        System.out.println('\t' + Ui.HLINE);
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t\t" + tas.task());
        System.out.println("\tNow you have " + tas.size() + " tasks in the list.");
        System.out.println('\t' + Ui.HLINE);
    }

    public void printMatchingTasks(String[] tasks) {
        System.out.println('\t' + Ui.HLINE);
        System.out.println("\tHere are the matching tasks in your list:");
        for (String task : tasks) {
            System.out.println('\t' + task);
        }
        System.out.println('\t' + Ui.HLINE);
    }
}
