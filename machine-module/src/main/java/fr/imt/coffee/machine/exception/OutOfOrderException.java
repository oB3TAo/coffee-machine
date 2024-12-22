package fr.imt.coffee.machine.exception;

/**
 * Custom exception thrown when the coffee machine is out of order.
 */
public class OutOfOrderException extends Exception {
    // Default constructor
    public OutOfOrderException() {
        super("The coffee machine is out of order.");
    }

    // Constructor with a custom message
    public OutOfOrderException(String message) {
        super(message);
    }
}
