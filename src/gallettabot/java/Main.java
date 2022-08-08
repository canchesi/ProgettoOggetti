package src.gallettabot.java;

import com.mongodb.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        try {
            /*
            * MongoClientURI URI = new MongoClientURI("mongodb://93.40.7.204:27017");
            * MongoClient mongo = new MongoClient(URI);
            * DB database = mongo.getDB("test");
            * DBCollection collection = database.getCollection("prova");
            * List<Integer> books = Arrays.asList(27464, 747854);
            * DBObject person = new BasicDBObject("_id", "jo").append("books", books);
            * collection.insert(person);
            * */
            TelegramBotsApi telegram = new TelegramBotsApi(DefaultBotSession.class);
            telegram.registerBot(new GallettaBot());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
