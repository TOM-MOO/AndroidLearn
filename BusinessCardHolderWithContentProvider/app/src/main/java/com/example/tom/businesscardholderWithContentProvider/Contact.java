package com.example.tom.businesscardholderWithContentProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tom.businesscardholder.R;

import java.util.ArrayList;
import java.util.List;

public class Contact extends AppCompatActivity {

    ArrayAdapter<String> adapter;

    List<String> name = new ArrayList<>();

    List<String> phone = new ArrayList<>();

    private MyDatabaseHelper dbHelper;

    static boolean judge = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ListView contactsView = findViewById(R.id.contacts_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, name);
        contactsView.setAdapter(adapter);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, 1);
        }
        else {
            readContacts();
        }
        dbHelper = new MyDatabaseHelper(this, "BusinessCardHolder.db", null, 1);
        dbHelper.getWritableDatabase();
        Button importAll = findViewById(R.id.importall);
        importAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!judge) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    for (int i = 0; i < name.size(); i++) {
                        values.put("name", name.get(i));
                        values.put("phone", phone.get(i));
                        db.insert("Business_card", null, values);
                        values.clear();
                    }
                    Toast.makeText(Contact.this, "添加成功", Toast.LENGTH_SHORT).show();
                    judge = true;
                    Intent intent = new Intent(Contact.this, CheckData.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(Contact.this, "不可重复添加", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    name.add(displayName);
                    phone.add(number);
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
