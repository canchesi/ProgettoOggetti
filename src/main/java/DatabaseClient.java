package src.main.java;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public final class DatabaseClient {

    private final MongoClient mongo;

    /* Legge le informazioni necessarie alla connessione al db precedentemente lette dall'oggetto config
    * ed effettua l'accesso*/
    public DatabaseClient(Config config) {
        String url = config.getMongoCredentials().get("url");
        String username = config.getMongoCredentials().get("username");
        String password = config.getMongoCredentials().get("password");
        MongoClientURI uri = new MongoClientURI("mongodb://" + username + ":" + password + "@" + url);

        this.mongo = new MongoClient(uri);

    }

    public MongoClient getMongo() {
        return mongo;
    }
}
