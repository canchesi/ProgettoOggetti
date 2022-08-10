package src.gallettabot.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String prova = "{'cacca': 'rosa'}";
        Gson gson = new Gson();
        Map<String, String> myMap = gson.fromJson(prova, Map.class);

        System.out.println(myMap.get("cacca"));

        try {
            TelegramBotsApi telegram = new TelegramBotsApi(DefaultBotSession.class);
            telegram.registerBot(new GallettaBot());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
