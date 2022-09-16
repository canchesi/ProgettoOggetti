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
            ArrayList<Map<String, Object>> subjects;
            if (document != null) {
                subjects = (ArrayList<Map<String, Object>>) document.get("subjects");
                for (byte i = 0; i < subjects.size(); ++i)
                    if ((Boolean) subjects.get(i).get("visibility"))
                        this.getAllButtons().add(new ArrayList<>(List.of(new Button((String) subjects.get(i).get("title"), "subj="+String.valueOf(i)))));
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
