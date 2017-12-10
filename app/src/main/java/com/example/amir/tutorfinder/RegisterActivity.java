package com.example.amir.tutorfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.AppCompatEditText;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText rUsername;
    private EditText rEmail;
    private EditText rPassword;
    private DatabaseReference rDatabase;

    private Button rRegBtn;
    private ProgressDialog rProgress;
    private android.support.v7.widget.Toolbar rRegToolbar;

    //Firebase Auth
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        rDatabase = FirebaseDatabase.getInstance().getReference().child("Student");

        //Progress dialog
        rProgress = new ProgressDialog(this);

        //Registration Field

        rUsername = (EditText) findViewById(R.id.user_name);
        rEmail = (EditText) findViewById(R.id.reg_email);
        rPassword = (EditText) findViewById(R.id.reg_pwd);

        rRegToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.register_page_toolbar);
        getSupportActionBar().setTitle("Register Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rRegBtn = (Button) findViewById(R.id.RegButton);
        rRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onSupportNavigateUp();
        onBackPressed();
        return true;
    }

    @Override
    public boolean onNavigateUp() {
        super.onNavigateUp();
        onBackPressed();
        return true;
    }

    private void startRegister() {

        final String username = rUsername.getText().toString().trim();
        String email = rEmail.getText().toString().trim();
        String password = rPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            rProgress.setMessage("Signing Up ..");
            rProgress.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = rDatabase.child(user_id);
                        current_user_db.child("name").setValue(username);
                        current_user_db.child("image").setValue("default");

                        Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();

                        rProgress.dismiss();

                        Intent searchpage = new Intent(RegisterActivity.this,SearchPage.class);
                        startActivity(searchpage);
                        finish();

                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Register Unsuccesful", Toast.LENGTH_SHORT).show();
                        rProgress.dismiss();

                    }

                }
            });
        }
    }

}


