package src.gallettabot.java;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public class GallettaBot extends TelegramLongPollingBot{

    private final Config config;

    public GallettaBot(){
        super();
        this.config = new Config("src/gallettabot/config.xml");
    }

    @Override
    public String getBotUsername() {
        return "GallettaBot a disposizione!";
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage m = new SendMessage();
        m.setChatId(update.getMessage().getChatId().toString());
        m.setText("Dio porco");
        try {
            execute(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
