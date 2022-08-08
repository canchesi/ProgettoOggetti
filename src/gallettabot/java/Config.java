package src.gallettabot.java;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.io.*;

public class Config extends XMLReader{


    public Config(String path) {
        try{
            DocumentBuilderFactory  dbf     = DocumentBuilderFactory.newInstance();
            File                    config  = new File(path);
            Document                doc     = parser.parse(config);
            this.parser                     = dbf.newDocumentBuilder();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

}
