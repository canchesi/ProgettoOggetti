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
        if (super.getUpperMenu().isEmpty()) {
            byte i = 0;
            try{
                Document subj = super.getClient().getMongo().getDatabase("gallettabot").getCollection("menu").find(new Document("name", "subjects")).first();
                for (String sub : (List<String>) subj.get("subjects"))
                    this.getAllButtons().add(new ArrayList<>(List.of(new Button(sub, String.valueOf(i++)))));

                this.getAllButtons().add(new ArrayList<>(List.of(new Button("Torna all'inizio", "/restart"))));
            } catch (NullPointerException npe) {
                return null;
            }
        }
        return this;
    }

    @Override
    public List<List<Button>> getAllButtons() {
        return super.getAllButtons();
    }

}
/*
*

*
* */
