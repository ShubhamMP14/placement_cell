package com.example.project_patt;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class forgetpwdActivity extends AppCompatActivity {

    private Button btnPwdReset;
    private EditText editTextPwdResetEmail;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;

    private final static String TAG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);

        getSupportActionBar().setTitle("Forgot password");

        editTextPwdResetEmail=findViewById(R.id.editText_password_reset_email);
        btnPwdReset=findViewById(R.id.button_password_reset);
        progressBar=findViewById(R.id.progressBar);

        btnPwdReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=editTextPwdResetEmail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(forgetpwdActivity.this,"Please enter your registered email",Toast.LENGTH_LONG).show();
                    editTextPwdResetEmail.setError("Email is required");
                    editTextPwdResetEmail.requestFocus();

                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(forgetpwdActivity.this,"Please enter valid email",Toast.LENGTH_LONG).show();
                    editTextPwdResetEmail.setError("Valid email is required");
                    editTextPwdResetEmail.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    resetPwd(email);
                }
            }

            private void resetPwd(String email) {
                authProfile=FirebaseAuth.getInstance();
                authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(forgetpwdActivity.this, "Please check your inbox for password reset lnik",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(forgetpwdActivity.this, MainActivity.class); // Use stored context
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else {
                            try {
                                throw task.getException();

                            }catch (FirebaseAuthInvalidUserException e){
                                editTextPwdResetEmail.setError("User does not exists or is no longer valid.Please register again.");
                            }catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(forgetpwdActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}