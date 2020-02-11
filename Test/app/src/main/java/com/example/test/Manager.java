package com.example.test;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Manager extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> user = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        DatabaseHelper dbHelper = new DatabaseHelper(this, "User.db", null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user);
        ListView listView = findViewById(R.id.managerListView);
        listView.setAdapter(adapter);

        Cursor cursor = db.rawQuery("select id from user order by id asc", null);
        while (cursor.moveToNext())
            user.add(cursor.getString(0));
        cursor.close();
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (!user.get(position).equals("administrator")) {
                    AlertDialog dialog = new AlertDialog.Builder(Manager.this)
                            .setTitle("Warning")
                            .setMessage("delete this user?")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.execSQL("delete from user where id = '" + user.get(position) + "'");
                                    Toast.makeText(Manager.this, R.string.delete, Toast.LENGTH_SHORT).show();
                                    Cursor cursor = db.rawQuery("select id from user order by id asc", null);
                                    user.clear();
                                    while (cursor.moveToNext())
                                        user.add(cursor.getString(0));
                                    cursor.close();
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    dialog.show();
                } else {
                    Intent intent = new Intent(Manager.this, ModifyPassword.class);
                    intent.putExtra("ID", "administrator");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("logout and back to the login page?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Manager.super.onBackPressed();
                        startActivity(new Intent(Manager.this, Login.class));
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

}
