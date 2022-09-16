package src.main.java;

import org.w3c.dom.*;
import java.util.*;

public final class Config{

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
        putDatabaseCredential(database, "url");
        putDatabaseCredential(database, "username");
        putDatabaseCredential(database, "password");
        putDatabaseCredential(database, "extra");
        putDatabaseCredential(database, "prefix");

    }

    public Map<String, String> getMongoCredentials(){
        return mongoCredentials;
    }

    private void putDatabaseCredential(NodeList database,String field) {
        this.mongoCredentials.put(field, ((Element) database.item(0))
                .getElementsByTagName(field).item(0)
                .getAttributes()
                .getNamedItem("value").getTextContent());
    }

    public String getToken() {
        return token;
    }
}
