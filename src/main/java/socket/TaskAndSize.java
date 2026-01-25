package socket;

import socket.task.Task;

/**
 * Encapsulates a task and integer.
 * Used to improve readability when passing values around classes.
 *
 * @param task Any specific task, in this codebase the task of the current user action.
 * @param size Any integer, in this codebase the new total number of tasks.
 */
public record TaskAndSize(Task task, int size) {}
