package socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class ParserTest {
    @Test
    public void unrecognisedCommandTest() {
        String input = "akjshfkd";
        Scanner scanner = new Scanner(input);
        Parser parser = new Parser(scanner);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, parser::parseInput);
        assertEquals("Unrecognised command.", e.getMessage());
    }

    @Test
    public void normalTest() throws SocketException {
        String[] inputs = new String[] {"bye", "list", "mark 1", "unmark 2", "delete 3", "todo todo_task",
                "deadline ddl_task /by 2026-01-01", "event event_task /from 2025-01-01 /to 2026-01-01"};
        Command[] commands = {Command.BYE, Command.LIST, Command.MARK, Command.UNMARK, Command.DELETE,
                Command.TODO, Command.DEADLINE, Command.EVENT};
        String[][] parsed = {
                new String[] {null, null, null},
                new String[] {null, null, null},
                new String[] {"1", null, null},
                new String[] {"2", null, null},
                new String[] {"3", null, null},
                new String[] {"todo_task", null, null},
                new String[] {"ddl_task", "2026-01-01"},
                new String[] {"event_task", "2025-01-01", "2026-01-01"}
        };

        for (int i = 0; i < inputs.length; i++) {
            Parser parser = new Parser(new Scanner(inputs[i]));
            Input input = parser.parseInput();
            assertEquals(commands[i], input.command());
            assertArrayEquals(parsed[i], input.parsed());
        }
    }

    @Test
    public void invalidParamsTest1() {
        Parser parser = new Parser(new Scanner("deadline ddl_task by 2026-01-01"));
        String e_msg = "Invalid parameters. Usage: deadline <description> /by <deadline>";
        SocketException e = assertThrows(SocketException.class, parser::parseInput);
        assertEquals(e_msg, e.getMessage());
    }

    @Test
    public void invalidParamsTest2() {
        Parser parser = new Parser(new Scanner("deadline ddl_task /before 2026-01-01"));
        String e_msg = "Invalid parameters. Usage: deadline <description> /by <deadline>";
        SocketException e = assertThrows(SocketException.class, parser::parseInput);
        assertEquals(e_msg, e.getMessage());
    }

    @Test
    public void invalidParamsTest3() {
        Parser parser = new Parser(new Scanner("event event_task on 2026-01-01"));
        String e_msg = "Invalid parameters. Usage: event <description>"
                + " /from <date> /to <date>";
        SocketException e = assertThrows(SocketException.class, parser::parseInput);
        assertEquals(e_msg, e.getMessage());
    }

    @Test
    public void invalidParamsTest4() {
        Parser parser = new Parser(new Scanner("event event_task /from 2026-01-01 /until 2026-01-02"));
        String e_msg = "Invalid parameters. Usage: event <description>"
                + " /from <date> /to <date>";
        SocketException e = assertThrows(SocketException.class, parser::parseInput);
        assertEquals(e_msg, e.getMessage());
    }
}