package com.armyof2.poll4bunk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LaunchActivity extends AppCompatActivity {

    private EditText pollName;
    private Intent intent;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String serverName, bunkName;

    private boolean flag = false;


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

        if(!flag)
            Toast.makeText(this, "Server does not exist!", Toast.LENGTH_SHORT).show();
    }

    public void onCreateButtonClicked(View view) {
        intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    public void getServerNum(){
        int serverNum = Integer.parseInt(String.valueOf(bunkName.charAt(4)));
        Log.d("TAG", "ServerNum = " + serverNum);
    }
}
