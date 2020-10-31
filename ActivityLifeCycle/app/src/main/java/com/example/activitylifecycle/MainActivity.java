package com.example.activitylifecycle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button showGuess;
    private EditText check;
    private final int REQUEST_CODE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showGuess=findViewById(R.id.button_guess);
        check=findViewById(R.id.guess_field);
        showGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess=check.getText().toString().trim();
                if(!guess.isEmpty()){
                    Intent intent = new Intent(MainActivity.this, ShowGuess.class);
                    intent.putExtra("guess", check.getText().toString().trim()); //Key value pair
                    intent.putExtra("name","Saksham Sachdeva");
                    intent.putExtra("city","Gwalior");
                    startActivityForResult(intent, REQUEST_CODE);
//                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this,"Enter Text",Toast.LENGTH_SHORT).show();
                }
            }
        });
//        Log.d("Cycle", "onCreate: is called");
//        Toast.makeText(MainActivity.this,"OnCreate() called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                assert data != null;
                String value=data.getStringExtra("message_back");
                Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
                Log.d("GettingResult", "onActivityResult: "+value);
            }
//        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d("Cycle", "onStart: is called");
//    }

//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        Log.d("Cycle", "onResume: is called");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("Cycle", "onPause: is called");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d("Cycle", "onStop: is called");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d("Cycle", "onDestroy: is called");
//    }
}
