package src.gallettabot.java.exceptions;

public class UnexpectedRequestException extends RuntimeException{

    public UnexpectedRequestException(){
        super("Unknown request.");
    }

    //messaggio inviato errore...

}
