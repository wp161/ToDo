package application.command;

/**
 * Represents an InvalidCommandException thrown when the given command is not valid
 */
public class InvalidCommandException extends Exception {
    /**
     * Constructs an InvalidCommandException
     * @param message message to be printed
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
