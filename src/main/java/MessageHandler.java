package src.main.java;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import src.main.java.exceptions.*;
import src.main.java.menus.*;

import java.util.*;

public final class MessageHandler {

    private final SendMessage response;
    private final DatabaseClient client;
    private final ArrayList<String> acceptedCommands;
    private Object lastBotSentMessageId;
    private boolean isUserMessageAcceptable;
    private boolean isBotMessageDeletable;
    private boolean isChatStoppable;
    private Menu menu;


    public MessageHandler(DatabaseClient client) {
        this.client = client;
        this.response = new SendMessage();
        this.isUserMessageAcceptable = true;
        this.isBotMessageDeletable = false;
        this.lastBotSentMessageId = null;
        this.menu = null;
        this.isChatStoppable = false;
        this.acceptedCommands = new ArrayList<>(Arrays.asList("/start", "/restart", "/stop"));
    }

    public SendMessage handleRequest(String request) throws UnexpectedRequestException {
        if(this.isUserMessageAcceptable) {
            try {
                switch (messageType(request)) {
                    case 0 -> handleCommand(request);
                    case 1 -> handleSubjectMenu();
                    case 2 -> handelFAQMenu(request);
                    case 3 -> handleFAQAnswerMenu(request);
                    default -> throw new UnexpectedRequestException();
                }
            } catch (UnexpectedCommandException | UnexpectedFunctionalityException ignored) {
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
            case "/stop" -> this.isChatStoppable = true;
            default -> throw new UnexpectedCommandException();
        }
        if (this.lastBotSentMessageId != null)
            this.isBotMessageDeletable = true;
    }

    private void handleSubjectMenu() {
        this.menu = new SubjectMenu(client);
    }

    private void handelFAQMenu(String handledCase) throws UnexpectedSubmenuException {
        this.menu = new FAQMenu(client, Byte.parseByte(handledCase.split("=")[1]));
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
        else if (receivedMessage.equals("faq"))
            return 1;
        else if (receivedMessage.startsWith("subj="))
            return 2;
        else if (receivedMessage.startsWith("quest:"))
            return 3;

        return -1;
    }

    public String checkAndGetMessage(Update update) {
        String receivedMessage;
        this.isUserMessageAcceptable = update.hasCallbackQuery();
        if (update.getMyChatMember() != null) {
            this.response.setChatId(update.getMyChatMember().getChat().getId());
            this.isUserMessageAcceptable = true;
            if (update.getMyChatMember().getNewChatMember().getStatus().equals("kicked"))
                return "/stop";
            else
                return "/start";
        }

        if (!this.isUserMessageAcceptable) {
            receivedMessage = checkAndGetMessage(update.getMessage().getText());
            this.response.setChatId(update.getMessage().getChatId());
            this.lastBotSentMessageId = update.getMessage().getMessageId();
        } else {
            receivedMessage = update.getCallbackQuery().getData();
            this.response.setChatId(update.getCallbackQuery().getMessage().getChatId());
        }
        return receivedMessage;
    }

    public String checkAndGetMessage(String message) {
        if (this.acceptedCommands.contains(message))
            this.isUserMessageAcceptable = true;
        return message;
    }

    public boolean isBotMessageDeletable() {
        return isBotMessageDeletable;
    }

    public boolean isChatStoppable() {
        return isChatStoppable;
    }

    public int getLastBotSentMessageId() {
        return (int) lastBotSentMessageId;
    }
}
