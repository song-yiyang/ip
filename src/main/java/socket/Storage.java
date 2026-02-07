package socket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import socket.task.Deadline;
import socket.task.Event;
import socket.task.Task;
import socket.task.Todo;

/**
 * Deals with loading tasks from the file and saving tasks in the file.
 */
public class Storage {
    private static final String SAVE_PATH = "./data/tasklist.txt";

    /**
     * Loads previously stored list of tasks in an ArrayList of Task.
     *
     * @return Stored task list.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Path path = Paths.get(Storage.SAVE_PATH);
            if (Files.exists(path)) {
                Files.readAllLines(path).forEach(saveString -> {
                    String[] comps = saveString.split(" \\| ");

                    try {
                        switch (saveString.charAt(0)) {
                        case 'T' -> tasks.add(new Todo(comps[2], (comps[1].equals("1"))));
                        case 'D' -> tasks.add(new Deadline(comps[2], comps[1].equals("1"), comps[3]));
                        case 'E' -> tasks.add(new Event(comps[2], comps[1].equals("1"), comps[3], comps[4]));
                        default -> { }
                        }
                    } catch (SocketException e) {
                        System.out.println("This should not happen: " + e);
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e);
        }
        return tasks;
    }

    /**
     * Saves current task list into a local, human-readable file at SAVE_PATH.
     *
     * @param taskList ArrayList of Task, the current task list.
     */
    public void saveTasks(TaskList taskList) {
        try {
            Path path = Paths.get(Storage.SAVE_PATH);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

            // Write tasks to savefile, one file per line, according to their save-string format
            Files.write(path, taskList.getTaskList().stream().map(Task::toSaveString).toList());
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e);
        }
    }
}
