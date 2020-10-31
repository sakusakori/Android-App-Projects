package bawp.shownamenow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import c.bawp.learnjava.Employee;


public class MainActivity extends AppCompatActivity {
    private Button myButton;
    private TextView showText;
    private EditText enterName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton = findViewById(R.id.button);
        enterName = findViewById(R.id.editText);

        Employee employee = new Employee();
        employee.setAnnualSalary(7860);


        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = enterName.getText().toString();

                showText.setText("Hello " + name);
            }
        });

        showText = findViewById(R.id.textView);


    }
}
