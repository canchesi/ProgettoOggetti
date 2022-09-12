package src.main.java.exceptions;

public class UnexpectedSubmenuException extends RuntimeException{
    public UnexpectedSubmenuException(){
        super("Unexpected submenu.");
    }
}
