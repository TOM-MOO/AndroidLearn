package com.example.tom.businesscardholderWithContentProvider;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.tom.businesscardholder.R;

public class Details extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        String name = intent.getStringExtra("index");
        dbHelper = new MyDatabaseHelper(this, "BusinessCardHolder.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Business_card where name = ?", new String[] {name});
        String Name = "", Sex = "", Phone = "", Address = "", Email = "";
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Name = cursor.getString(cursor.getColumnIndex("name"));
                Sex = cursor.getString(cursor.getColumnIndex("sex"));
                Phone = cursor.getString(cursor.getColumnIndex("phone"));
                Address = cursor.getString(cursor.getColumnIndex("address"));
                Email = cursor.getString(cursor.getColumnIndex("email"));
            }
        }


        TextView detail1 = findViewById(R.id.detail1);
        TextView detail2 = findViewById(R.id.detail2);
        TextView detail3 = findViewById(R.id.detail3);
        TextView detail4 = findViewById(R.id.detail4);
        TextView detail5 = findViewById(R.id.detail5);

        detail1.setText(Name);
        detail2.setText(Sex);
        detail3.setText(Phone);
        detail4.setText(Address);
        detail5.setText(Email);



        cursor.close();
    }
}
