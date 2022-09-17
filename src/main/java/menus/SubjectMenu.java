package src.main.java.menus;

import org.bson.Document;
import src.main.java.Button;
import src.main.java.DatabaseClient;

import java.util.*;

public class SubjectMenu extends Menu {

    public SubjectMenu(DatabaseClient client) {
        super(client);
    }

    @Override
    public Menu generateButtons() {
        this.setTextToPrint("Seleziona la materia di interesse");
        try {
            Document document = super.getClient().getMongo().getDatabase("gallettabot").getCollection("menus").find(new Document("name", "subjects")).first();
            Map<String, Map<String, String>> subjects;
            if (document != null) {
                subjects = (Map<String, Map<String, String>>) document.get("subjects");
                Set<String> keys = subjects.keySet();
                for (Object key: keys)
                    if (Boolean.parseBoolean(subjects.get(key).get("visibility")))
                        this.getAllButtons().add(new ArrayList<>(List.of(new Button(subjects.get(key).get("title"), "subj="+key))));
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

}
