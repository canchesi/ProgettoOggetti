package src.main.java;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.net.URL;

/*
* La classe Button prevede due semplici costruttori che, in base al secondo parametro,
* generano un oggetto InlineKeyboardButton che restituisca un dato in callback se un semplice bottone, per poi
* eventualmente restituire qualcosa all'utente, o setti un URL raggiungibile tramite la pressione del tasto.
* */

public final class Button extends InlineKeyboardButton {

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
