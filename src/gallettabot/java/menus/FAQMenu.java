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
            Map<String, Object> faq = super.getClient().getMongo().getDatabase("gallettabot").getCollection("menus").find((new Document("name", "faq")).append("subj", Byte.parseByte(this.getSubject()))).first();
            if (faq != null) {
                faq = (Map<String, Object>) faq.get("questions");
                for (Object current : faq.values()) {
                    Iterator<Object> currentIterators = ((Document) current).values().iterator();
                    while (currentIterators.hasNext()) {
                        ArrayList<String> text = new ArrayList<>(Arrays.asList(currentIterators.next().toString()));
                        String maybeUrl = currentIterators.next().toString();
                        if (maybeUrl.startsWith("http://") || maybeUrl.startsWith("https://"))
                            this.getAllButtons().add(new ArrayList<>(List.of(new Button(text.get(0), new URL(maybeUrl)))));
                        else {
                            text.add("\n\nRisposta:\n" + maybeUrl);
                            this.getAllButtons().add(new ArrayList<>(List.of(new Button(text.get(0), "quest=Domanda:\n" + text.get(0) + text.get(1)))));
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
