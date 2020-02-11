package com.example.tom.businesscardholder;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class AddData extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        dbHelper = new MyDatabaseHelper(this, "BusinessCardHolder.db", null, 1);
        dbHelper.getWritableDatabase();
        Button Done = (Button) findViewById(R.id.done);
        final CheckBox man = (CheckBox) findViewById(R.id.man);
        final CheckBox woman = (CheckBox) findViewById(R.id.woman);
        final TextView name = (TextView) findViewById(R.id.name);
        final TextView phone = (TextView) findViewById(R.id.phone);
        final TextView address = (TextView) findViewById(R.id.address);
        final TextView email = (TextView) findViewById(R.id.email);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                if (man.isChecked() && !woman.isChecked())
                    sex = "男";
                else if (!man.isChecked() && woman.isChecked())
                    sex = "女";
                String Phone = phone.getText().toString();
                String Address = address.getText().toString();
                String Email = email.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", Name);
                values.put("sex", sex);
                values.put("phone", Phone);
                values.put("address", Address);
                values.put("email", Email);
                db.insert("Business_card", null, values);
                values.clear();
                name.setText("");
                man.setChecked(false);
                woman.setChecked(false);
                sex = "";
                phone.setText("");
                address.setText("");
                email.setText("");
                Toast.makeText(AddData.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
