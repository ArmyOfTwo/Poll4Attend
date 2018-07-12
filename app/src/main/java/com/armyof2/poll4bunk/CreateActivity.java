package com.armyof2.poll4bunk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CreateActivity extends AppCompatActivity{

    private EditText bunkName;
    private EditText bunkDate0;
    private EditText bunkDate1;
    private EditText bunkDate2;
    private EditText bunkNum;
    private Intent intent;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        bunkName = (EditText) findViewById(R.id.et_pollname);
        bunkDate0 = (EditText) findViewById(R.id.et_date0);
        bunkDate1 = (EditText) findViewById(R.id.et_date1);
        bunkDate2 = (EditText) findViewById(R.id.et_date2);
        bunkNum = (EditText) findViewById(R.id.et_numofparti);
        intent = new Intent(this, LaunchActivity.class);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
    }

    public void onCreateButtonClicked(View view) {
        String bname, bdate;
        int bnum;
        bname = bunkName.getText().toString();
        bdate = (bunkDate0.getText().toString() + bunkDate1.getText().toString() + bunkDate2.getText().toString());
        bnum = Integer.parseInt(bunkNum.getText().toString());
        makeFile();
        myRef.setValue("Hello, World!");

        startActivity(intent);
    }

    public void makeFile() {

    }
}
