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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.armyof2.poll4bunk.LaunchActivity.SERVER_ID;
import static com.armyof2.poll4bunk.LaunchActivity.name;
import static com.armyof2.poll4bunk.SignInActivity.userUid;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> bunkYes, bunkNo, bunkYes80, bunkUndec;
    private TextView dateView;
    private TextView titleView;
    private int option = 4;
    public static String i = "0", j = "0", k = "0", l = "0";
    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef2, myRef3;
    private ArrayList<String> bunkServerStuff;
    private ArrayList<String> bunkServerVotes;
    private RadioButton r1, r2, r3, r4;
    private String hasVoted = "yolo", added = "yolo";

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
        bunkYes = new ArrayList<>();
        bunkNo = new ArrayList<>();
        bunkYes80 = new ArrayList<>();
        bunkUndec = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(SERVER_ID);
        myRef2 = database.getReference().child(SERVER_ID).child("YuserUIDs");
        myRef3 = database.getReference().child(SERVER_ID).child("Bunker's Name");

        myRef.child("Yes");
        myRef.child("No");
        myRef.child("Yes80");
        myRef.child("Undec");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(!dataSnapshot.getKey().equals("YuserUIDs") && !dataSnapshot.getKey().equals("Bunker's Name")) {
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
                if(!dataSnapshot.getKey().equals("YuserUIDs") && !dataSnapshot.getKey().equals("Bunker's Name")) {
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
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                final String value = dataSnapshot.getValue(String.class);
                if (dataSnapshot.getKey().equals(userUid)) {
                    hasVoted = value;
                    Log.d("TAG", "onChildAdded : hasVoted = " + hasVoted);
                }

                    myRef3.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot3, String s) {
                            String val = dataSnapshot3.getValue(String.class);
                            Log.d("TAG", "value = " + value);
                            if(dataSnapshot.getKey().equals(dataSnapshot3.getKey())){
                                if (value.equals("yes")) {
                                    if(added.equals("no") && !name.equals(val))
                                        bunkNo.remove(bunkNo.size() - 1);
                                    else if(added.equals("yes80") && !name.equals(val))
                                        bunkYes80.remove(bunkYes80.size() - 1);
                                    else if(added.equals("undec") && !name.equals(val))
                                        bunkUndec.remove(bunkUndec.size() - 1);
                                    bunkYes.add(val);
                                    Log.d("TAG", "bunkYesAddedAdded = " + bunkYes);
                                    added = "yes";
                                }
                                else if (value.equals("no")) {
                                    if(added.equals("yes") && !name.equals(val))
                                        bunkYes.remove(bunkYes.size() - 1);
                                    else if(added.equals("yes80") && !name.equals(val))
                                        bunkYes80.remove(bunkYes80.size() - 1);
                                    else if(added.equals("undec") && !name.equals(val))
                                        bunkUndec.remove(bunkUndec.size() - 1);
                                    bunkNo.add(val);
                                    Log.d("TAG", "bunkNoAddedAdded = " + bunkNo);
                                    added = "no";
                                }
                                else if (value.equals("yes80")) {
                                    if(added.equals("no") && !name.equals(val))
                                        bunkNo.remove(bunkNo.size() - 1);
                                    else if(added.equals("yes") && !name.equals(val))
                                        bunkYes.remove(bunkYes.size() - 1);
                                    else if(added.equals("undec") && !name.equals(val))
                                        bunkUndec.remove(bunkUndec.size() - 1);
                                    bunkYes80.add(val);
                                    Log.d("TAG", "bunkYes80AddedAdded = " + bunkYes80);
                                    added = "yes80";
                                }
                                else if (value.equals("undec")) {
                                    if(added.equals("no") && !name.equals(val))
                                        bunkNo.remove(bunkNo.size() - 1);
                                    else if(added.equals("yes80") && !name.equals(val))
                                        bunkYes80.remove(bunkYes80.size() - 1);
                                    else if(added.equals("yes") && !name.equals(val))
                                        bunkYes.remove(bunkYes.size() - 1);
                                    bunkUndec.add(val);
                                    Log.d("TAG", "bunkUndecAddedAdded = " + bunkUndec);
                                    added = "undec";
                                }

                            }
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

            @Override
            public void onChildChanged(final DataSnapshot dataSnapshot, String s) {
                final String value = dataSnapshot.getValue(String.class);
                if (dataSnapshot.child("YuserUIDs").getKey().equals(userUid)) {
                    hasVoted = value;
                    Log.d("TAG", "onChildChanged : hasVoted = " + hasVoted);
                }

                    myRef3.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot3, String s) {
                            String val = dataSnapshot3.getValue(String.class);
                            if (dataSnapshot.getKey().equals(dataSnapshot3.getKey())) {
                                if (value.equals("yes")) {
                                    if (added.equals("no") && name.equals(val))
                                        removeStringFromArraylist(val, bunkNo);
                                    else if (added.equals("yes80") && name.equals(val))
                                        removeStringFromArraylist(val, bunkYes80);
                                    else if (added.equals("undec") && name.equals(val))
                                        removeStringFromArraylist(val, bunkUndec);
                                    bunkYes.add(val);
                                    Log.d("TAG", "bunkYesChangedAdded = " + bunkYes);
                                    added = "yes";
                                } else if (value.equals("no")) {
                                    if (added.equals("yes") && name.equals(val))
                                        removeStringFromArraylist(val, bunkYes);
                                    else if (added.equals("yes80") && name.equals(val))
                                        removeStringFromArraylist(val, bunkYes80);
                                    else if (added.equals("undec") && name.equals(val))
                                        removeStringFromArraylist(val, bunkUndec);
                                    bunkNo.add(val);
                                    Log.d("TAG", "bunkNoChangedAdded = " + bunkNo);
                                    added = "no";
                                } else if (value.equals("yes80")) {
                                    if (added.equals("no") && name.equals(val))
                                        removeStringFromArraylist(val, bunkNo);
                                    else if (added.equals("yes") && name.equals(val))
                                        removeStringFromArraylist(val, bunkYes);
                                    else if (added.equals("undec") && name.equals(val))
                                        removeStringFromArraylist(val, bunkUndec);
                                    bunkYes80.add(val);
                                    Log.d("TAG", "bunkYes80ChangedAdded = " + bunkYes80);
                                    added = "yes80";
                                } else if (value.equals("undec")) {
                                    if (added.equals("no") && name.equals(val))
                                        removeStringFromArraylist(val, bunkNo);
                                    else if (added.equals("yes80") && name.equals(val))
                                        removeStringFromArraylist(val, bunkYes80);
                                    else if (added.equals("yes") && name.equals(val))
                                        removeStringFromArraylist(val, bunkYes);
                                    bunkUndec.add(val);
                                    Log.d("TAG", "bunkUndecChangedAdded = " + bunkUndec);
                                    added = "undec";
                                }
                            }
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

    public void removeStringFromArraylist(String s, ArrayList<String> list)
    {
        for (int i = 0; i < list.size(); i++){
            if(s.equals(list.get(i)))
                list.remove(i);
        }
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
