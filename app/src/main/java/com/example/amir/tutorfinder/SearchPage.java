package com.example.amir.tutorfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SearchPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.search_page_toolbar);
        getSupportActionBar().setTitle("Search Page");

        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toTutorList = new Intent(SearchPage.this, TutorList.class);
                startActivity(toTutorList);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null){

            sendToStart();
        }

    }

    private void sendToStart() {
        Intent mainPage = new Intent(SearchPage.this, MainActivity.class);
        startActivity(mainPage);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()== R.id.main_logout_button){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }


        return true;
    }




}
