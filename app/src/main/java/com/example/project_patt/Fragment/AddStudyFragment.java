package com.example.project_patt.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_patt.R;
import com.example.project_patt.retrieve.studydisplaymodel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

public class AddStudyFragment extends Fragment {
    private static final int REQUEST_CODE_PICK_FILE = 1001;
    private Uri selectedFileUri;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;

    private EditText studyTypeEditText;
    private EditText courseNameEditText;
    private EditText studyLinkEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_study, container, false);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        databaseRef = FirebaseDatabase.getInstance().getReference("studies");

        studyTypeEditText = rootView.findViewById(R.id.editText_study_type);
        courseNameEditText = rootView.findViewById(R.id.editText_course_name);
        studyLinkEditText = rootView.findViewById(R.id.editText_study_link);


        Button uploadFileButton = rootView.findViewById(R.id.btn_upload_file);
        uploadFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFileUri != null) {
                    uploadFileToFirebase(selectedFileUri);
                } else {
                    showToast("Please pick a file first");
                }

            }
        });

        return rootView;
    }

    private void pickFileFromDrive() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*"); // Allow all file types
        startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                selectedFileUri = data.getData();
                if (selectedFileUri != null) {
                    showToast("File selected: " + selectedFileUri.toString());
                }
            }
        }
    }

    private void uploadFileToFirebase(Uri fileUri) {
        try {
            InputStream inputStream = requireActivity().getContentResolver().openInputStream(fileUri);
            if (inputStream != null) {
                String fileName = getFileName(fileUri);
                String studyType = studyTypeEditText.getText().toString().trim();
                String courseName = courseNameEditText.getText().toString().trim();
                String studyLink = studyLinkEditText.getText().toString().trim();
                studydisplaymodel study = new studydisplaymodel(studyType, courseName, studyLink);

                // Upload study to Firebase under "studies/studyType"
                databaseRef.child(studyType).push().setValue(study);

                StorageReference fileRef = storageRef.child("uploads/" + fileName);
                fileRef.putStream(inputStream)
                        .addOnSuccessListener(taskSnapshot -> {
                            showToast("File uploaded successfully");
                        })
                        .addOnFailureListener(exception -> {
                            showToast("Failed to upload file: " + exception.getMessage());
                            exception.printStackTrace();
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Failed to upload file");
        }
    }

    private String getFileName(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            try (Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        fileName = cursor.getString(displayNameIndex);
                    } else {
                        fileName = uri.getLastPathSegment();
                    }
                }
            }
        }
        if (fileName == null) {
            fileName = uri.getLastPathSegment();
        }
        return fileName;
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}