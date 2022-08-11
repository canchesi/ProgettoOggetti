package src.gallettabot.java;

public class UnexpectedRequestException extends RuntimeException{

    public UnexpectedRequestException(){
        super("Unknown request.");
    }

    //messaggio inviato errore...

}
