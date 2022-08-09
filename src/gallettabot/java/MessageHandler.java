package src.gallettabot.java;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import src.gallettabot.java.menus.Menu;

import java.util.List;

public class MessageHandler {

    private final Menu menu;
    private final Update update;
    private final SendMessage response;

    public MessageHandler(DatabaseClient client, Update update) {
        this.update = update;
        this.response = new SendMessage();
        this.menu = new Menu(client);
        response.setChatId(update.getMessage().getChatId());
        response.setText("Richiesta errata");
    }

    public SendMessage handleRequest() { //throws EccezioneFattaDaNoi {
        switch (this.update.getMessage().getText()) {
            case "/start":
                getSubjectMenu();
                break;
            default:
                //throw ExxecioneFattaDaNoi
        }

        return this.response;
    }

    private void getSubjectMenu() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup((List<List<InlineKeyboardButton>>)(Object) this.menu.generateButtons().getAllButtons());
        this.response.setText("Seleziona la materia d'interesse");
        this.response.setReplyMarkup(markup);
    }

}
