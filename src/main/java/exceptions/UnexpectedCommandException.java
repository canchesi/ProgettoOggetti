package src.main.java.exceptions;

public class UnexpectedCommandException extends GallettaBotException{
    public  UnexpectedCommandException(){
        super("Unexpected command");
    }
}
