package com.example.tom.note1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private static int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        final EditText editText = (EditText) findViewById(R.id.edit_text);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNotes();
                button1.setVisibility(View.GONE);
                button2.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = editText.getText().toString();
                editText.setText("");
                SharedPreferences.Editor editor = getSharedPreferences("note", MODE_PRIVATE).edit();
                editor.putString("note" + n++, note);
                editor.apply();
                initNotes();
            }
        });
    }

    private void initNotes() {
        SharedPreferences pref = getSharedPreferences("note", MODE_PRIVATE);
        if (n == 0)
            return;
        else {
            String[] Notes = new String[n];
            for (int i = 0; i < n; i++) {
                    Notes[i] = pref.getString("note" + i, "");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,
                    Notes);
            ListView listview = (ListView) findViewById(R.id.listview);
            listview.setAdapter(adapter);
        }
    }
}
