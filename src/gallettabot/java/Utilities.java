package src.gallettabot.java;

public class Utilities {

    public static boolean isInteger(String str) {
        try{
            Integer.parseInt(str, 10);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
