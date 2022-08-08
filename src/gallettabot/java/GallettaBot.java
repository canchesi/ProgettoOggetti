package src.gallettabot.java;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;




public class GallettaBot extends TelegramLongPollingBot{

    @Override
    public String getBotUsername() {
        return "GallettaBot a disposizione!";
    }

    @Override
    public String getBotToken() {

        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
