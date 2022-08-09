package src.gallettabot.java;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class Button extends InlineKeyboardButton {

    public Button(String title){
        super();
        InlineKeyboardButton a = new InlineKeyboardButton();
        super.setText(title);

    }

/*
 *     InlineKeyboardButton a = new InlineKeyboardButton();
 *     a.setText("eh si");
 *     a.setUrl("https://www.google.com");
 *     rowInline.add(a);
 *     rowsInline.add(rowInline);
 *     markupInline.setKeyboard(rowsInline);
 *     m.setReplyMarkup(markupInline);
 */
}
