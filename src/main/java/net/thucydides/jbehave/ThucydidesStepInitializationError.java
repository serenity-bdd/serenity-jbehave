package net.thucydides.jbehave;

public class ThucydidesStepInitializationError extends RuntimeException {
    public ThucydidesStepInitializationError(Exception cause) {
        super(cause);
    }
}
