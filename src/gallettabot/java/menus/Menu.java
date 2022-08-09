package src.gallettabot.java.menus;

import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import src.gallettabot.java.Button;
import src.gallettabot.java.DatabaseClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Menu {

    private final List<List<Button>> allButtons;
    private String upperMenu;
    private final DatabaseClient client;

    public Menu(DatabaseClient client){
        this.client = client;
        this.allButtons = new ArrayList<>();
        this.upperMenu = "";
    }

    public Menu(DatabaseClient client, String upperMenu){
       this(client);
       this.upperMenu = upperMenu;
    }

    public Menu generateButtons() {

        if (this.upperMenu.isEmpty()) {
            try{
                Document subj = client.getMongo().getDatabase("gallettabot").getCollection("menu").find(new Document("name", "subjects")).first();
                for (String sub : (List<String>) subj.get("subjects"))
                    this.allButtons.add(new ArrayList<>(List.of(new Button(sub))));

            } catch (NullPointerException npe) {
                return null;
            }
        }
        return this;

    }

    public List<List<Button>> getAllButtons() {
        return allButtons;
    }

}
