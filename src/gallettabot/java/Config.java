package src.gallettabot.java;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.io.*;

public class Config extends XMLReader{

    private final String token;

    public Config(String path){
        super(path);
        token = getTokenFromFile();
    }

    private String getTokenFromFile(){
        return this.getDoc().getDocumentElement().getAttribute("id");
    }

    public String getToken() {
        return token;
    }
}
