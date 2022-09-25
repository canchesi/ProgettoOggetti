package src.main.java.menus;

import org.bson.Document;
import src.main.java.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public final class MainMenu extends Menu{

    public MainMenu(DatabaseClient client) {
        super(client);
    }

    @Override
    public MainMenu generateButtons() {
        super.setTextToPrint("Seleziona la funzionalità");
        try{
            Document document = super.getClient().getMongo().getDatabase("gallettabot").getCollection("menus").find(new Document("name", "functionalities")).first();
            if (document != null) {
                ArrayList<Map<String, String>> functs = (ArrayList<Map<String, String>>) document.get("functs");
                for (Map<String, String> func: functs)
                    if (func.containsKey("link"))
                        this.getButtons().add(new ArrayList<>(List.of(new Button(func.get("title"), new URL(func.get("link"))))));
                    else
                        this.getButtons().add(new ArrayList<>(List.of(new Button(func.get("title"), func.get("callback")))));
            }
        } catch (NullPointerException | MalformedURLException npe) {
            return null;
        }
        return this;
    }

    @Override
    public List<List<Button>> getButtons() {
        return super.getButtons();
    }

}
