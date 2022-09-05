package src.gallettabot.java.menus;

import org.bson.Document;
import src.gallettabot.java.Button;
import src.gallettabot.java.DatabaseClient;

import java.net.URL;
import java.util.*;

import static src.gallettabot.java.Utilities.isLink;

public class FAQMenu extends SubjectMenu {

    private final byte subject;

    public FAQMenu(DatabaseClient client, byte subject) {
        super(client);
        this.subject = subject;
    }

    @Override
    public Menu generateButtons() {
        this.setTextToPrint("Seleziona una domanda");
        try{
            Object faq = super.getClient().getMongo().getDatabase("gallettabot").getCollection("menus").find((new Document("name", "faq")).append("subj", this.getSubject())).first();
            if (faq != null) {
                faq = ((Map<String, String>) faq).get("questions");
                System.out.println(faq);
                byte i = 0;
                for (Object current : (ArrayList) faq) {
                    Iterator<Object> currentIterators = ((Document) current).values().iterator();
                    while (currentIterators.hasNext()) {
                        String question = currentIterators.next().toString();
                        String answer = currentIterators.next().toString();
                        if (isLink(answer))
                            this.getAllButtons().add(new ArrayList<>(List.of(new Button(question, new URL(answer)))));
                        else {
                            this.getAllButtons().add(new ArrayList<>(List.of(new Button(question, "quest:name=faq,subj="+this.getSubject()+",q="+i++))));
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

    public byte getSubject() {
        return subject;
    }

}
