package src.gallettabot.java;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.codecs.jsr310.LocalTimeCodec;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.w3c.dom.Element;
import src.gallettabot.java.menus.Menu;

import java.time.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


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

        MessageHandler handler = new MessageHandler(client, update);

        try {
            SendMessage message = handler.handleRequest();
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } //catch (EccezioneFattaDaNoi e) {
          //da vedere
         //}
    }

}
