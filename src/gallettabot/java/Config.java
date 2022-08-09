package src.gallettabot.java;

import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.*;

import java.util.*;

public class Config{

    private final String token;
    private final Map<String, String> mongoCredentials;
    public Config(String path){

        XMLReader config = new XMLReader(path);
        mongoCredentials = new HashMap<>();
        NodeList database = ((Element) config.getDoc()
                .getElementsByTagName("config").item(0))
                .getElementsByTagName("database");

        token = ((Element)((Element) config.getDoc().getElementsByTagName("config").item(0))
                    .getElementsByTagName("telegram-api").item(0))
                    .getElementsByTagName("token").item(0)
                    .getTextContent();
        mongoCredentials.put("url", ((Element) database.item(0))
                                    .getElementsByTagName("url").item(0)
                                    .getAttributes()
                                    .getNamedItem("value").getTextContent());
        mongoCredentials.put("username", ((Element) database.item(0))
                                    .getElementsByTagName("username").item(0)
                                    .getAttributes()
                                    .getNamedItem("value").getTextContent());
        mongoCredentials.put("password", ((Element) database.item(0))
                                    .getElementsByTagName("password").item(0)
                                    .getAttributes()
                                    .getNamedItem("value").getTextContent());

    }

    public Map<String, String> getMongoCredentials(){
        return mongoCredentials;
    }

    public String getToken() {

        return token;
    }
}
