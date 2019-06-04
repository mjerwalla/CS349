package ca.uwaterloo.cs349.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Finish extends AppCompatActivity {
    private String userName;
    private int questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Intent intent = getIntent();
        questions = intent.getIntExtra("cs349.uwaterloo.ca.questionNum", 0);
        userName = intent.getStringExtra("cs349.uwaterloo.ca.name");
        TextView tv = (TextView) findViewById(R.id.textView22);
        String name = "Name: " + userName;
        tv.setText(name);
        int total = intent.getIntExtra("cs349.uwaterloo.ca.total", 0);

        String qNum = String.valueOf(questions);
        String tNum = String.valueOf(total);
        tv = (TextView) findViewById(R.id.textView23);
        tv.setText("Your Score: " + tNum +"/" + qNum);

    }

    public void logout(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void topic(View v) {
        Intent intent = new Intent(this, TopicSelection.class);
        intent.putExtra("cs349.uwaterloo.ca.name", userName);
        startActivity(intent);
    }
}
