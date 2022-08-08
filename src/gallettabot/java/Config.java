package src.gallettabot.java;


public class Config{

    private final String token;

    public Config(String path){

        XMLReader config = new XMLReader(path);
        token = config.getDoc().getDocumentElement().getAttribute("id");

    }

    public String getToken() {
        return token;
    }
}
