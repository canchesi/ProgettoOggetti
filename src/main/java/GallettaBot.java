package src.main.java;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import src.main.java.exceptions.UnexpectedRequestException;

/*
    Bisogna estendere la classe TelegramLongPollingBot per poter creare e gestire il backend del bot.
    È obbligatorio dover fare l'override di tre metodi: getBotUsername(), getBotToken() e onUpdateReceived().
 */
public final class GallettaBot extends TelegramLongPollingBot{

    // L'oggetto config contiene le informazioni di base tramite lettura del file config.xml.
    private final Config config;
    /* L'oggetto client contiene i metodi per la connessione al database e ritorna la connessione stessa ottenuta
       tramite le API ufficiali di Mongo per Java. */
    private final DatabaseClient client;

    public GallettaBot(){
        super();
        this.config = new Config("config.xml");
        this.client = new DatabaseClient(this.config);
    }

    @Override
    public String getBotUsername() {
        return "GallettaBot";
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        /* La classe MessageHandler cattura i messaggi che vengono mandati al bot dal client di Telegram
           e si occupa del ritorno del corretto messaggio. */
        MessageHandler handler = new MessageHandler(client);
        /* SendMessage e DeleteMessage sono classi fornite dall'API di Telegram utilizzate per inviare o eliminare
           messaggi della chat. */
        SendMessage message = null;
        DeleteMessage toBeDeleted;
        /* Un oggetto Chat è atto al salvataggio di informazioni utili, come il chatId, e offre metodi per la gestione
           della chat stessa. */
        Chat thisChat;
        // ID dell'ultimo messaggio inviato dal bot, utile per la successiva eliminazione.
        int lastMessageId;

        // Il metodo handleRequest cattura e gestisce il messaggio inviato dall'utente.
        try {
            message = handler.handleRequest(handler.checkAndGetMessage(update));
        } catch (UnexpectedRequestException e) {
            // In caso di richiesta sconosciuta il bot resetta la chat.
            message = handler.handleRequest(handler.checkAndGetMessage("/start"));
        } finally { // In ogni caso...
            assert message != null; // Per togliere il warning...
            /* Viene istanziata la chat con il chatId per rispondere alla corretta chat e, se assente una corrispondenza
               nel database viene creato il relativo documento. */
            thisChat = new Chat(client, message.getChatId());
            try{
                /* Il metodo getLastMessageId() cerca nel corretto documento del database l'id dell'ultimo messaggio
                   inviato dal bot. */
                lastMessageId = thisChat.getLastMessageId();
            } catch (NullPointerException ignored) {
                // Se inesistente (corrisponde ad un'incoerenza), elimina il documento
                thisChat.deleteChat();
                lastMessageId = -1;
            }

            // Se la richiesta gestita è un messaggio scritto tramite tastiera del client...
            if (handler.isBotMessageDeletable()) {
                // ... questo viene eliminato per pulizia.
                toBeDeleted = new DeleteMessage(thisChat.getChatId(), handler.getLastBotSentMessageId());
                try {
                    execute(toBeDeleted);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                    // In caso di errore, viene eliminato il documento relativo alla chat incriminata.
                    thisChat.deleteChat();
                }
            }

            // Se esiste un ultimo messaggio inviato dal bot...
            if (lastMessageId != -1) {
                // ... elimina quest'ultimo per pulizia e lasciar spazio al messaggio successivo.
                try{
                    toBeDeleted = new DeleteMessage(thisChat.getChatId(), lastMessageId);
                    execute(toBeDeleted);
                    // Se la richiesta è lo stop del bot, viene eliminato il relativo documento.
                    if (handler.isChatStoppable()) {
                        thisChat.deleteChat();
                    }
                } catch (TelegramApiException e) {
                    // Lo stesso in caso di errore.
                    thisChat.deleteChat();
                }
            } else { // Se il messaggio non esiste...
                // ... crea il relativo documento.
                thisChat.createDocument(thisChat.getChatId());
            }

            // Se non è una richiesta di stop del bot...
            if(!handler.isChatStoppable())
                // ... viene inserito nel database, o sostituito, il messageId dell'ultimo messaggio del bot.
                try {
                    thisChat.setLastMessageIdInDocument(execute(message).getMessageId());
                } catch (TelegramApiException e) {
                    // In caso di errore, come sempre, si elimina il relativo documento.
                    thisChat.deleteChat();
                }
        }
    }

}
