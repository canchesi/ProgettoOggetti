package src.main.java.exceptions;

public abstract class GallettaBotException extends RuntimeException{

    public GallettaBotException(String error){
        super(error);
    }

}
