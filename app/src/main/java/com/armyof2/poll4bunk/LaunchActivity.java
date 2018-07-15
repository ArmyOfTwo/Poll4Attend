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

import static com.armyof2.poll4bunk.SignInActivity.userUid;


public class LaunchActivity extends AppCompatActivity {

    public static String SERVER_ID;
    public static String name;
    private EditText pollName;
    private EditText bunkerName;
    private Intent intent;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String serverName, strOut, strFinal[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        pollName = (EditText) findViewById(R.id.et_pollname);
        bunkerName = (EditText) findViewById(R.id.et_bunkername);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void onJoinButtonClicked(View view) {
        serverName = pollName.getText().toString();
        intent = new Intent(this, MainActivity.class);
        name = bunkerName.getText().toString();
        if(bunkerName.getText().toString().equals("")){
            Toast.makeText(this, "You forgot to enter your name!", Toast.LENGTH_SHORT).show();
            return;
        }

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("TAG", "Child = " + child.getValue().toString());
                    // child
                    strOut = child.getValue().toString();
                    String Title="";
                    int pos=strOut.indexOf("Bunk Title");
                    Title= strOut.substring(pos+11).split(",")[0];
                    Log.d("TAG", "Title = " + Title);

                    if(Title.equals(serverName)){
                        goodToast();
                        SERVER_ID = child.getKey();
                        myRef.child(SERVER_ID).child("Bunker's Name").child(userUid).setValue(name);
                        Log.d("TAG", "SERVER_ID = " + SERVER_ID);
                        startActivity(intent);
                        return;
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
