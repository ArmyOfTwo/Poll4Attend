package com.armyof2.poll4bunk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LaunchActivity extends AppCompatActivity {

    private EditText pollName;
    private Intent intent;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        pollName = (EditText) findViewById(R.id.et_pollname);
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void onJoinButtonClicked(View view) {
        String poll;
        poll = pollName.getText().toString();
        if(poll.equals(""))
            Toast.makeText(this, "Poll Server name cannot be empty", Toast.LENGTH_SHORT).show();
        //compare poll with server poll
    }

    public void onCreateButtonClicked(View view) {
        intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
}
