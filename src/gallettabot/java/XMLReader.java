package src.gallettabot.java;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class XMLReader {

    private final File parsingFile;
    private final Document doc;

    public XMLReader(String path){
        try{
            this.parsingFile = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = dbf.newDocumentBuilder();
            this.doc = parser.parse(getParsingFile());
        } catch (ParserConfigurationException | IOException | SAXException | NullPointerException e) {
            throw new RuntimeException(e);
        }

    }

    public File getParsingFile() {
        return parsingFile;
    }

    public String getParsingFileAbs(){
        return this.parsingFile.getAbsolutePath();
    }

    public Document getDoc() {
        return doc;
    }

    //-------------------------------
    public static void main(String[] args) {
        XMLReader prova = new XMLReader("src/gallettabot/config/config.xml");
        System.out.println(prova.getParsingFileAbs());
        System.out.println(prova.getParsingFile());
    }

}
