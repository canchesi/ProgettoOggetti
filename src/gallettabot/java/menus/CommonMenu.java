package src.gallettabot.java.menus;

import org.bson.Document;
import src.gallettabot.java.Button;
import src.gallettabot.java.DatabaseClient;

import java.net.URL;
import java.util.*;

import static src.gallettabot.java.Utilities.isLink;

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
            String whatToDo = "";
            Map<String, String> appointment;
            if (document != null) {
                Map<String, Object>func = (Map<String, Object>) document.get("functionalities");
                Set<String> keys = func.keySet();
                Collection<Object> values = func.values();
                ArrayList<Iterator<?>> iterators = new ArrayList<>(Arrays.asList(keys.iterator(), values.iterator()));
                while (iterators.get(0).hasNext()) {
                    whatToDo = (String) iterators.get(0).next();
                    if (whatToDo.equals("appointment")) {
                        appointment = (Map<String, String>) iterators.get(1).next();
                        this.getAllButtons().add(new ArrayList<>(List.of(new Button(appointment.get("title"), new URL(appointment.get("link"))))));
                    } else
                        this.getAllButtons().add(new ArrayList<>(List.of(new Button((String) iterators.get(1).next(), "name=" + whatToDo + ",subj=" + this.subject))));
                }
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
