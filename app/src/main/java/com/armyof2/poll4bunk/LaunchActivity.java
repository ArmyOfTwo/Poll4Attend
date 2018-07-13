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


public class LaunchActivity extends AppCompatActivity {

    public static String SERVER_ID;
    private EditText pollName;
    private Intent intent;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String serverName, strOut, strFinal[];
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        pollName = (EditText) findViewById(R.id.et_pollname);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void onJoinButtonClicked(View view) {
        serverName = pollName.getText().toString();
        intent = new Intent(this, MainActivity.class);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("TAG", "Child = " + child.getValue().toString());
                    strOut = child.getValue().toString().substring(12);
                    strFinal = strOut.split(",");
                    Log.d("TAG", "strFinal = " + strFinal[0]);
                    if(strFinal[0].equals(serverName)){
                        goodToast();
                        SERVER_ID = child.getKey();
                        Log.d("TAG", "SERVER_ID = " + SERVER_ID);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void onCreateButtonClicked(View view) {
        intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    public void goodToast(){
        Toast.makeText(this, "Server joined!", Toast.LENGTH_SHORT).show();
    }
}
