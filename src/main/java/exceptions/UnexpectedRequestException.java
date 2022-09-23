package src.main.java.exceptions;

public class UnexpectedRequestException extends GallettaBotException{
    public UnexpectedRequestException(){
        super("Unknown request.");
    }
}
