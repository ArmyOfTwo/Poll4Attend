package com.armyof2.poll4bunk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LaunchActivity extends AppCompatActivity {

    private EditText pollName;
    private Intent intent;

    private FirebaseDatabase database;
    private DatabaseReference myRef0;
    private DatabaseReference myRef1;
    private DatabaseReference myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        pollName = (EditText) findViewById(R.id.et_pollname);

        database = FirebaseDatabase.getInstance();
        myRef0 = database.getReference("bname");
        myRef1 = database.getReference("bdate");
        myRef2 = database.getReference("bnum");
    }

    public void onJoinButtonClicked(View view) {
        String poll;
        poll = pollName.getText().toString();
        if(poll.equals(""))
            Toast.makeText(this, "Poll Server name cannot be empty", Toast.LENGTH_SHORT).show();

        // Read from the database
        myRef0.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        //compare poll with server poll

    }

    public void onCreateButtonClicked(View view) {
        intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
}
