package src.gallettabot.java;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import src.gallettabot.java.GallettaBot;

public class Main {
    public static void main(String[] args) {
        try {
            /*
             MongoClientURI URI = new MongoClientURI("mongodb://93.40.7.204:27017");
             MongoClient mongo = new MongoClient(URI);
             MongoDatabase database = mongo.getDatabase("test");
             MongoCollection<Document> collection = database.getCollection("prova");
             List<Integer> books = Arrays.asList(27464, 747854);
             Document person = new Document("_id", "jo").append("books", books);
             collection.insertOne(person);
             */
            //Config config = new Config("src/gallettabot/config/config.xml");
            //DatabaseClient client = new DatabaseClient(config);

            TelegramBotsApi telegram = new TelegramBotsApi(DefaultBotSession.class);
            telegram.registerBot(new GallettaBot());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
