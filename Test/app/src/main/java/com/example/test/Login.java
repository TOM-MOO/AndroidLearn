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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseHelper dbHelper = new DatabaseHelper(this, "User.db", null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor check = db.rawQuery("select * from user where id = 'administrator'", null);
        if (!check.moveToNext())
            db.execSQL("insert into user values ('administrator', 'panxingyudeqq123')");
        check.close();

        final EditText password = findViewById(R.id.password);
        final EditText id = findViewById(R.id.id);
        Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordText = password.getText().toString();
                String idText = id.getText().toString();
                if (!idText.equals("")) {
                    if (!passwordText.equals("")) {
                        Cursor cursor = db.rawQuery("select password from user where id = '" + idText + "'", null);
                        if (cursor.moveToNext()) {
                            String truePassword = cursor.getString(0);
                            if (truePassword.equals(passwordText)) {
                                cursor.close();
                                db.close();
                                Intent intent;
                                if (idText.equals("administrator"))
                                    intent = new Intent(Login.this, Manager.class);
                                else
                                    intent = new Intent(Login.this, User.class);
                                intent.putExtra("ID", idText);
                                password.setText("");
                                id.setText("");
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, R.string.passwordWrong, Toast.LENGTH_SHORT).show();
                                password.setText("");
                            }
                        } else {
                            Toast.makeText(Login.this, R.string.idWrong, Toast.LENGTH_SHORT).show();
                            id.setText("");
                        }

                    } else
                        Toast.makeText(Login.this, R.string.passwordEmpty, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(Login.this, R.string.idEmpty, Toast.LENGTH_SHORT).show();
            }
        });

        Button register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private long start = 0;

    @Override
    public void onBackPressed() {
        long end = System.currentTimeMillis();
        if (end - start > 2000) {
            Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();
            start = end;
        } else
            super.onBackPressed();
    }
}
