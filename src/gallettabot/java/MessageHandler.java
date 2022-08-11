package src.gallettabot.java;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import src.gallettabot.java.menus.CommonMenu;
import src.gallettabot.java.menus.MainMenu;
import src.gallettabot.java.menus.Menu;
import java.util.List;

public class MessageHandler {

    private final SendMessage response;
    private final DatabaseClient client;
    private Menu menu;


    public MessageHandler(DatabaseClient client) {
        this.client = client;
        this.response = new SendMessage();
        this.response.setText("Richiesta errata");
    }

    public SendMessage handleRequest(String request) throws UnexpectedRequestException {
        switch (messageType(request)){
            case 0 -> handleCommand(request);
            case 1 -> handleSubject(request);
            case 2 -> handleSubmenu(request);
            default -> throw new UnexpectedRequestException();
        }
        setResponse();

        return this.response;
    }

    private void handleSubmenu(String handledCase) {
        //TODO Submenus
    }

    private void handleSubject(String handledCase) {
        this.menu = new CommonMenu(client, handledCase);
    }

    public void handleCommand(String message) throws UnexpectedRequestException {
        switch (message) {
            case "/start", "/restart" -> this.menu = new MainMenu(this.client);
            default -> throw new UnexpectedRequestException();
        }
    }

    private void setResponse() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup((List<List<InlineKeyboardButton>>) (Object) this.menu.generateButtons().getAllButtons());
        this.response.setText(this.menu.getTextToPrint());
        this.response.setReplyMarkup(markup);
    }

    private static byte messageType(String receivedMessage) {
        if (Utilities.isCommand(receivedMessage))
            return 0;
        else if (Utilities.isByte(receivedMessage))
            return 1;
        else
            return 2;
    }

    public String checkAndGetMessage(Update update) {
        String receivedMessage;
        if (!update.hasCallbackQuery()) {
            receivedMessage = update.getMessage().getText();
            this.response.setChatId(update.getMessage().getChatId());
        } else {
            receivedMessage = update.getCallbackQuery().getData();
            this.response.setChatId(update.getCallbackQuery().getMessage().getChatId());
        }
        return receivedMessage;
    }


}
