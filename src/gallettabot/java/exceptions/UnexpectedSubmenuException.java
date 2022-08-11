package src.gallettabot.java.exceptions;

public class UnexpectedSubmenuException extends RuntimeException{
    public UnexpectedSubmenuException(){
        super("Unexpected submenu.");
    }
}
