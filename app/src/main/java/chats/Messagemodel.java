package chats;
public class Messagemodel {
    private String senderId;
    private String receiverId;
    private String message;
    private String timestamp;

    public Messagemodel() {
        // Required empty constructor for Firestore
    }

    public Messagemodel(String senderId, String receiverId, String message, String timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}