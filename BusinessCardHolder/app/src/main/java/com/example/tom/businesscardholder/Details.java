package com.example.tom.businesscardholder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        String name = intent.getStringExtra("index");
        dbHelper = new MyDatabaseHelper(this, "BusinessCardHolder.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Business_card where name = ?", new String[] {name});
        //Cursor cursor = db.query("Business_card", null, null, null, null, null, null);
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
        TextView detail = (TextView) findViewById(R.id.detail);
        detail.setText("姓名：" + Name + "\n性别：" + Sex + "\n联系方式：" + Phone + "\n单位：" + Address + "\n电子邮件：" + Email);
        cursor.close();
    }
}
