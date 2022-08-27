package src.gallettabot.java;

import org.w3c.dom.*;
import java.util.*;

public class Config{

    // L'oggetto token il token per la connessione alle api di Telegram.
    private final String token;
    /* mongoCredentials è una Hashmap che contiene le informazioni fondamentali per la
       connessione al database. */
    private final Map<String, String> mongoCredentials;

    
    public Config(String path){

        mongoCredentials = new HashMap<>();
        // reader è un oggetto che fornisce i metodi base per l'accesso e la lettura ai file XML.
        XMLReader reader = new XMLReader(path);
        NodeList database = ((Element) reader.getDoc()
                .getElementsByTagName("config").item(0))
                .getElementsByTagName("database");

        token = ((Element)((Element) reader.getDoc().getElementsByTagName("config").item(0))
                    .getElementsByTagName("telegram-api").item(0))
                    .getElementsByTagName("token").item(0)
                    .getTextContent();
        mongoCredentials.put("url", ((Element) database.item(0))
                                    .getElementsByTagName("url").item(0)
                                    .getAttributes()
                                    .getNamedItem("value").getTextContent());
        mongoCredentials.put("username", ((Element) database.item(0))
                                    .getElementsByTagName("username").item(0)
                                    .getAttributes()
                                    .getNamedItem("value").getTextContent());
        mongoCredentials.put("password", ((Element) database.item(0))
                                    .getElementsByTagName("password").item(0)
                                    .getAttributes()
                                    .getNamedItem("value").getTextContent());

    }

    public Map<String, String> getMongoCredentials(){
        return mongoCredentials;
    }

    public String getToken() {
        return token;
    }
}
