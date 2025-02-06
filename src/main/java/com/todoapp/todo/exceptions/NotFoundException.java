package com.todoapp.todo.exceptions;

/**
 * {@code NotFoundException} is a custom exception class extending
 * {@link RuntimeException}. It is designed to be used when a requested
 * resource or entity cannot be found in the system.
 * <p>
 * This exception is unchecked, meaning that it does not need to be explicitly
 * caught or declared in the method signature. It is typically used to indicate
 * a programming error or an unexpected state of the application.
 */
public class NotFoundException extends RuntimeException {
    /**
     * Constructs a new {@code NotFoundException} with the specified detail
     * message. The message provides information about the specific resource or
     * entity that could not be found.
     *
     * @param message The detail message explaining why the exception was thrown.
     *                This message should be informative and helpful for
     *                debugging.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
