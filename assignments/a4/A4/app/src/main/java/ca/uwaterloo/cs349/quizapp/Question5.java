package ca.uwaterloo.cs349.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class Question5 extends AppCompatActivity {
    private String userName;
    private int questions;
    private int total;
    private int correctChoices;
    private int wrongChoices;
    private boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        result = false;
        correctChoices = 0;
        wrongChoices = 0;

        Intent intent = getIntent();
        questions = intent.getIntExtra("cs349.uwaterloo.ca.questionNum", 0);
        userName = intent.getStringExtra("cs349.uwaterloo.ca.name");
        total = intent.getIntExtra("cs349.uwaterloo.ca.total", 0);
        TextView tv = (TextView) findViewById(R.id.textView19);
        String name = "Name: " + userName;
        tv.setText(name);

        Button btn = (Button) findViewById(R.id.button18);
        btn.setText("FINISH");

        String qNum = String.valueOf(questions);
        qNum = "5/"+qNum;
        tv = (TextView) findViewById(R.id.textView21);
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
        intent = new Intent(this, Finish.class);
        intent.putExtra("cs349.uwaterloo.ca.questionNum", questions);
        intent.putExtra("cs349.uwaterloo.ca.name", userName);
        intent.putExtra("cs349.uwaterloo.ca.total", total);
        startActivity(intent);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox7:
                if (checked) {
                    correctChoices+=1;
                    if ((correctChoices == 2) && (wrongChoices == 0)){
                        result = true;
                        total+=1;
                    }
                    // Add morning session
                } else{
                    correctChoices-=1;
                    if(result){
                        total -=1;
                        result = false;
                    }
                    // Remove morning session
                }
                break;
            case R.id.checkbox5:
                if (checked){
                    if (correctChoices ==2) {
                        wrongChoices+=1;
                        if (result) {
                            result = false;
                            total -=1;
                        }
                    }
                    // Add afternoon session
                } else {
                    wrongChoices -=1;
                    if (wrongChoices == 0){
                        if (correctChoices == 2) {
                            result = true;
                            total+=1;
                        }
                    }
                    // Remove afternoon session
                }
                break;
            case R.id.checkbox8:
                if (checked){
                    correctChoices+=1;
                    if ((correctChoices == 2) && (wrongChoices == 0)){
                        result = true;
                        total+=1;
                    }
                    // Add afternoon session
                } else {
                    correctChoices-=1;
                    if(result){
                        total -=1;
                        result = false;
                    }
                    // Remove afternoon session
                }
                break;
            case R.id.checkbox6:
                if (checked){
                    if (correctChoices ==2) {
                        wrongChoices+=1;
                        if (result) {
                            result = false;
                            total -=1;
                        }
                        // Add afternoon session
                    } else {
                        wrongChoices -=1;
                        if (wrongChoices == 0){
                            if (correctChoices == 2) {
                                result = true;
                                total+=1;
                            }
                        }
                    }
                    // Remove afternoon session
                }
                break;
        }

    }
}
