package src.gallettabot.java;

import org.bson.Document;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import src.gallettabot.java.exceptions.*;
import src.gallettabot.java.menus.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHandler {

    private final SendMessage response;
    private final DatabaseClient client;
    private boolean isAcceptable;
    private Menu menu;


    public MessageHandler(DatabaseClient client) {
        this.client = client;
        this.response = new SendMessage();
        this.response.setText("Richiesta errata");
        this.isAcceptable = false;
    }

    public SendMessage handleRequest(String request) throws UnexpectedRequestException {
        if(this.isAcceptable) {
            try {
                switch (messageType(request)) {
                    case 0 -> handleCommand(request);
                    case 1 -> handleSubject(Byte.parseByte(request));
                    case 2 -> {
                        return printMessage(request);
                    }
                    case 3 -> handleSubmenu(request);
                    default -> throw new UnexpectedRequestException();
                }
            } catch (UnexpectedCommandException | UnexpectedSubjectException ignored) {
                throw new UnexpectedRequestException();
            }
            setResponse();

            return this.response;
        } else {
            throw new UnexpectedRequestException();
        }
    }

    private void handleCommand(String handledCase) throws UnexpectedCommandException {
        switch (handledCase) {
            case "/start", "/restart" -> this.menu = new MainMenu(this.client);
            default -> throw new UnexpectedCommandException();
        }
    }

    private void handleSubject(byte handledCase) throws UnexpectedSubjectException {

        int subjectSize = ((List<String>)this.client.getMongo().getDatabase("gallettabot").getCollection("menus").find(new Document("name", "subjects")).first().get("subjects")).size();
        if (handledCase < 0 || handledCase >= subjectSize)
            throw new UnexpectedSubjectException();
        else
            this.menu = new CommonMenu(client, String.valueOf(handledCase));
    }

    private void handleSubmenu(String handledCase) throws UnexpectedSubmenuException {
        Map<String, String> request = new HashMap<>();
        String[] parsedHandledCase = handledCase.split(",");

        for (String field: parsedHandledCase) {
            String[] toBeInserted = field.split("=");
            request.put(toBeInserted[0], toBeInserted[1]);
        }

        switch (request.get("name")){
            case "faq" -> this.menu = new FAQMenu(client, request.get("subj"));
            case "quest" -> this.menu = new QuestionMenu(client, request.get("subj"));
            case "appointment" -> this.menu = new AppointmentMenu(client, request.get("subj"));
            default -> throw new UnexpectedSubmenuException();
        }

    }

    private SendMessage printMessage(String request) {
        this.response.setText(request.split("=")[1]);
        return this.response;
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
        else if (receivedMessage.startsWith("ans="))
            return 2;
        else
            return 3;
    }

    public String checkAndGetMessage(Update update) {
        String receivedMessage;
        this.isAcceptable = update.hasCallbackQuery();
        if (!this.isAcceptable) {
            receivedMessage = update.getMessage().getText();
            this.response.setChatId(update.getMessage().getChatId());
            if (receivedMessage.equals("/start"))
                this.isAcceptable = true;
        } else {
            receivedMessage = update.getCallbackQuery().getData();
            this.response.setChatId(update.getCallbackQuery().getMessage().getChatId());
        }
        return receivedMessage;
    }

    public String checkAndGetMessage(String message) {
        if (message.equals("/start") || message.equals("/restart"))
            this.isAcceptable = true;
        return message;
    }


}
