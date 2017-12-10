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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterTutor extends AppCompatActivity {

    private EditText rtUsername;
    private EditText rtEmail;
    private EditText rtFullName;
    private EditText rtPassword;
    private EditText rtTutorFee;
    private EditText rtAddress;
    private EditText rtSubject;
    private DatabaseReference rtDatabase;

    private Button rtRegBtn;
    private ProgressDialog rProgress;
    private android.support.v7.widget.Toolbar rRegToolbar;

    //Firebase Auth
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor);

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        rtDatabase = FirebaseDatabase.getInstance().getReference().child("Tutor");

        //Progress dialog
        rProgress = new ProgressDialog(this);

        //Registration Field

        rtUsername = (EditText) findViewById(R.id.tUserName);
        rtEmail = (EditText) findViewById(R.id.tEmail);
        rtFullName = (EditText) findViewById(R.id.tFullName);
        rtPassword = (EditText) findViewById(R.id.tPassword);
        rtTutorFee = (EditText) findViewById(R.id.tFee);
        rtAddress =(EditText) findViewById(R.id.tAddress);
        rtSubject = (EditText) findViewById(R.id.tSubject);

        rRegToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.registerTutor_page_toolbar);
        getSupportActionBar().setTitle("Register Tutor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rtRegBtn = (Button) findViewById(R.id.regButton);
        rtRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        final String username = rtUsername.getText().toString().trim();
        final String email = rtEmail.getText().toString().trim();
        final String FullName = rtFullName.getText().toString().trim();
        final String password = rtPassword.getText().toString().trim();
        final String TutorFee = rtTutorFee.getText().toString().trim();
        final String Address1 = rtAddress.getText().toString().trim();
        final String Subject = rtSubject.getText().toString().trim();

        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(TutorFee)  && !TextUtils.isEmpty(Address1) && !TextUtils.isEmpty(Subject)){

            rProgress.setMessage("Signing Up ..");
            rProgress.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = rtDatabase.child(user_id);
                        current_user_db.child("name").setValue(username);
                        current_user_db.child("FullName").setValue(FullName);
                        current_user_db.child("TutorFee").setValue(TutorFee);
                        current_user_db.child("Address").setValue(Address1);
                        current_user_db.child("Subject").setValue(Subject);
                        current_user_db.child("image").setValue("default");


                        Toast.makeText(RegisterTutor.this, "Register Successful", Toast.LENGTH_SHORT).show();

                        rProgress.dismiss();

                        FirebaseAuth.getInstance().signOut();

                        Intent bookpage = new Intent(RegisterTutor.this,MainActivity.class);
                        startActivity(bookpage);
                        finish();

                    }
                    else{
                        Toast.makeText(RegisterTutor.this, "Register Unsuccesful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        rProgress.dismiss();
                        FirebaseAuth.getInstance().signOut();

                    }

                }
            });
        }
    }

}
