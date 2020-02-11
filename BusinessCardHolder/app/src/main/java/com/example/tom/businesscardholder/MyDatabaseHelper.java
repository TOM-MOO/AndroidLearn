package com.example.tom.businesscardholder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Tom on 2017/10/17.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BUSINESS_CARD = "create table Business_card (" + "id integer primary key autoincrement, "
            + "name text, " + "sex text, " + "phone text, " + "address text, " + "email text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BUSINESS_CARD);
        Toast.makeText(mContext, "创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists Business_card");
        onCreate(db);
    }
}
