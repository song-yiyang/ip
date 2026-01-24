import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Socket {
    private static Ui ui;

    private static final String SAVE_PATH = "./data/tasklist.txt";

    private static final ArrayList<Task> tasks = new ArrayList<>();

    private static int getIndex(Scanner scanner, String msg) throws SocketException {
        try {
            String indexString = scanner.nextLine().strip();
            int index = Integer.parseInt(indexString) - 1;
            if (index >= Socket.tasks.size()) {
                throw new SocketException("You only have " + Socket.tasks.size() + " tasks!");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new SocketException("Invalid parameters. Usage: " + msg + " <index>");
        }
    }

    private static void loadTasks() {
        try {
            Path path = Paths.get(Socket.SAVE_PATH);
            if (Files.exists(path)) {
                Files.readAllLines(path).forEach(saveString -> {
                    String[] comps = saveString.split(" \\| ");

                    try {
                        switch (saveString.charAt(0)) {
                            case 'T' -> Socket.tasks.add(new Todo(comps[2], (comps[1].equals("1"))));
                            case 'D' -> Socket.tasks.add(new Deadline(comps[2], comps[1].equals("1"), comps[3]));
                            case 'E' -> Socket.tasks.add(new Event(comps[2], comps[1].equals("1"), comps[3], comps[4]));
                        }
                    } catch (SocketException e) {
                        System.out.println("This should not happen: " + e);
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    private static void saveTasks() {
        try {
            Path path = Paths.get(Socket.SAVE_PATH);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

            Files.write(path, Socket.tasks.stream().map(Task::toSaveString).toList());
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public static void main(String[] args) {
        Socket.ui = new Ui();

        ui.printWelcome();
        Socket.loadTasks();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.next();

            try {
                Command command = Command.valueOf(input.toUpperCase());
                switch (command) {
                case BYE -> {
                    ui.printBye();
                    System.exit(0);
                }
                case LIST -> ui.printTaskList(Socket.tasks);
                case MARK -> {
                    int index = getIndex(scanner, "mark");
                    Socket.tasks.get(index).markAsDone();
                    ui.printTaskDone(Socket.tasks.get(index));
                }
                case UNMARK -> {
                    int index = getIndex(scanner, "unmark");
                    Socket.tasks.get(index).unmark();
                    ui.printTaskUndone(Socket.tasks.get(index));
                }
                case TODO -> {
                    Socket.tasks.add(new Todo(scanner.nextLine().strip()));
                    ui.printAddedTask(Socket.tasks.get(Socket.tasks.size() - 1), Socket.tasks.size());
                }
                case DEADLINE -> {
                    String[] info = scanner.nextLine().strip().split(" /by ");
                    if (info.length != 2) {
                        System.out.println(info);
                        throw new SocketException("Invalid parameters. Usage: deadline <description> /by <deadline>");
                    }
                    Socket.tasks.add(new Deadline(info[0], info[1]));
                    ui.printAddedTask(Socket.tasks.get(Socket.tasks.size() - 1), Socket.tasks.size());
                }
                case EVENT -> {
                    String[] info = scanner.nextLine().strip().split(" /(from|to) ");
                    if (info.length != 3) {
                        throw new SocketException("Invalid parameters. Usage: event <description>" +
                                " /from <date> /to <date>");
                    }
                    Socket.tasks.add(new Event(info[0], info[1], info[2]));
                    ui.printAddedTask(Socket.tasks.get(Socket.tasks.size() - 1), Socket.tasks.size());
                }
                case DELETE -> {
                    int index = getIndex(scanner, "delete");
                    Task task = Socket.tasks.remove(index);
                    ui.printDeletedTask(task, Socket.tasks.size());
                }
                default -> ui.print("This should not happen. hmmm");
                }

                saveTasks(); // For convenience, because the only command that doesn't need to save is list
            } catch (IllegalArgumentException e) {
                ui.print("Unrecognised command.");
            } catch (SocketException e) {
                ui.print(e.getMessage());
            } catch (DateTimeParseException e) {
                ui.print("Date format not recognised. Please use yyyy-mm-dd format");
            } finally {
                System.out.println();
            }
        }
    }
}
