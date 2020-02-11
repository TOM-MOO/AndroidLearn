package com.example.test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        Intent intent = getIntent();
        final String id = intent.getStringExtra("ID");

        DatabaseHelper dbHelper = new DatabaseHelper(this, "User.db", null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        final EditText oriP = findViewById(R.id.oriPassword);
        final EditText newP = findViewById(R.id.newPassword);
        final EditText coNewP = findViewById(R.id.coNewPassword);
        Button mod = findViewById(R.id.modify);

        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String o = oriP.getText().toString();
                String n = newP.getText().toString();
                String c = coNewP.getText().toString();

                if (!o.equals("")) {
                    Cursor cursor = db.rawQuery("select password from user where id = '" + id + "'", null);
                    cursor.moveToNext();
                    String realP = cursor.getString(0);
                    if (realP.equals(o)) {
                        if (n.equals(c)) {
                            if (!n.equals("")) {
                                db.execSQL("update user set password = '" + n + "'" + " where id = '" + id + "'");
                                Toast.makeText(ModifyPassword.this, R.string.successModify, Toast.LENGTH_SHORT).show();
                                finish();
                            } else
                                Toast.makeText(ModifyPassword.this, R.string.newPasswordEmpty, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ModifyPassword.this, R.string.consistency, Toast.LENGTH_SHORT).show();
                            newP.setText("");
                            coNewP.setText("");
                        }
                    } else {
                        Toast.makeText(ModifyPassword.this, R.string.oriPasswordWrong, Toast.LENGTH_SHORT).show();
                        oriP.setText("");
                    }
                } else
                    Toast.makeText(ModifyPassword.this, R.string.oriPasswordEmpty, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
