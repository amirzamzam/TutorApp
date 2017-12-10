package com.example.amir.tutorfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private EditText mEmail;
    private EditText mPassword;

    private Button mLoginbtn;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.edEmail);
        mPassword = (EditText) findViewById(R.id.edPassword);

        mLoginbtn = (Button) findViewById(R.id.LoginBtn);

        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {



            if(firebaseAuth.getCurrentUser() != null){
                startActivity(new Intent(MainActivity.this,SearchPage.class));

            }

            }
        };

        mLoginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startSignIn();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    private void startSignIn() {

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(MainActivity.this, "Please fill the empty fields", Toast.LENGTH_LONG).show();


        } else {


            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Error signing in", Toast.LENGTH_LONG).show();
                }

                }
            });
        }
    }



    public void toRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class );
        TextView textView = (TextView) findViewById(R.id.textView2);
        startActivity(intent);

    }


    public void toRegisterTutor(View view){
        Intent intentTutor = new Intent(this, RegisterTutor.class );
        TextView textView = (TextView) findViewById(R.id.textView3);
        startActivity(intentTutor);

    }




}
