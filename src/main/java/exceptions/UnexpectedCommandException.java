package src.main.java.exceptions;

public final class UnexpectedCommandException extends GallettaBotException{
    public  UnexpectedCommandException(){
        super("Unexpected command");
    }
}
