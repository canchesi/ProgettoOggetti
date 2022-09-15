package src.main.java.exceptions;

public class UnexpectedFunctionalityException extends RuntimeException{
    public UnexpectedFunctionalityException() {
        super("Absent subject");
    }
}
