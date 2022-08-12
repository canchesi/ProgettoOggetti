package src.gallettabot.java.menus;

import org.bson.Document;
import src.gallettabot.java.Button;
import src.gallettabot.java.DatabaseClient;

import java.net.URL;
import java.util.*;

public class FAQMenu extends CommonMenu {

    public FAQMenu(DatabaseClient client, String subject) {
        super(client, subject);
    }

    @Override
    public Menu generateButtons() {
        this.setTextToPrint("Seleziona una domanda");
        try{
            Object faq = super.getClient().getMongo().getDatabase("gallettabot").getCollection("menus").find((new Document("name", "faq")).append("subj", Byte.parseByte(this.getSubject()))).first();
            if (faq != null) {
                faq = ((Map<String, String>) faq).get("questions");
                for (Object current : (ArrayList) faq) {
                    Iterator<Object> currentIterators = ((Document) current).values().iterator();
                    while (currentIterators.hasNext()) {
                        ArrayList<String> text = new ArrayList<>(Arrays.asList(currentIterators.next().toString(), currentIterators.next().toString()));
                        if (text.get(1).startsWith("http://") || text.get(1).startsWith("https://"))
                            this.getAllButtons().add(new ArrayList<>(List.of(new Button(text.get(0), new URL(text.get(1))))));
                        else {
                            this.getAllButtons().add(new ArrayList<>(List.of(new Button(text.get(0), "quest=Domanda:\n" + text.get(0) + "\n\nRisposta:\n" + text.get(1)))));
                        }
                    }
                }
            }
            this.getAllButtons().add(new ArrayList<>(List.of(new Button("⬆️ Torna all'inizio", "/restart"))));
        } catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }
}
