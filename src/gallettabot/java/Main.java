package src.gallettabot.java;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import src.gallettabot.java.GallettaBot;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi telegram = new TelegramBotsApi(DefaultBotSession.class);
            telegram.registerBot(new GallettaBot());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
