package src.gallettabot.java;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.net.URL;

public class Button extends InlineKeyboardButton {

    public Button(String title, String callback){
        super();
        super.setText(title);
        super.setCallbackData(callback);
    }

    public Button(String title, URL link) {
        super();
        super.setText(title);
        super.setUrl(link.toString());
    }

}
