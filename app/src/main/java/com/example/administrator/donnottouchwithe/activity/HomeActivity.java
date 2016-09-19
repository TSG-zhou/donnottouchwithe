package com.example.administrator.donnottouchwithe.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.donnottouchwithe.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void jingdian(View v){
        startActivity(new Intent(this,JindianActivity.class));
        finish();
    }
    public void can(View v){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    public void out(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出");
        builder.setMessage("确定退出");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出");
        builder.setMessage("确定退出");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }
}
