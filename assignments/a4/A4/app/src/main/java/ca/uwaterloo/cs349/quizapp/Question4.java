package ca.uwaterloo.cs349.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class Question4 extends AppCompatActivity {
    private String userName;
    private int questions;
    private int total;
    private boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);
        result = false;
        Intent intent = getIntent();
        questions = intent.getIntExtra("cs349.uwaterloo.ca.questionNum", 0);
        userName = intent.getStringExtra("cs349.uwaterloo.ca.name");
        total = intent.getIntExtra("cs349.uwaterloo.ca.total", 0);
        TextView tv = (TextView) findViewById(R.id.textView16);
        String name = "Name: " + userName;
        tv.setText(name);

        Button btn = (Button) findViewById(R.id.button15);
        if (questions == 4) {
            btn.setText("FINISH");
        } else {
            btn.setText("NEXT");
        }

        String qNum = String.valueOf(questions);
        qNum = "4/"+qNum;
        tv = (TextView) findViewById(R.id.textView18);
        tv.setText(qNum);
    }


    public void logout(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void previous(View v)
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }

    public void Next(View v) {
        Intent intent;
        if (questions == 4) {
            intent = new Intent(this, Finish.class);
        } else {
            intent = new Intent(this, Question5.class);
        }
        intent.putExtra("cs349.uwaterloo.ca.questionNum", questions);
        intent.putExtra("cs349.uwaterloo.ca.name", userName);
        intent.putExtra("cs349.uwaterloo.ca.total", total);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButton15:
                if (checked) {
                    result = true;
                    total += 1;

                }
                break;
            case R.id.radioButton12:
                if (checked) {
                    if (result == true){
                        total -=1;
                        result = false;
                    }
                    // Forget about me

                }
                break;
            case R.id.radioButton13:
                if (checked) {
                    if (result == true) {
                        total -= 1;
                        result = false;
                    }
                    // Maybe not...

                }
                break;

            case R.id.radioButton14:
                if (checked){
                    if (result == true){
                        total -=1;
                        result = false;
                    }

                }
                break;
        }
    }
}
