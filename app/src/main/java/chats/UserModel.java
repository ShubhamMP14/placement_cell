package chats;
public class UserModel {
    private String userId;
    private String userName;
    private String imageUrl;

    public UserModel() {
        // Required empty constructor for Firestore
    }

    public UserModel(String userId, String userName, String imageUrl) {
        this.userId = userId;
        this.userName = userName;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}