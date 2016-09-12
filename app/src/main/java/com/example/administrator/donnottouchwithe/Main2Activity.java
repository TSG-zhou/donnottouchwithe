package com.example.administrator.donnottouchwithe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView tv_df_1;
    private TextView tv_gf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv_df_1 = (TextView) findViewById(R.id.tv_df_1);
        tv_gf = (TextView) findViewById(R.id.tv_gf);
        //获取当前最高分
        Intent intent = getIntent();
        String df = intent.getStringExtra("df");
        String gf = intent.getStringExtra("gf");
        tv_df_1.setText(df);
        tv_gf.setText(gf);
    }

    public void click(View v){
        Intent it = new Intent();
        it.setClass(this,com.example.administrator.donnottouchwithe.MainActivity.class);
        startActivity(it);
        finish();
    }
}