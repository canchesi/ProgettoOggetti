package src.gallettabot.java;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import src.gallettabot.java.menus.*;
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
        //try{
        switch (messageType(request)){
            case 0 -> handleCommand(request);
            case 1 -> handleSubject(request);
            case 2 -> handleSubmenu(request);
            default -> throw new UnexpectedRequestException();
        }//} catch (Varie exceptions) {}
        setResponse();

        return this.response;
    }

    private void handleCommand(String handledCase) {//throws UnexpectedRequestException {
        switch (handledCase) {
            case "/start", "/restart" -> this.menu = new MainMenu(this.client);
            //default -> throw new UnexpectedCommandException();
        }
    }

    private void handleSubject(String handledCase) { //throws UnexpectedSubjectException {

        //int subjectSize = ((List<String>)this.client.getMongo().getDatabase("gallettabot").getCollection("menu").find(new Document("name", "subjects")).first().get("subjects")).size();
        //if (handledCase > 0 && handledCase < subjectSize)
            this.menu = new CommonMenu(client, handledCase);
        //else
        //    throw UnexpectedSubjectException

    }

    private void handleSubmenu(String handledCase) { //throws UnexpectedSubmenuException {
        //TODO Submenus
    }

    private void setResponse() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup((List<List<InlineKeyboardButton>>) (Object) this.menu.generateButtons().getAllButtons());
        this.response.setText(this.menu.getTextToPrint());
        this.response.setReplyMarkup(markup);
    }

    private byte messageType(String receivedMessage) {
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
