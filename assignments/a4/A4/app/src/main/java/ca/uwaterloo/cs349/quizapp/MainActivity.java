package ca.uwaterloo.cs349.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        btn.setEnabled(false);
//        btn.setBackgroundColor(Color.BLACK);

        EditText tv = (EditText) findViewById(R.id.editText);
        tv.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Button btn = (Button) findViewById(R.id.button);
                if(s.length() != 0){

                    btn.setBackgroundColor(Color.parseColor("#DAA520"));
                    btn.setEnabled(true);
                }
                else {
                    btn.setBackgroundColor(Color.LTGRAY);
                    btn.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
//                Button btn = (Button) findViewById(R.id.button);
//                btn.setBackgroundColor(Color.BLACK);
//                btn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

    }

    public void sendMessage(View v) {
        Intent intent = new Intent(this, TopicSelection.class);
        EditText eTxt = (EditText) findViewById(R.id.editText);
        intent.putExtra("cs349.uwaterloo.ca.name", eTxt.getText().toString());
        startActivity(intent);


        // Do Nothing
    }
}
