package src.main.java.menus;

import org.bson.Document;
import src.main.java.GoBack;
import src.main.java.Button;
import src.main.java.DatabaseClient;

import java.net.URL;
import java.util.*;

import static src.main.java.Utilities.isLink;

public class FAQMenu extends Menu implements GoBack {

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
                faq = ((Map<String, Object>) faq).get("questions");
                byte i = 0;
                for (Object current : ((Map<String, Object>) faq).values()) {
                    String question = (String) ((Document) current).get("q");
                    String answer = (String) ((Document) current).get("a");
                    if (isLink(answer))
                        this.getButtons().add(new ArrayList<>(List.of(new Button(question, new URL(answer)))));
                    else {
                        this.getButtons().add(new ArrayList<>(List.of(new Button(question, "quest:subj="+this.getSubject()+",q="+i++))));
                    }
                }
            }
            this.getButtons().add(new ArrayList<>(List.of(new Button("\uD83C\uDFE0️ Home", "/restart"), this.generateBackButton("faq"))));
        } catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public Button generateBackButton(Object request) {
        return new Button(GoBack.upArrow+"️ Indietro", (String) request);
    }
}
