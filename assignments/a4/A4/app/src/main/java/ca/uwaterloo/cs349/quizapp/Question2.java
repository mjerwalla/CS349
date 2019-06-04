package ca.uwaterloo.cs349.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class Question2 extends AppCompatActivity {
    private String userName;
    private int questions;
    private int total;
    private int correctChoices;
    private int wrongChoices;
    private boolean result;
//    private questionList qs;
//    private question q2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        Intent intent = getIntent();
        result = false;
        correctChoices = 0;
        wrongChoices = 0;
        questions = intent.getIntExtra("cs349.uwaterloo.ca.questionNum", 0);
        userName = intent.getStringExtra("cs349.uwaterloo.ca.name");
        total = intent.getIntExtra("cs349.uwaterloo.ca.total", 0);
//        qs = ((questionList)intent.getSerializableExtra("cs349.uwaterloo.ca.array"));
        TextView tv = (TextView) findViewById(R.id.textView4);
        String name = "Name: " + userName;
        tv.setText(name);

        Button btn = (Button) findViewById(R.id.button9);
        if (questions == 2) {
            btn.setText("FINISH");
        } else {
            btn.setText("NEXT");
        }

        String qNum = String.valueOf(questions);
        qNum = "2/"+qNum;
        tv = (TextView) findViewById(R.id.textView12);
        tv.setText(qNum);
//        if (qs.qList.size()>=2) {
//            q2 = qs.qList.get(1);
//            CheckBox c = (CheckBox) findViewById(R.id.checkbox1);
//            c.setChecked(q2.op1);
//            c = (CheckBox) findViewById(R.id.checkbox2);
//            c.setChecked(q2.op2);
//            c = (CheckBox) findViewById(R.id.checkbox3);
//            c.setChecked(q2.op3);
//            c = (CheckBox) findViewById(R.id.checkbox4);
//            c.setChecked(q2.op4);
//        } else {
//            q2 = new question();
//            qs.qList.add(q2);
//        }

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
        if (questions == 2) {
            intent = new Intent(this, Finish.class);
        } else {
            intent = new Intent(this, Question3.class);
        }
        intent.putExtra("cs349.uwaterloo.ca.questionNum", questions);
        intent.putExtra("cs349.uwaterloo.ca.name", userName);
        intent.putExtra("cs349.uwaterloo.ca.total", total);
//        intent.putExtra("cs349.uwaterloo.ca.array", qs);
        startActivity(intent);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox1:
//                qs.qList.get(1).op1 = !qs.qList.get(1).op1;
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
            case R.id.checkbox2:
//                qs.qList.get(1).op2 = !qs.qList.get(1).op2;
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
            case R.id.checkbox3:
//                qs.qList.get(1).op3 = !qs.qList.get(1).op3;
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
            case R.id.checkbox4:
//                qs.qList.get(1).op4 = !qs.qList.get(1).op4;
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
