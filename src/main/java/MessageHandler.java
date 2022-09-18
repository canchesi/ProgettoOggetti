package src.main.java;

import jdk.jshell.execution.Util;
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
        // Metodo che invoca la funzionalità corrispondente in base al tipo di messaggio inviato dall'utente
        if(this.isUserMessageAcceptable) {
            try {
                switch (messageType(request)) {
                    case 0 -> handleCommand(request);
                    case 1 -> handleSubjectMenu();
                    case 2 -> handleFAQMenu(request);
                    case 3 -> handleFAQAnswerMenu(request);
                    default -> throw new UnexpectedRequestException();
                }
            } catch (UnexpectedCommandException | UnexpectedFunctionalityException ignored) {
                throw new UnexpectedRequestException();
            }
            // Se è impostato un menu di ritorno, lo inserisce in un messaggio di risposta
            if(this.menu != null)
                this.setResponse();

            return this.response;
        } else {
            throw new UnexpectedRequestException();
        }
    }

    private void handleCommand(String handledCase) throws UnexpectedCommandException {
        // In base al comando inviato, viene eseguita un'azione
        switch (handledCase) {
            case "/start", "/restart" -> this.menu = new MainMenu(this.client);
            case "/stop" -> this.isChatStoppable = true;
            default -> throw new UnexpectedCommandException();
        }
        // Se il bot ha mandato un messaggio precedentemente, viene settato un flag per l'eliminazione
        if (this.lastBotSentMessageId != null)
            this.isBotMessageDeletable = true;
    }

    // Crea un menu per le materie
    private void handleSubjectMenu() {
        this.menu = new SubjectMenu(client);
    }

    // Crea un menu per la stampa delle FAQ della materia selezionata
    private void handleFAQMenu(String handledCase) throws UnexpectedSubmenuException {
        this.menu = new FAQMenu(client, handledCase.split("=", 2)[1]);
    }

    // Crea un messaggio di risposta con la domanda selezionata
    private void handleFAQAnswerMenu (String handledCase) {
        this.menu = new FAQAnswerMenu(client, handledCase);
    }

    // Crea la risposta del bot in base al menu
    private void setResponse() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup((List<List<InlineKeyboardButton>>) (Object) this.menu.generateButtons().getAllButtons());
        this.response.setText(this.menu.getTextToPrint());
        this.response.setReplyMarkup(markup);
    }

    // Discrimina i messaggi ricevuti in base a come iniziano
    private byte messageType(String receivedMessage) {
        if (Utilities.isCommand(receivedMessage))
            return 0;
        else if (Utilities.isFAQReq(receivedMessage))
            return 1;
        else if (Utilities.isSubjectReq(receivedMessage))
            return 2;
        else if (Utilities.isQuestionReq(receivedMessage))
            return 3;

        return -1;
    }

    // Discrimina se il messaggio è un messaggio o è una riammissione per errato blocco del bot
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

        // Gestisce la richiesta del callback o del messaggio ricevuto
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

    // Se è un comando conosciuto al bot
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
