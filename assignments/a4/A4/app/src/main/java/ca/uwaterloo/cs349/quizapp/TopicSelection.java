package ca.uwaterloo.cs349.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TopicSelection extends AppCompatActivity {
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);

        Intent intent = getIntent();
        String name = intent.getStringExtra("cs349.uwaterloo.ca.name");
        userName = name;
        name = "Welcome " + userName;
        TextView tv = (TextView) findViewById(R.id.textView3);
        tv.setText(name);

        Spinner spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.num_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    public void logout(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void load(View v) {
        Intent intent = new Intent(this, Question1.class);
        Spinner mySpinner=(Spinner) findViewById(R.id.spinner);
        int spinner_pos = mySpinner.getSelectedItemPosition();
        String[] size_values = getResources().getStringArray(R.array.num_array);
        int size = Integer.valueOf(size_values[spinner_pos]);
        intent.putExtra("cs349.uwaterloo.ca.questionNum", size);
        intent.putExtra("cs349.uwaterloo.ca.name", userName);
        questionList qs = new questionList();
        intent.putExtra("cs349.uwaterloo.ca.array", qs);
        startActivity(intent);
    }
}
