package src.gallettabot.java;

import org.bson.Document;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import src.gallettabot.java.exceptions.*;
import src.gallettabot.java.menus.*;

import java.util.*;

public class MessageHandler {

    private final SendMessage response;
    private final DatabaseClient client;
    private Object messageId;
    private boolean isAcceptable;
    private boolean isDeletable;
    private boolean isStoppable;
    private final ArrayList<String> acceptedCommands;
    private Menu menu;


    public MessageHandler(DatabaseClient client) {
        this.client = client;
        this.response = new SendMessage();
        this.isAcceptable = true;
        this.isDeletable = false;
        this.messageId = null;
        this.menu = null;
        this.isStoppable = false;
        this.acceptedCommands = new ArrayList<>(Arrays.asList("/start", "/restart", "/stop"));
    }

    public SendMessage handleRequest(String request) throws UnexpectedRequestException {
        if(this.isAcceptable) {
            try {
                switch (messageType(request)) {
                    case 0 -> handleCommand(request);
                    case 1 -> handleSubject(Byte.parseByte(request));
                    case 2 -> handleFAQAnswerMenu(request);
                    case 3 -> handleSubmenu(request);
                    default -> throw new UnexpectedRequestException();
                }
            } catch (UnexpectedCommandException | UnexpectedSubjectException ignored) {
                throw new UnexpectedRequestException();
            }
            if(this.menu != null)
                setResponse();

            return this.response;
        } else {
            throw new UnexpectedRequestException();
        }
    }

    private void handleCommand(String handledCase) throws UnexpectedCommandException {
        switch (handledCase) {
            case "/start", "/restart" -> this.menu = new MainMenu(this.client);
            case "/stop" -> this.isStoppable = true;
            default -> throw new UnexpectedCommandException();
        }
        if (this.messageId != null)
            this.isDeletable = true;
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

    private void handleFAQAnswerMenu (String handledCase) {
        this.menu = new FAQAnswerMenu(client, handledCase);
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
        else if (receivedMessage.startsWith("quest="))
            return 2;
        else
            return 3;
    }

    public String checkAndGetMessage(Update update) {
        String receivedMessage;
        this.isAcceptable = update.hasCallbackQuery();
        if (update.getMyChatMember() != null) {
            this.response.setChatId(update.getMyChatMember().getChat().getId());
            if (update.getMyChatMember().getNewChatMember().getStatus().equals("kicked"))
                return "/stop";
            else
                return "/start";
        }

        if (!this.isAcceptable) {
            receivedMessage = checkAndGetMessage(update.getMessage().getText());
            this.response.setChatId(update.getMessage().getChatId());
            this.messageId = update.getMessage().getMessageId();
        } else {
            receivedMessage = update.getCallbackQuery().getData();
            this.response.setChatId(update.getCallbackQuery().getMessage().getChatId());
        }
        return receivedMessage;
    }

    public String checkAndGetMessage(String message) {
        if (this.acceptedCommands.contains(message))
            this.isAcceptable = true;
        return message;
    }

    public boolean isDeletable() {
        return isDeletable;
    }

    public boolean isStoppable() {
        return isStoppable;
    }

    public int getMessageId() {
        return (int) messageId;
    }
}
