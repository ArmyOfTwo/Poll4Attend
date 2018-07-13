package com.armyof2.poll4bunk;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.armyof2.poll4bunk.SignInActivity.userUid;

public class CreateActivity2 extends AppCompatActivity {

    private EditText bunkName;
    private EditText bunkDate0;
    private EditText bunkDate1;
    private EditText bunkDate2;
    private EditText bunkNum;
    private Intent intent;
    private FirebaseDatabase database;
    public static DatabaseReference myRef;
    private HashMap<String, String> dataMap;
    private DialogInterface.OnClickListener dialogClickListener;
    private boolean serverExists = false;

    private class BunkServer {
        String name;
        String date;
        String num;
    }
    BunkServer bunk;

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
        bunk = new BunkServer();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(userUid))
                    serverExists = true;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        //Create yes / no dialog box
        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        myRef.child(userUid).setValue(dataMap);
                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        startActivity(intent);
                        //No button clicked
                        break;
                }
            }
        };
    }


    public void onCreateButtonClicked(View view) {
        bunk.name = bunkName.getText().toString();
        bunk.date = (bunkDate0.getText().toString() + "-" + bunkDate1.getText().toString() + "-" + bunkDate2.getText().toString());
        bunk.num = bunkNum.getText().toString();

        dataMap = new HashMap<String, String>();
        dataMap.put("Bunk Title", bunk.name);
        dataMap.put("Bunk Date", bunk.date);
        dataMap.put("Bunk Participants", bunk.num);

        if(!serverExists) {
            myRef.child(userUid).setValue(dataMap);
            Log.d("TAG", "If exec");
            startActivity(intent);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("You have already created a server before, Overwrite?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
            Log.d("TAG", "Else exec");
        }
    }
}

