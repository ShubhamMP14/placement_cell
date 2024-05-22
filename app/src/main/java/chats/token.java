package chats;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class token extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Retrieve FCM token here
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(Task<String> task) {
                        if (task.isSuccessful()) {
                            // Get the FCM token
                            String token = task.getResult();

                            // Log and use the FCM token
                            Log.d("FCM Token", token);
                        } else {
                            // Handle error
                            Log.w("FCM Token", "getToken failed", task.getException());
                        }
                    }
                });
    }
}
