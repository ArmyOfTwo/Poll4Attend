package com.armyof2.poll4bunk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView dateView;
    private int option = 4;
    private EditText keyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateView = (TextView) findViewById(R.id.tv_date);
        keyText = (EditText) findViewById(R.id.et_key);

        //get and set date from server

        //get unique keys from server

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rad_op1:
                if (checked)
                    option = 1;
                    break;
            case R.id.rad_op2:
                if (checked)
                    option = 2;
                    break;
            case R.id.rad_op3:
                if (checked)
                    option = 3;
                    break;
            case R.id.rad_op4:
                if (checked)
                    option = 4;
                    break;
        }
    }

    public void onConfirmButtonClicked(View view) {
        //Get the key
        String key;
        key = keyText.getText().toString();

        //Analyze and Authenticate the key


        //send the option to server
    }
}
