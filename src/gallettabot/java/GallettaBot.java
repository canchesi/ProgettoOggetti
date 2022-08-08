package src.gallettabot.java;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GallettaBot extends TelegramLongPollingBot{

    private final Config config;

    public GallettaBot(){
        super();
        this.config = new Config("src/gallettabot/config/config.xml");
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
        /*
         * if (update.getMessage().getText().equals("test")) {
         *     InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
         *     List <List< InlineKeyboardButton >> rowsInline = new ArrayList < > ();
         *     List < InlineKeyboardButton > rowInline = new ArrayList< >();
         *     InlineKeyboardButton a = new InlineKeyboardButton();
         *     a.setText("eh si");
         *     a.setUrl("https://www.google.com");
         *     rowInline.add(a);
         *     rowsInline.add(rowInline);
         *     markupInline.setKeyboard(rowsInline);
         *     m.setReplyMarkup(markupInline);
         * }
         * */
        m.setText("Buon pomeriggio!");
        try {
            execute(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
