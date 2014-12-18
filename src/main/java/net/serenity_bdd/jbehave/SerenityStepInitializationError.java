package net.serenity_bdd.jbehave;

public class SerenityStepInitializationError extends RuntimeException {
    public SerenityStepInitializationError(Exception cause) {
        super(cause);
    }
}
