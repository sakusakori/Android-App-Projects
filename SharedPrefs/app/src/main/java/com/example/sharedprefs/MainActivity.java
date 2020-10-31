package com.example.sharedprefs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String MESSAGE_ID = "messages_prefs";
    private EditText editText;
    private TextView textView;
    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textView);
        myButton=findViewById(R.id.button);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString().trim();
                SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("message1",message);

                editor.apply(); //here we are saving to disk
            }
        });
        //Get data from Shared Preferences
        SharedPreferences getSharedPrefs=getSharedPreferences(MESSAGE_ID,MODE_PRIVATE);
        String value = getSharedPrefs.getString("message1","Nothing YET");
        textView.setText(value);
    }
}