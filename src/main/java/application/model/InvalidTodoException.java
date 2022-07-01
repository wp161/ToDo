package application.model;

/**
 * Represents an InvalidTodoException thrown when a field is invalid when creating a new Todo
 */
public class InvalidTodoException extends Exception {
    /**
     * Constructs a new InvalidTodoException
     * @param message message to be printed
     */
    public InvalidTodoException(String message) {
        super(message);
    }
}
