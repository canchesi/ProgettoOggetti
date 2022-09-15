package src.main.java.exceptions;

public class UnexpectedRequestException extends RuntimeException{
    public UnexpectedRequestException(){
        super("Unknown request.");
    }
}
