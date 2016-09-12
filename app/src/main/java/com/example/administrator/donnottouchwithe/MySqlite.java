package com.example.administrator.donnottouchwithe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/12.
 */
public class MySqlite extends SQLiteOpenHelper {
    public MySqlite(Context context) {
        super(context,"dont.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table fenshu(_id integer primary key autoincrement,name char(10),fs integer(10))");
        db.execSQL("insert into fenshu (name,fs)values(?,?)",new Object[]{"null",0});
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
