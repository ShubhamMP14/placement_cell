package com.example.project_patt.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_patt.LoginActivity;
import com.example.project_patt.R;
import com.example.project_patt.UpdateProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class admin_profile extends Fragment {

    private TextView textviewWelcome, textviewFullname, textviewEmail, textviewDob, textviewGender, textviewMobile;
    private ProgressBar progressBar;
    private ImageView imageView;
    private FirebaseAuth authProfile;
    private Context mContext;

    public admin_profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin__profile, container, false);
        mContext = getContext(); // Store the context for later use
        setHasOptionsMenu(true); // Enable options menu for fragment

        // Initialize views
        textviewWelcome = view.findViewById(R.id.textView_show_welcome);
        textviewFullname = view.findViewById(R.id.textView_show_full_name);
        textviewEmail = view.findViewById(R.id.textView_show_email);
        textviewDob = view.findViewById(R.id.textView_show_dob);
        textviewGender = view.findViewById(R.id.textView_show_gender);
        textviewMobile = view.findViewById(R.id.textView_show_mobile);
        progressBar = view.findViewById(R.id.progress_bar);
        imageView = view.findViewById(R.id.imageView_profile_dp);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UploadProfilePicActivity.class);
                startActivity(intent);

            }
        });

        // Initialize Firebase authentication
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser != null) {
            checkIfEmailVerified(firebaseUser);
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        } else {
            Toast.makeText(getActivity(), "Something went wrong! User details are not available at the moment", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void checkIfEmailVerified(FirebaseUser firebaseUser) {
        if (!firebaseUser.isEmailVerified()) {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Email is not verified");
        builder.setMessage("Please verify your email now. You cannot login without email verification next time.");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Registered Users").child(firebaseUser.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String fullName = firebaseUser.getDisplayName();
                    String email = firebaseUser.getEmail();
                    String dob = dataSnapshot.child("dob").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String mobile = dataSnapshot.child("mobile").getValue(String.class);

                    textviewWelcome.setText("Welcome, " + fullName);
                    textviewFullname.setText(fullName);
                    textviewEmail.setText(email);
                    textviewDob.setText(dob);
                    textviewGender.setText(gender);
                    textviewMobile.setText(mobile);

                    // Load profile picture using Picasso
                    Uri uri = firebaseUser.getPhotoUrl();
                    Picasso.get().load(uri).into(imageView);
                } else {
                    Toast.makeText(mContext, "User data not found", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Error retrieving user data", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.commom_menu1, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh1) {
            // Reload the current fragment
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
            return true;
        } else if (id == R.id.menu_update_profile1) {
            Intent intent = new Intent(mContext, UpdateProfileActivity.class);
            startActivity(intent);
            return true;
        } else if (id==R.id.menu_update_email1) {
            Intent intent = new Intent(mContext, UpdateEmailActivity.class);
            startActivity(intent);
            return true;
        } else if (id==R.id.menu_delete_profile1) {
            Intent intent=new Intent(mContext,DeleteProfileActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_logout1) {
            authProfile.signOut();
            Toast.makeText(mContext, "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
