package com.armyof2.poll4bunk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateActivity extends AppCompatActivity {

    private EditText bunkName;
    private EditText bunkDate0;
    private EditText bunkDate1;
    private EditText bunkDate2;
    private EditText bunkNum;
    private Intent intent;
    private FirebaseDatabase database;
    private DatabaseReference myRef0;
    private DatabaseReference myRef1;
    private DatabaseReference myRef2;
    private String s1 = "", s2 = "", s3 = "";
    private int i, x, a = 0;
    private boolean next = true;

    private class BunkServer {
        String name;
        String date;
        int num;
    }

    BunkServer[] bunk;

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
        bunk = new BunkServer[50];
        database = FirebaseDatabase.getInstance();

        for (i = 0; i < 50; i++) {

            bunk[i] = new BunkServer();
            s1 = "bunk" + i + "name";
            s2 = "bunk" + i + "date";
            s3 = "bunk" + i + "num";

            //Get from database
            myRef0 = database.getReference(s1);
            myRef1 = database.getReference(s2);
            myRef2 = database.getReference(s3);

            // Read from the database
            myRef0.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!next) {
                        x = a - 1;
                        Log.d("TAG", "Value of x is: " + x);
                        return;
                    }

                    String value = dataSnapshot.getValue(String.class);
                    Log.d("TAG", "Value is: " + value);
                    ++a;
                    if(value == null) {
                        next = false;
                        Log.d("TAG", "next is false now");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });
            Log.d("TAG", "Break at i: " + i);
            /*if(!next) {
                Log.d("TAG", "Break at i: " + i);
                break;
            }*/
        }
        myRef0 = database.getReference("bunk" + a + "name");
        myRef1 = database.getReference("bunk" + a + "date");
        myRef2 = database.getReference("bunk" + a + "num");
        Log.d("TAG", "Value of a is: " + a);
    }

    public void onCreateButtonClicked(View view) {
        myRef0 = database.getReference("bunk" + a + "name");
        myRef1 = database.getReference("bunk" + a + "date");
        myRef2 = database.getReference("bunk" + a + "num");

        bunk[x].name = bunkName.getText().toString();
        bunk[x].date = (bunkDate0.getText().toString() + "-" + bunkDate1.getText().toString() + "-" + bunkDate2.getText().toString());
        bunk[x].num = Integer.parseInt(bunkNum.getText().toString());
        makeFile(bunk[x].name, bunk[x].date, bunk[x].num);
        //start
        startActivity(intent);
    }

    public void makeFile(String bname, String bdate, int bnum) {
        myRef0 = database.getReference("bunk" + x + "name");
        myRef1 = database.getReference("bunk" + x + "date");
        myRef2 = database.getReference("bunk" + x + "num");
        myRef0.setValue(bname);
        myRef1.setValue(bdate);
        myRef2.setValue(bnum);
    }

    public DatabaseReference getRef0(){
        return myRef0;
    }

    public DatabaseReference getRef1(){
        return myRef1;
    }

    public DatabaseReference getRef2(){
        return myRef2;
    }
}

