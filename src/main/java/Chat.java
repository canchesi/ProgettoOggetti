package src.main.java;

import org.bson.Document;

public final class Chat {

    private final DatabaseClient client;
    private final String chatId;

    public Chat(DatabaseClient client, String chatId) {
        this.client = client;
        this.chatId = chatId;
    }

    public int getLastMessageId() {
        /* L'oggetto mid (message id), rappresentante l'ultimo messaggio mandato dal bot, viene preso dal database,
         * se esisttente, altrimenti viene ritornato -1
         */
        Document mid = this.client.getMongo().getDatabase("gallettabot").getCollection("chats").find((new Document("chatId", this.chatId))).first();
        if (mid != null) {
            return (int) mid.get("messageId");
        }
        return -1;
    }

    public String getChatId() {
        return chatId;
    }

    public void createDocument(String chatId) {
        // Crea un documento nel database, nella collezione chats, e inserisce il chatId della chat corrente
        client.getMongo().getDatabase("gallettabot").getCollection("chats").insertOne(new Document("chatId", chatId));
    }

    public void setLastMessageIdInDocument(int messageId) {
        // Aggiorna l'ultimo messaggio inviato dal bot
        Document update = new Document("$set", new Document("messageId", messageId));
        Document whichDocument = new Document("chatId", this.chatId);
        client.getMongo().getDatabase("gallettabot").getCollection("chats").updateOne(whichDocument, update);
    }

    public void deleteChat() {
        client.getMongo().getDatabase("gallettabot").getCollection("chats").deleteOne(new Document("chatId", this.chatId));
    }

}
