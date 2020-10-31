package com.example.makeitrain;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private Button showMoney;
    private Button showTag;
    private TextView moneyText;
    private TextView newText;
    private int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moneyText=findViewById(R.id.money_text);
        newText=findViewById(R.id.new_text);
        moneyText.setTextColor(getResources().getColor(R.color.myColor2));

        showMoney=findViewById(R.id.button_make_rain);
        showTag=findViewById(R.id.button_show_tag);
//        showMoney.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("MYTAG", "onClick: Show Money");
//
//            }
//        });
    }
    public void showTag(View v){
//        Log.d("MYTAG", "showTag: Saksham Sachdeva");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        counter-=1000;
        moneyText.setText(String.valueOf(numberFormat.format(counter)));
        if(counter>=10000){
            newText.setText("Hey your amount is greater than equal to $10,000");
            moneyText.setTextColor(getResources().getColor(R.color.myColor));
        } else {
            newText.setText("Your amount is less than $10,000");
            moneyText.setTextColor(getResources().getColor(R.color.myColor2));
        }
        Toast.makeText(getApplicationContext(), "Saksham Sachdeva", Toast.LENGTH_LONG).show();
        Log.d("MYTAG1", "onClick: Show Tag");
    }
    public void makeItRain(View v){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        counter+=1000;
        moneyText.setText(String.valueOf(numberFormat.format(counter)));
        if(counter>=10000){
            newText.setText("Hey your amount is greater than equal to $10,000");
//            moneyText.setTextColor(Color.BLACK);
            moneyText.setTextColor(getResources().getColor(R.color.myColor));
        } else {
            newText.setText("Your amount is less than $10,000");
            moneyText.setTextColor(getResources().getColor(R.color.myColor2));
        }
//        moneyText.setText(Integer.toString(counter));
//        Log.d("MYTAG2", "makeItRain: Show Money "+counter);
    }
}
