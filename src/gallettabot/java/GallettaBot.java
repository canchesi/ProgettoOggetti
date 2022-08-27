package src.gallettabot.java;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import src.gallettabot.java.exceptions.UnexpectedRequestException;

/*
    Bisogna estendere la classe TelegramLongPollingBot per poter creare e gestire il backend del bot.
    È obbligatorio dover fare l'override di tre metodi: getBotUsername(), getBotToken() e onUpdateReceived().
 */
public class GallettaBot extends TelegramLongPollingBot{

    // L'oggetto config contiene le informazioni di base tramite lettura del file config.xml.
    private final Config config;
    /* L'oggetto client contiene i metodi per la connessione al database e ritorna la connessione stessa ottenuta
       tramite le API ufficiali di Mongo per Java. */
    private final DatabaseClient client;

    public GallettaBot(){
        super();
        this.config = new Config("src/gallettabot/config/config.xml");
        this.client = new DatabaseClient(this.config);
    }

    @Override
    public String getBotUsername() {
        return "GallettaBot";
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        /*



         */

        MessageHandler handler = new MessageHandler(client);
        SendMessage message = null;
        DeleteMessage toBeDeleted;
        Chat thisChat;
        int lastMessageId;

        try {
            message = handler.handleRequest(handler.checkAndGetMessage(update));
        } catch (UnexpectedRequestException e) {
            message = handler.handleRequest(handler.checkAndGetMessage("/start"));
        } finally {
            thisChat = new Chat(client, message.getChatId());
            try{
                lastMessageId = thisChat.getLastMessageId();
            } catch (NullPointerException ignored) {
                thisChat.deleteChat();
                lastMessageId = -1;
            }
            if (handler.isDeletable()) {
                toBeDeleted = new DeleteMessage(thisChat.getChatId(), handler.getMessageId());
                try {
                    execute(toBeDeleted);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                    thisChat.deleteChat();
                }
            }
            if (lastMessageId != -1) {
                try{
                    toBeDeleted = new DeleteMessage(thisChat.getChatId(), lastMessageId);
                    execute(toBeDeleted);
                    if (handler.isStoppable()) {
                        thisChat.deleteChat();
                    }
                } catch (TelegramApiException e) {
                    thisChat.deleteChat();
                }
            } else {
                thisChat.createDocument(thisChat.getChatId());
            }

            if(!handler.isStoppable())
                try {
                    thisChat.setLastMessageIdInDocument(execute(message).getMessageId());
                } catch (TelegramApiException e) {
                    //TODO Potrebbe scrivere un log perché tanto telegram non funziona
                    //thisChat.deleteChat();
                }
        }
    }

}
