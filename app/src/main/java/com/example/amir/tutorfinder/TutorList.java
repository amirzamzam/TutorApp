package com.example.amir.tutorfinder;

import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TutorList extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ListView mUserList;

    private ArrayList<String> mTutorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tutor");
        mUserList = (ListView) findViewById(R.id.ListView1);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , mTutorList);

        mUserList.setAdapter(arrayAdapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map<String,String> map = (Map)dataSnapshot.getValue();
                String Name = map.get("FullName");
                String Subject = map.get("Subject");
                String Tutorfee = map.get("TutorFee");
                String address = map.get("AddressLine1");

                Log.v("E_VALUE", "Name : " + Name);
                Log.v("E_VALUE", "Subject : " + Subject);
                Log.v("E_VALUE", "Tutor Fee : " + Tutorfee);
                Log.v("E_VALUE", "Address : " + address);


                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }
}
