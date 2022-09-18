package src.main.java.menus;

import org.bson.Document;
import src.main.java.Button;
import src.main.java.DatabaseClient;

import java.net.URL;
import java.util.*;

import static src.main.java.Utilities.isLink;

public class FAQMenu extends SubjectMenu {

    private final String subject;

    public FAQMenu(DatabaseClient client, String subject) {
        super(client);
        this.subject = subject;
    }

    @Override
    public Menu generateButtons() {
        this.setTextToPrint("Seleziona una domanda");
        try{
            Object faq = super.getClient().getMongo().getDatabase("gallettabot").getCollection("menus").find(new Document("subj", this.getSubject())).first();
            if (faq != null) {
                faq = ((Map<String, String>) faq).get("questions");
                byte i = 0;
                for (Object current : (ArrayList) faq) {
                    Iterator<Object> currentIterators = ((Document) current).values().iterator();
                    while (currentIterators.hasNext()) {
                        String question = currentIterators.next().toString();
                        String answer = currentIterators.next().toString();
                        if (isLink(answer))
                            this.getAllButtons().add(new ArrayList<>(List.of(new Button(question, new URL(answer)))));
                        else {
                            this.getAllButtons().add(new ArrayList<>(List.of(new Button(question, "quest:subj="+this.getSubject()+",q="+i++))));
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

    public String getSubject() {
        return subject;
    }

}
