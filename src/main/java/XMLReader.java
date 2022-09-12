package src.main.java;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;

public class XMLReader {

    private final File parsingFile;
    // L'oggetto doc rappresenta il file XML già parsificato tramite i metodi del pacchetto DOM.
    private final Document doc;

    public XMLReader(String file){
        try{
            this.parsingFile = new File("classes/"+file);
            /* Per il parsing del file XML, DOM mette a disposizione una classe DocumentBuilder istanziabile tramite
               una DocumentBuilderFactory. Il DocumentBuilder effettua il parsing tramite il metodo parse().
             */
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = dbf.newDocumentBuilder();
            this.doc = parser.parse(getParsingFile());
        } catch (ParserConfigurationException | IOException | NullPointerException | SAXException e) {
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

}
