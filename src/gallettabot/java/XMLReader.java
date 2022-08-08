package src.gallettabot.java;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class XMLReader {

    private File readingFile;
    private DocumentBuilder parser;

    public XMLReader(String path) {
        try{
            this.readingFile            = new File(path);
            DocumentBuilderFactory  dbf = DocumentBuilderFactory.newInstance();
            this.parser                 = dbf.newDocumentBuilder();
            Document                doc = parser.parse(getReadingFile());


        } catch (ParserConfigurationException | IOException | SAXException | NullPointerException e) {
            throw new RuntimeException(e);
        }



    }

    public File getReadingFile() {
        return readingFile;
    }
}
