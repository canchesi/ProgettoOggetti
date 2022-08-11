package src.gallettabot.java.menus;

import src.gallettabot.java.Button;
import src.gallettabot.java.DatabaseClient;

import java.util.ArrayList;
import java.util.List;

public class FAQAnswerMenu extends Menu{

    private final String question;

    public FAQAnswerMenu(DatabaseClient client, String question) {
        super(client);
        this.question = question;
    }

    @Override
    public Menu generateButtons() {
        this.setTextToPrint(this.question.split("=")[1]);
        this.getAllButtons().add(new ArrayList<>(List.of(new Button("⬆️ Torna all'inizio", "/restart"))));
        return this;
    }
}
