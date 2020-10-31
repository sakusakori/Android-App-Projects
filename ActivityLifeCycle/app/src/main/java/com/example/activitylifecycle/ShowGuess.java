package com.example.activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowGuess extends AppCompatActivity {

    private TextView showGuessTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_guess);

        Bundle extra=getIntent().getExtras();

//        String value=getIntent().getStringExtra("guess");
        showGuessTextView=findViewById(R.id.received_textview);
        if(extra != null){
            showGuessTextView.setText(extra.getString("guess"));
        }
        //Because textview and button are inherit from view class
        showGuessTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                intent.putExtra("message_back","From Second Activity");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
