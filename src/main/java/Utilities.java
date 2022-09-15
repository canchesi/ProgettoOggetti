package src.main.java;

public final class Utilities {

    /* public static boolean isByte(String str) {
        try{
            Byte.parseByte(str, 10);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    } */

    public static boolean isCommand(String str) {
        return str.startsWith("/");
    }

    public static boolean isLink(String str) {
        return str.startsWith("https://") || str.startsWith("http://");
    }

    public static boolean isFAQReq(String str) { return str.equals("faq"); }

    public static boolean isSubjectReq(String str) { return str.startsWith("subj="); }

    public static boolean isQuestionReq(String str) { return str.equals("quest:"); }

}
