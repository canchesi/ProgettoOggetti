package src.gallettabot.java.exceptions;

public class UnexpectedSubjectException extends RuntimeException{

    public UnexpectedSubjectException() {
        super("Absent subject");
    }

}
