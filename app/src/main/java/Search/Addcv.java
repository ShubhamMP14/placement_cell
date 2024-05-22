package Search;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_patt.MainActivity;
import com.example.project_patt.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class Addcv extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_CODE = 1;

    private EditText editTextCVTitle;
    private EditText editTextCVDescription;
    private Button buttonUploadCV;
    private Button buttonSubmitCV;
    private Uri selectedFileUri;

    // Firebase
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcv);

        // Initialize Firebase Storage
        storageReference = FirebaseStorage.getInstance().getReference();

        editTextCVTitle = findViewById(R.id.editTextCVTitle);
        editTextCVDescription = findViewById(R.id.editTextCVDescription);
        buttonUploadCV = findViewById(R.id.buttonUploadCV);
        buttonSubmitCV = findViewById(R.id.buttonSubmitCV);

        buttonUploadCV.setOnClickListener(view -> openFileChooser());
        buttonSubmitCV.setOnClickListener(view -> submitCV());
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Set MIME type to select any file type
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }

    private void submitCV() {
        // Check if a file is selected
        if (selectedFileUri != null) {
            // Get a reference to the file in Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

            // Create a reference to "cv" folder where CVs will be stored
            StorageReference cvRef = storageRef.child("cv/" + UUID.randomUUID().toString());

            // Upload the file to Firebase Storage
            cvRef.putFile(selectedFileUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // CV uploaded successfully
                        Toast.makeText(Addcv.this, "CV uploaded successfully", Toast.LENGTH_SHORT).show();

                        // Navigate to the main activity
                        Intent mainIntent = new Intent(Addcv.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish(); // Finish the current activity
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(Addcv.this, "CV upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(Addcv.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedFileUri = data.getData();
            if (selectedFileUri != null) {
                // Handle the selected file URI as needed
                // You can upload the file to Firebase Storage or perform any other operation
                Toast.makeText(Addcv.this, "File selected: " + selectedFileUri.toString(), Toast.LENGTH_SHORT).show();
            } else {
                // Handle the case where the selectedFileUri is null
                Toast.makeText(Addcv.this, "Failed to retrieve file URI", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
