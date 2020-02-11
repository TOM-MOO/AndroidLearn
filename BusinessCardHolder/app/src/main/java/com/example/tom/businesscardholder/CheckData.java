package com.example.tom.businesscardholder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CheckData extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    ArrayList names = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_data);
        dbHelper = new MyDatabaseHelper(this, "BusinessCardHolder.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        final Cursor cursor = db.query("Business_card", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(cursor.getColumnIndex("name")));
            } while(cursor.moveToNext());
        }
        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CheckData.this, android.R.layout.simple_list_item_1,
                names);
        final ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CheckData.this, Details.class);
                intent.putExtra("index", (String)names.get(position));
                startActivity(intent);
            }
        });
    }
}
