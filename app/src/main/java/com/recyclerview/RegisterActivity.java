package com.recyclerview;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.recyclerview.Common.Model;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private List<AuthUI.IdpConfig> idpConfigs;
    public static Model currentUser;
    FirebaseUser user;



    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onStart() {
        firebaseAuth.addAuthStateListener(listener);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result !=null && result.getResultCode() == RESULT_OK)
                {
                    user =  FirebaseAuth.getInstance().getCurrentUser();
                }else {
                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        LogInConfig();
    }

    private void LogInConfig() {

        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("user");

        idpConfigs = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());



        firebaseAuth = FirebaseAuth.getInstance();
        listener = firebaseAuth1 -> {
            FirebaseUser user = firebaseAuth1.getCurrentUser();
            if (user !=null){
                myFirebaseUser();

            }else {
                LogInLayout();

            }
        };
    }

    private void LogInLayout() {

        AuthMethodPickerLayout authMethodPickerLayout = new AuthMethodPickerLayout
                .Builder(R.layout.sign_in_layout)
                .setPhoneButtonId(R.id.phoneBtn)
                .setGoogleButtonId(R.id.gmailBtn)
                .build();

        activityResultLauncher.launch(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAuthMethodPickerLayout(authMethodPickerLayout)
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(idpConfigs)
                .build());






    }

    private void myFirebaseUser() {

        reference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Model model = snapshot.getValue(Model.class);
                            GoMainActivity(model);
                        }else {
                            showRegisterLayout();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private void showRegisterLayout() {

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.register_layout))
                .create();




        View dialogView  = dialog.getHolderView();

        EditText name = dialogView.findViewById(R.id.name);
        EditText email = dialogView.findViewById(R.id.email);
        EditText phone = dialogView.findViewById(R.id.phone);

        Button submit = dialogView.findViewById(R.id.submit);



        if (Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()!=null &&
        !TextUtils.isEmpty(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()))
            phone.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        if (FirebaseAuth.getInstance().getCurrentUser().getEmail() !=null &&
                !TextUtils.isEmpty(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
            email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        dialog.show();


        submit.setOnClickListener(view -> {
            if(TextUtils.isEmpty(name.getText().toString())){
                Toast.makeText(RegisterActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
            }else  if(TextUtils.isEmpty(email.getText().toString())){
                Toast.makeText(RegisterActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
            }else  if(TextUtils.isEmpty(phone.getText().toString())){
                Toast.makeText(RegisterActivity.this, "Please enter phone", Toast.LENGTH_SHORT).show();
            }else {
                Model model = new Model();
                model.setName(name.getText().toString());
                model.setName(email.getText().toString());
                model.setName(phone.getText().toString());

                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(model)
                        .addOnSuccessListener(unused -> {
                            dialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();

                        })
                        .addOnFailureListener(e -> {
                            dialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Register field", Toast.LENGTH_SHORT).show();

                        });
            }

        });








    }

    private void GoMainActivity(Model model) {

        currentUser = model;
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();

    }
}