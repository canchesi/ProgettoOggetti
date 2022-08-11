package src.gallettabot.java;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



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

        try {
            message = handler.handleRequest(handler.checkAndGetMessage(update));
        } catch (UnexpectedRequestException e) {
            message = handler.handleRequest("/restart");
        } finally {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                //TODO Potrebbe scrivere un log perché tanto telegram non funziona
                e.printStackTrace();
            }
        }
    }

}
