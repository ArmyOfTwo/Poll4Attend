package com.armyof2.poll4bunk;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

import static com.armyof2.poll4bunk.SignInActivity.userUid;

public class CreateActivity extends FragmentActivity {

    static EditText bunkDate;
    private EditText bunkName;
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
        bunkDate = (EditText) findViewById(R.id.et_date);
        bunkDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        bunkNum = (EditText) findViewById(R.id.et_numofparti);
        intent = new Intent(this, LaunchActivity.class);
        bunk = new BunkServer();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(userUid))
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
                switch (which) {
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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            bunkDate.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    public void onCreateButtonClicked(View view) {
        bunk.name = bunkName.getText().toString();
        bunk.date = bunkDate.getText().toString();
        bunk.num = bunkNum.getText().toString();

        if(bunk.name.equals("")){
            Toast.makeText(this, "You forgot to input title!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(bunk.date.equals("")){
            Toast.makeText(this, "You forgot to input date!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(bunk.num.equals("")){
            Toast.makeText(this, "You forgot to input participants!", Toast.LENGTH_SHORT).show();
            return;
        }

        dataMap = new HashMap<String, String>();
        dataMap.put("Bunk Title", bunk.name);
        dataMap.put("Bunk Date", bunk.date);
        dataMap.put("Bunk Participants", bunk.num);

        if (!serverExists) {
            myRef.child(userUid).setValue(dataMap);
            Log.d("TAG", "If exec");
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("You have already created a server before, Overwrite?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
            Log.d("TAG", "Else exec");
        }
    }
}

