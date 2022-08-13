package src.gallettabot.java;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import src.gallettabot.java.exceptions.UnexpectedRequestException;


public class GallettaBot extends TelegramLongPollingBot{

    private final Config config;
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
            lastMessageId = thisChat.getLastMessageId();
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
