package src.gallettabot.java;

public final class Utilities {

    public static boolean isByte(String str) {
        try{
            Byte.parseByte(str, 10);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isCommand(String str) {
        return str.startsWith("/");
    }

    public static boolean isLink(String str) {
        return str.startsWith("https://") || str.startsWith("http://");
    }

}
