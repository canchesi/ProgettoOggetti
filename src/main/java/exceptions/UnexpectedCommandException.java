package src.main.java.exceptions;

public class UnexpectedCommandException extends RuntimeException{
    public  UnexpectedCommandException(){
        super("Unexpected command");
    }


}
