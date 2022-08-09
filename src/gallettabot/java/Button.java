package src.gallettabot.java;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class Button extends InlineKeyboardButton {

    public Button(String title){
        super();
        this.setText(title);
        this.setCallbackData("j");
    }

}
