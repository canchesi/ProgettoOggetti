package src.gallettabot.java.menus;

import org.bson.Document;
import src.gallettabot.java.Button;
import src.gallettabot.java.DatabaseClient;

import java.util.*;

public class FAQMenu extends CommonMenu {

    public FAQMenu(DatabaseClient client, String subject) {
        super(client, subject);
    }

    @Override
    public Menu generateButtons() {
        this.setTextToPrint("Seleziona una domanda");
        try{
            Map<String, Object> faq = (Map<String, Object>) super.getClient().getMongo().getDatabase("gallettabot").getCollection("menus").find((new Document("name", "faq")).append("subj", Byte.parseByte(this.getSubject()))).first().get("questions");
            for (Object current: faq.values()) {
                Iterator<Object> currentIterators = ((Document) current).values().iterator();
                while (currentIterators.hasNext())
                    this.getAllButtons().add(new ArrayList<>(List.of(new Button(currentIterators.next().toString(), "ans="+currentIterators.next().toString()))));
            }
            this.getAllButtons().add(new ArrayList<>(List.of(new Button("⬆️ Torna all'inizio", "/restart"))));
        } catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }
}
