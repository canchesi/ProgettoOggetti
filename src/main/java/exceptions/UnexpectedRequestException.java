package src.main.java.exceptions;

public final class UnexpectedRequestException extends GallettaBotException{
    public UnexpectedRequestException(){
        super("Unknown request.");
    }
}
