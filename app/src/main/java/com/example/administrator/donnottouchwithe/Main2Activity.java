package com.example.administrator.donnottouchwithe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView tv_df_1;
    private TextView tv_gf;
    private SQLiteDatabase db;
    private int gf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        MySqlite ms = new MySqlite(this);
        db = ms.getWritableDatabase();

        Cursor cursor = db.query("fenshu",null,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            gf = cursor.getInt(cursor.getColumnIndex("fs"));
        }

        tv_df_1 = (TextView) findViewById(R.id.tv_df_1);
        tv_gf = (TextView) findViewById(R.id.tv_gf);

        Intent intent = getIntent();
        String df = intent.getStringExtra("df");
        tv_df_1.setText(df);
        tv_gf.setText(gf+"");
    }

    public void click(View v){
        Intent it = new Intent();
        it.setClass(this,com.example.administrator.donnottouchwithe.MainActivity.class);
        startActivity(it);
        finish();
    }
}
