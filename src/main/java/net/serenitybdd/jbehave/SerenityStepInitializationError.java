package net.serenitybdd.jbehave;

public class SerenityStepInitializationError extends RuntimeException {
    public SerenityStepInitializationError(Exception cause) {
        super(cause);
    }
}
