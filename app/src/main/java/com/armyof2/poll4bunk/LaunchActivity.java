package com.armyof2.poll4bunk;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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
    private Toast mToast;
    private GoogleSignInAccount acc;
    public static String name2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale( getResources().getConfiguration());
        adjustDisplayScale( getResources().getConfiguration());
        setContentView(R.layout.activity_launch);

        pollName = (EditText) findViewById(R.id.et_pollname);
        bunkerName = (EditText) findViewById(R.id.et_bunkername);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mToast = Toast.makeText( this  , "" , Toast.LENGTH_SHORT );

        acc = GoogleSignIn.getLastSignedInAccount(this);
        if (acc != null) {
            name2 = acc.getDisplayName();
            String[] strArray = name2.split(" ");
            StringBuilder builder = new StringBuilder();
            for (String s : strArray) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap + " ");
            }
            bunkerName.setText(builder.toString());
        }
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

                    if(Title.equals(serverName) || Title.equals(serverName+"}")){
                        goodToast();
                        SERVER_ID = child.getKey();
                        myRef.child(SERVER_ID).child("Bunker's Name").child(userUid).setValue(name);
                        Log.d("TAG", "SERVER_ID = " + SERVER_ID);
                        startActivity(intent);
                        return;
                    } else
                        badToast();
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
        mToast.setText("Server joined!");
        mToast.show();
    }

    public void badToast(){
        mToast.setText("Server does not exists!");
        mToast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Intent i = new Intent(this,SignInActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.about:
                i = new Intent(this,Info.class);
                startActivity(i);
                return true;

            case R.id.clog:
                i = new Intent(this, com.armyof2.poll4bunk.Log.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void adjustFontScale(Configuration configuration) {
        if (configuration != null && configuration.fontScale != 1.0) {
            configuration.fontScale = (float) 1.0;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            this.getResources().updateConfiguration(configuration, metrics);
        }
    }

    public void adjustDisplayScale(Configuration configuration) {
        if (configuration != null) {
            Log.d("TAG", "adjustDisplayScale: " + configuration.densityDpi);
            if(configuration.densityDpi >= 485)
                configuration.densityDpi = 500;
            else if(configuration.densityDpi >= 300)
                configuration.densityDpi = 400;
            else if(configuration.densityDpi >= 100)
                configuration.densityDpi = 200;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.densityDpi * metrics.density;
            this.getResources().updateConfiguration(configuration, metrics);
        }
    }
}

