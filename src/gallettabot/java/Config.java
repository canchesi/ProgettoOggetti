package src.gallettabot.java;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.io.*;

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
