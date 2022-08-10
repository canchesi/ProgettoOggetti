package src.gallettabot.java;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import src.gallettabot.java.menus.MainMenu;
import src.gallettabot.java.menus.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageHandler {

    private final Update update;
    private final SendMessage response;
    private final DatabaseClient client;
    private Menu menu;


    public MessageHandler(DatabaseClient client, Update update) {
        Long chatId = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage().getChatId() : update.getMessage().getChatId();
        this.update = update;
        this.client = client;
        this.response = new SendMessage();
        this.response.setChatId(chatId);
        this.response.setText("Richiesta errata");
    }

    public SendMessage handleRequest() throws UnexpectedRequestException {
        ArrayList<Object> message = this.checkAndGetMessage();
        switch ((String) message.get(1)) {
            case "/start" -> {
                this.menu = new MainMenu(this.client);
                setResponse();
            }
            default -> {
                if (!Utilities.isInteger((String) message.get(1)))
                    throw new UnexpectedRequestException();
                else {

                }
            }
        }

        return this.response;
    }

    public SendMessage handleAutoRequest(String handledCase) {
        switch (handledCase) {

            case "/restart" -> {
                //pulizia
                this.menu = new MainMenu(this.client);
                setResponse();
            }
            default -> System.out.println("OK");
        }
        return this.response;
    }

    private void setResponse() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup((List<List<InlineKeyboardButton>>) (Object) this.menu.generateButtons().getAllButtons());
        this.response.setText("Seleziona la materia d'interesse");
        this.response.setReplyMarkup(markup);
    }

    public ArrayList<Object> checkAndGetMessage() {
        ArrayList<Object> message = new ArrayList<>();
        message.add(update.hasCallbackQuery() ? this.update.getCallbackQuery().getMessage().getMessageId() : this.update.getMessage().getMessageId());
        message.add(update.hasCallbackQuery() ? this.update.getCallbackQuery().getData() : this.update.getMessage().getText());
        return message;
    }

}
