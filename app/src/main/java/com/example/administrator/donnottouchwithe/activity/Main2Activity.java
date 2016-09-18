package com.example.administrator.donnottouchwithe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.donnottouchwithe.R;

public class Main2Activity extends AppCompatActivity {

    private TextView tv_df_1;
    private TextView tv_gf;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv_df_1 = (TextView) findViewById(R.id.tv_df_1);
        tv_gf = (TextView) findViewById(R.id.tv_gf);
        //获取当前最高分
        Intent intent = getIntent();
        state = intent.getIntExtra("stat",0);
        if (state == 1) {
            String df = intent.getStringExtra("df");
            String gf = intent.getStringExtra("gf");

            tv_df_1.setText(df);
            tv_gf.setText(gf);
        }else if (state ==2){
            int sjs = intent.getIntExtra("sjs",0);
            double sjms = intent.getDoubleExtra("sjms",0);

            int sjsjl = intent.getIntExtra("sjsjl",0);
            double sjmsjl = intent.getDoubleExtra("sjmsjl",0);

            tv_df_1.setText(sjs+"'"+sjms+"''");
            tv_gf.setText(sjsjl+"'"+sjmsjl+"''");
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }

    public void click(View v){
        if (state == 1){
            startActivity(new Intent(this,MainActivity.class));
        }else {
            startActivity(new Intent(this,JindianActivity.class));
        }
        finish();
    }
}