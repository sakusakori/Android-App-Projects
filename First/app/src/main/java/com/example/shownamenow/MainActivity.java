package com.example.shownamenow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button myButton;
    private TextView showText;
    private EditText enterName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton=findViewById(R.id.button);
        showText=findViewById(R.id.textView);
        enterName=findViewById(R.id.editText);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=enterName.getText().toString();
                showText.setText("Hello " + name);
            }
        });

    }
}
