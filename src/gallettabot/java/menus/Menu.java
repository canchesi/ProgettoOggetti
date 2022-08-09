package src.gallettabot.java.menus;

import src.gallettabot.java.Button;
import src.gallettabot.java.DatabaseClient;

import java.util.ArrayList;
import java.util.List;
public abstract class Menu {

    private List<List<Button>> allButtons;
    private DatabaseClient client;

    public Menu(DatabaseClient client){
        this.allButtons = new ArrayList<>();
        this.client = client;
    }

    public List<List<Button>> getAllButtons() {
        return allButtons;
    }
}
