package src.gallettabot.java.menus;

import src.gallettabot.java.Button;
import src.gallettabot.java.DatabaseClient;

import java.util.ArrayList;
import java.util.List;


public abstract class Menu {

    private final List<List<Button>> allButtons;
    private String upperMenu;
    private final DatabaseClient client;

    public Menu(DatabaseClient client){
        this.client = client;
        this.allButtons = new ArrayList<>();
        this.upperMenu = "";
    }

    public Menu(DatabaseClient client, String upperMenu){
       this(client);
       this.upperMenu = upperMenu;
    }

    public Menu generateButtons() {
        return this;
    }

    public List<List<Button>> getAllButtons() {
        return allButtons;
    }

    public String getUpperMenu() {
        return upperMenu;
    }

    public DatabaseClient getClient() {
        return client;
    }

}
