package src.gallettabot.java.menus;

import org.bson.Document;
import src.gallettabot.java.*;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends Menu{

    public MainMenu(DatabaseClient client) {
        super(client);
    }

    @Override
    public MainMenu generateButtons() {
        byte i = 0;
        super.setTextToPrint("Seleziona la materia di interesse");
        try{

            Document subj = super.getClient().getMongo().getDatabase("gallettabot").getCollection("menu").find(new Document("name", "subjects")).first();
            for (String sub : (List<String>) subj.get("subjects"))
                this.getAllButtons().add(new ArrayList<>(List.of(new Button(sub, String.valueOf(i++)))));

        } catch (NullPointerException npe) {
            return null;
        }
        return this;
    }

    @Override
    public List<List<Button>> getAllButtons() {
        return super.getAllButtons();
    }

}
