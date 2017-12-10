package com.example.amir.tutorfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ViewBookings extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbarBooking;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        toolbarBooking = (android.support.v7.widget.Toolbar) findViewById(R.id.booking_page_toolbar);
        getSupportActionBar().setTitle("Booking Page");
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
        Intent mainPage = new Intent(ViewBookings.this, MainActivity.class);
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



}
