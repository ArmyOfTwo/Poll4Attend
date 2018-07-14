package com.armyof2.poll4bunk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.armyof2.poll4bunk.LaunchActivity.SERVER_ID;
import static com.armyof2.poll4bunk.SignInActivity.userUid;

public class MainActivity extends AppCompatActivity {

    private TextView dateView;
    private TextView titleView;
    private int option = 4;
    private String i = "0", j = "0", k = "0", l = "0";
    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef2;
    private ArrayList<String> bunkServerStuff;
    private ArrayList<String> bunkServerVotes;
    private RadioButton r1, r2, r3, r4;
    private String hasVoted = "yolo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateView = (TextView) findViewById(R.id.tv_date);
        titleView = (TextView) findViewById(R.id.tv_title);
        r1 = (RadioButton) findViewById(R.id.rad_op1);
        r2 = (RadioButton) findViewById(R.id.rad_op2);
        r3 = (RadioButton) findViewById(R.id.rad_op3);
        r4 = (RadioButton) findViewById(R.id.rad_op4);

        r1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                option = 1;
                r1.setChecked(true);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                option = 2;
                r1.setChecked(false);
                r2.setChecked(true);
                r3.setChecked(false);
                r4.setChecked(false);
            }
        });

        r3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                option = 3;
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(true);
                r4.setChecked(false);
            }
        });

        r4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                option = 4;
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(true);
            }
        });

        bunkServerStuff = new ArrayList<>();
        bunkServerVotes = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(SERVER_ID);
        myRef2 = database.getReference().child(SERVER_ID).child("YuserUIDs");

        myRef.child("Yes");
        myRef.child("No");
        myRef.child("Yes80");
        myRef.child("Undec");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(!dataSnapshot.getKey().equals("YuserUIDs")) {
                    String value = dataSnapshot.getValue(String.class);
                    bunkServerStuff.add(value);
                    Log.d("TAG", "onChildAdded: " + bunkServerStuff);
                    dateView.setText(bunkServerStuff.get(0));
                    if (bunkServerStuff.size() == 3)
                        titleView.setText(bunkServerStuff.get(2));
                    bunkServerVotes.add(value);
                    if (dataSnapshot.getKey().equals("Yes")) {
                        i = value;
                        Log.d("TAG", "i = " + i);
                    }
                    if (dataSnapshot.getKey().equals("No"))
                        j = value;
                    if (dataSnapshot.getKey().equals("Yes80"))
                        k = value;
                    if (dataSnapshot.getKey().equals("Undec"))
                        l = value;
                }
                    //Log.d("TAG", "i = " + i + ", j = " + j + ", k = " + k + ", l = " + l);
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(!dataSnapshot.getKey().equals("YuserUIDs")) {
                    String value = dataSnapshot.getValue(String.class);
                    bunkServerVotes.add(value);
                    if (dataSnapshot.getKey().equals("Yes"))
                        i = value;
                    if (dataSnapshot.getKey().equals("No"))
                        j = value;
                    if (dataSnapshot.getKey().equals("Yes80"))
                        k = value;
                    if (dataSnapshot.getKey().equals("Undec"))
                        l = value;
                    Log.d("TAG", "i = " + i + ", j = " + j + ", k = " + k + ", l = " + l);
                }
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


        myRef2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                if (dataSnapshot.getKey().equals(userUid)) {
                    hasVoted = value;
                    Log.d("TAG", "onChildAdded : hasVoted = " + hasVoted);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                if (dataSnapshot.child("YuserUIDs").getKey().equals(userUid)) {
                    hasVoted = value;
                    Log.d("TAG", "onChildChanged : hasVoted = " + hasVoted);
                }
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

    public void onConfirmButtonClicked(View view) {
        switch (option){
            case 1:
                int p = Integer.parseInt(i);
                ++p;
                int q = Integer.parseInt(j);
                --q;
                int r = Integer.parseInt(k);
                --r;
                int s = Integer.parseInt(l);
                --s;

                if(hasVoted.equals("yes")) {
                    break;
                } else if(hasVoted.equals("no")){
                    myRef.child("No").setValue(Integer.toString(q));
                } else if(hasVoted.equals("yes80")){
                    myRef.child("Yes80").setValue(Integer.toString(r));
                } else if(hasVoted.equals("undec")){
                    myRef.child("Undec").setValue(Integer.toString(s));
                }
                myRef.child("Yes").setValue(Integer.toString(p));
                myRef.child("YuserUIDs").child(userUid).setValue("yes");
                hasVoted = "yes";
                break;

            case 2:
                p = Integer.parseInt(i);
                --p;
                q = Integer.parseInt(j);
                ++q;
                r = Integer.parseInt(k);
                --r;
                s = Integer.parseInt(l);
                --s;

                if(hasVoted.equals("no")) {
                    break;
                } else if(hasVoted.equals("yes")){
                    myRef.child("Yes").setValue(Integer.toString(p));
                } else if(hasVoted.equals("yes80")){
                    myRef.child("Yes80").setValue(Integer.toString(r));
                } else if(hasVoted.equals("undec")){
                    myRef.child("Undec").setValue(Integer.toString(s));
                }
                myRef.child("No").setValue(Integer.toString(q));
                myRef.child("YuserUIDs").child(userUid).setValue("no");
                hasVoted = "no";
                break;

            case 3:
                p = Integer.parseInt(i);
                --p;
                q = Integer.parseInt(j);
                --q;
                r = Integer.parseInt(k);
                ++r;
                s = Integer.parseInt(l);
                --s;

                if(hasVoted.equals("yes80")) {
                    break;
                } else if(hasVoted.equals("no")){
                    myRef.child("No").setValue(Integer.toString(q));
                } else if(hasVoted.equals("yes")){
                    myRef.child("Yes").setValue(Integer.toString(p));
                } else if(hasVoted.equals("undec")){
                    myRef.child("Undec").setValue(Integer.toString(s));
                }
                myRef.child("Yes80").setValue(Integer.toString(r));
                myRef.child("YuserUIDs").child(userUid).setValue("yes80");
                hasVoted = "yes80";
                break;

            case 4:
                p = Integer.parseInt(i);
                --p;
                q = Integer.parseInt(j);
                --q;
                r = Integer.parseInt(k);
                --r;
                s = Integer.parseInt(l);
                ++s;

                if(hasVoted.equals("undec")) {
                    break;
                } else if(hasVoted.equals("no")){
                    myRef.child("No").setValue(Integer.toString(q));
                } else if(hasVoted.equals("yes80")){
                    myRef.child("Yes80").setValue(Integer.toString(r));
                } else if(hasVoted.equals("yes")){
                    myRef.child("Yes").setValue(Integer.toString(p));
                }
                myRef.child("Undec").setValue(Integer.toString(s));
                myRef.child("YuserUIDs").child(userUid).setValue("undec");
                hasVoted = "undec";
                break;
        }
    }
}
