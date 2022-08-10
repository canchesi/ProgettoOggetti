package src.gallettabot.java;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class Button extends InlineKeyboardButton {

    public Button(String title, String callback){
        super();
        super.setText(title);
        super.setCallbackData(callback);
    }

}
