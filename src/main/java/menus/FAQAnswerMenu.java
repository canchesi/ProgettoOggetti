package src.main.java.menus;

import org.bson.Document;
import src.main.java.Backable;
import src.main.java.Button;
import src.main.java.DatabaseClient;

import java.util.*;

public final class FAQAnswerMenu extends Menu implements Backable {

    private final String question;

    public FAQAnswerMenu(DatabaseClient client, String question) {
        super(client);
        this.question = question;
    }

    @Override
    public Menu generateButtons(){
        List<List<String>> parsedQuestion = parseAnswer(question);
        Object faq = this.getClient().getMongo().getDatabase("gallettabot").getCollection("menus")
                .find(new Document(parsedQuestion.get(0).get(0), parsedQuestion.get(0).get(1))).first();
        if (faq != null) {
            faq = ((Document) faq).get("questions");
            Map<String, String> toPrint =  ((Map<String, Map<String, String>>) faq).get(parsedQuestion.get(1).get(1));
            this.setTextToPrint("Domanda:\n" + toPrint.get("q") + "\n\nRisposta:\n" + toPrint.get("a"));
        }
        this.getButtons().add(new ArrayList<>(List.of(new Button("\uD83C\uDFE0 Home", "/restart"), this.generateBackButton(parsedQuestion))));
        return this;
    }

    private static List<List<String>> parseAnswer(String question) {
        List<List<String>> parse = new ArrayList<>();
        String[] tmp;

        question = question.split(":")[1];
        for (String firstParse: question.split(",")) {
            tmp = firstParse.split("=");
            parse.add(new ArrayList<>(Arrays.asList(tmp[0], tmp[1])));
        }
        return parse;
    }

    @Override
    public Button generateBackButton(Object request) {
        return new Button(Backable.upArrow+" Indietro", "subj="+((List<List<String>>) request).get(0).get(1));
    }
}
