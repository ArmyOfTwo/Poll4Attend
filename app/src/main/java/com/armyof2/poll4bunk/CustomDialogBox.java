package com.armyof2.poll4bunk;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static com.armyof2.poll4bunk.MainActivity.BUNK_NO;
import static com.armyof2.poll4bunk.MainActivity.BUNK_UNDEC;
import static com.armyof2.poll4bunk.MainActivity.BUNK_YES;
import static com.armyof2.poll4bunk.MainActivity.BUNK_YES80;
import static com.armyof2.poll4bunk.MainActivity.posp;

public class CustomDialogBox extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button ok;
    public static TextView titlView, arrayView;
    String sorted = "";

    public CustomDialogBox(Activity a) {
        super(a);

        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        titlView = (TextView) findViewById(R.id.txt_dia);
        arrayView = (TextView) findViewById(R.id.txt_bunkers);
        TextView tv = (TextView) findViewById(R.id.txt_bunkers);
        tv.setMovementMethod(new ScrollingMovementMethod());
        if(posp == 0) {
            titlView.setText("People who voted for \n                'Yes':");
            sortAl(BUNK_YES);
            arrayView.setText(sorted);
        } else if(posp == 1){
            titlView.setText("People who voted for \n                 'No':");
            sortAl(BUNK_NO);

            arrayView.setText(sorted);
        } else if(posp == 2){
            titlView.setText("People who voted for \n         'Yes if 80%':");
            sortAl(BUNK_YES80);
            arrayView.setText(sorted);
        } else {
            titlView.setText("People who voted for \n         'Undecided':");
            sortAl(BUNK_UNDEC);
            arrayView.setText(sorted);
        }

        ok = (Button) findViewById(R.id.btn_yes);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void sortAl(ArrayList<String> arrayList) {
        if (arrayList == null)
            sorted = "";
        else {
            for (String l : arrayList) {
                sorted = sorted + "\n" + l;
            }
        }
    }
}
