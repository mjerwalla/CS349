package ca.uwaterloo.cs349.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class Question1 extends AppCompatActivity {
    private String userName;
    private int questions;
    private boolean result;
    private int total;
//    private questionList qs;
//    private question q1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);
        result = false;
        Intent intent = getIntent();
        questions = intent.getIntExtra("cs349.uwaterloo.ca.questionNum", 0);
        userName = intent.getStringExtra("cs349.uwaterloo.ca.name");
//        qs = ((questionList)intent.getSerializableExtra("cs349.uwaterloo.ca.array"));
        total = 0;
        TextView tv = (TextView) findViewById(R.id.textView5);
        String name = "Name: " + userName;
        tv.setText(name);

        Button btn = (Button) findViewById(R.id.button5);
        btn.setEnabled(false);
        btn = (Button) findViewById(R.id.button6);
        if (questions == 1) {
            btn.setText("FINISH");
        } else {
            btn.setText("NEXT");
        }

        String qNum = String.valueOf(questions);
        qNum = "1/"+qNum;
        tv = (TextView) findViewById(R.id.textView11);
        tv.setText(qNum);
//        if (qs.qList.size() > 0){
//            q1 = qs.qList.get(0);
//            RadioButton r = (RadioButton) findViewById(R.id.radioButton4);
//            r.setChecked(q1.op1);
//            r = (RadioButton) findViewById(R.id.radioButton5);
//            r.setChecked(q1.op2);
////        r.setText("Taiwan");
//            r = (RadioButton) findViewById(R.id.radioButton6);
//            r.setChecked(q1.op3);
////            r.setText("China");
//            r = (RadioButton) findViewById(R.id.radioButton7);
//            r.setChecked(q1.op4);
////        r.setText("Peru");
//
//        } else {
//            q1 = new question();
//            qs.qList.add(q1);
//        }
    }

    public void logout(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Next(View v) {
        Intent intent;
        if (questions == 1) {
            intent = new Intent(this, Finish.class);
        } else {
            intent = new Intent(this, Question2.class);
        }
        intent.putExtra("cs349.uwaterloo.ca.questionNum", questions);
        intent.putExtra("cs349.uwaterloo.ca.name", userName);
        intent.putExtra("cs349.uwaterloo.ca.total", total);
//        intent.putExtra("cs349.uwaterloo.ca.array", qs);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButton4:
//                qs.qList.get(0).op1 = !qs.qList.get(0).op1;
                if (checked) {
                    result = true;
                    total += 1;

                }
                break;
            case R.id.radioButton5:
//                qs.qList.get(0).op2 = !qs.qList.get(0).op2;
                if (checked) {
                    if (result == true){
                        total -=1;
                        result = false;
                    }
                    // Forget about me

                }
                break;
            case R.id.radioButton6:
//                qs.qList.get(0).op3 = !qs.qList.get(0).op3;
                if (checked) {
                    if (result == true) {
                        total -= 1;
                        result = false;
                    }
                    // Maybe not...

                }
                break;

            case R.id.radioButton7:
//                qs.qList.get(0).op4 = !qs.qList.get(0).op4;
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
