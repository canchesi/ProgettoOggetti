package src.main.java.menus;

import src.main.java.Button;
import src.main.java.DatabaseClient;
import java.util.ArrayList;
import java.util.List;


public abstract class Menu {

    private final List<List<Button>> allButtons;
    private String textToPrint;
    private final DatabaseClient client;

    public Menu(DatabaseClient client){
        this.client = client;
        this.allButtons = new ArrayList<>();
        this.textToPrint = null;
    }

    public Menu generateButtons() {
        return this;
    }

    public List<List<Button>> getButtons() {
        return allButtons;
    }

    public DatabaseClient getClient() {
        return client;
    }

    public String getTextToPrint() {
        return textToPrint;
    }

    public void setTextToPrint(String textToPrint) {
        this.textToPrint = textToPrint;
    }
}
