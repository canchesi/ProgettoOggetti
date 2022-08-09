package src.gallettabot.java;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.w3c.dom.Element;

import java.util.Iterator;


public class GallettaBot extends TelegramLongPollingBot{

    private final Config config;
    private final DatabaseClient client;

    public GallettaBot(){
        super();
        this.config = new Config("src/gallettabot/config/config.xml");
        this.client = new DatabaseClient(this.config);
    }

    @Override
    public String getBotUsername() {
        return "GallettaBot a disposizione!\n";
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage m = new SendMessage();
        m.setChatId(update.getMessage().getChatId().toString());
        m.setText("Buon pomeriggio! Sono quel porco di dio.");

        try {
            execute(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMenu(){

    }
}
