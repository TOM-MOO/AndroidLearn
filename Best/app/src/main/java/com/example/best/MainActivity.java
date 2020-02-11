package com.example.best;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText text;
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button done = findViewById(R.id.done);
        show = findViewById(R.id.show);
        text = findViewById(R.id.text);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = text.getText().toString();
                if (s == "") {
                    Toast.makeText(MainActivity.this, "Please type something in the blank!", Toast.LENGTH_SHORT).show();
                }
                else {
                    show.setText(s);
                }
            }
        });
    }
}