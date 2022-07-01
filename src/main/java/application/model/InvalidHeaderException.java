package application.model;

/**
 * Represents an InvalidHeaderException thrown when the collected headers contain illegal or unknown header
 */
public class InvalidHeaderException extends Exception {
    /**
     * Constructs an InvalidHeaderException
     * @param message message to be printed
     */
    public InvalidHeaderException(String message) {
        super(message);
    }
}
