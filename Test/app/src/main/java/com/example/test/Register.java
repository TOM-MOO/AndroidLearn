package com.example.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        DatabaseHelper dbHelper = new DatabaseHelper(this, "User.db", null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        Button register = findViewById(R.id.register);
        final EditText id = findViewById(R.id.reId);
        final EditText password = findViewById(R.id.rePassword);
        final EditText coPassword = findViewById(R.id.coPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = id.getText().toString();
                String passwordText = password.getText().toString();
                String coPasswordText = coPassword.getText().toString();

                if (!idText.equals("")) {
                    Cursor cursor = db.rawQuery("select * from user where id = '" + idText + "'", null);
                    if (cursor.moveToNext()) {
                        Toast.makeText(Register.this, R.string.idExist, Toast.LENGTH_SHORT).show();
                        id.setText("");
                    }
                    else
                    {
                        if (passwordText.equals(coPasswordText)) {
                            if (!passwordText.equals("")) {
                                db.execSQL("insert into user values ('" + idText + "', '" + passwordText + "')");
                                Toast.makeText(Register.this, R.string.successRegister, Toast.LENGTH_SHORT).show();
                                id.setText("");
                                password.setText("");
                                coPassword.setText("");
                            } else
                                Toast.makeText(Register.this, R.string.passwordEmpty, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Register.this, R.string.consistency, Toast.LENGTH_SHORT).show();
                            password.setText("");
                            coPassword.setText("");
                        }
                    }
                    cursor.close();
                } else
                    Toast.makeText(Register.this, R.string.idEmpty, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
