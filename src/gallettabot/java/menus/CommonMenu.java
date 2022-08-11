package src.gallettabot.java.menus;

import org.bson.Document;
import src.gallettabot.java.Button;
import src.gallettabot.java.DatabaseClient;

import java.util.*;

public class CommonMenu extends Menu {

    private final String subject;

    public CommonMenu(DatabaseClient client, String subject) {
        super(client);
        this.subject = subject;
    }

    @Override
    public Menu generateButtons() {
        this.setTextToPrint("Seleziona la funzionalità");
        try {
            Document document = super.getClient().getMongo().getDatabase("gallettabot").getCollection("menus").find(new Document("name", "commonMenu")).first();
            if (document != null) {
                Map<String, String>func = (Map<String, String>) document.get("functionalities");
                Set<String> keys = func.keySet();
                Collection<String> values = func.values();
                ArrayList<Iterator<String>> iterators = new ArrayList<>(Arrays.asList(keys.iterator(), values.iterator()));
                while (iterators.get(0).hasNext())
                    this.getAllButtons().add(new ArrayList<>(List.of(new Button(iterators.get(1).next(), "name=" + iterators.get(0).next() + ",subj=" + this.subject))));
            }
            this.getAllButtons().add(new ArrayList<>(List.of(new Button("⬆️️️ Torna all'inizio", "/restart"))));
            } catch(Exception e){
                e.printStackTrace();
            }
        return this;
    }

    @Override
    public DatabaseClient getClient() {
        return super.getClient();
    }

    @Override
    public List<List<Button>> getAllButtons() {
        return super.getAllButtons();
    }

    public String getSubject() {
        return this.subject;
    }
}
