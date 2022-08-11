package src.gallettabot.java.menus;

import src.gallettabot.java.Button;
import src.gallettabot.java.DatabaseClient;

import java.util.List;

public class FAQMenu extends CommonMenu{


    public FAQMenu(DatabaseClient client, String subject) {
        super(client, subject);
    }

    @Override
    public Menu generateButtons() {
        return super.generateButtons();
    }

    @Override
    public List<List<Button>> getAllButtons() {
        return super.getAllButtons();
    }

    @Override
    public DatabaseClient getClient() {
        return super.getClient();
    }

    @Override
    public String getTextToPrint() {
        return super.getTextToPrint();
    }

    @Override
    public void setTextToPrint(String textToPrint) {
        super.setTextToPrint(textToPrint);
    }
}
