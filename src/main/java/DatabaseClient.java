package src.main.java;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.util.ArrayList;

public final class DatabaseClient {

    private final MongoClient mongo;

    /* Legge le informazioni necessarie alla connessione al db precedentemente lette dall'oggetto config
    * ed effettua l'accesso*/
    public DatabaseClient(Config config) {
        this.mongo = new MongoClient(new MongoClientURI(buildMongoURI(config)));
    }

    public MongoClient getMongo() {
        return mongo;
    }

    private String buildMongoURI(Config config) {
        StringBuilder uri = new StringBuilder(!config.getMongoCredentials().get("prefix").isEmpty() ? config.getMongoCredentials().get("prefix") : "mongodb" +"://");
        String[] whatToSearch = {"username", "password", "url", "extra"};
        String[] specialChars = {":", "@"};
        ArrayList<String> uriComponents = new ArrayList<>();
        byte i;

        for (String what: whatToSearch)
            uriComponents.add(config.getMongoCredentials().get(what));

        for (i = 0; i < 2; ++i)
            if (!uriComponents.get(i).isEmpty())
                uri.append(uriComponents.get(i)).append(specialChars[i]);
        uri.append(uriComponents.get(i++));
        if (!uriComponents.get(i).isEmpty())
            uri.append("/?").append(uriComponents.get(i));

        return uri.toString();
    }

}
