package com.example.tom.businesscardholderWithContentProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tom.businesscardholder.R;

public class AddData extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        dbHelper = new MyDatabaseHelper(this, "BusinessCardHolder.db", null, 1);
        dbHelper.getWritableDatabase();
        Button Done = findViewById(R.id.done);
        final CheckBox man = findViewById(R.id.man);
        final CheckBox woman = findViewById(R.id.woman);
        final TextView name = findViewById(R.id.name);
        final TextView phone = findViewById(R.id.phone);
        final TextView address = findViewById(R.id.address);
        final TextView email = findViewById(R.id.email);
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

        Button toView = findViewById(R.id.view);
        toView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddData.this, CheckData.class);
                startActivity(intent);
            }
        });

        Button contactview = findViewById(R.id.contact);
        contactview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddData.this, Contact.class);
                startActivity(intent);
            }
        });
    }
}
