package com.example.administrator.donnottouchwithe.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.donnottouchwithe.R;
import com.example.administrator.donnottouchwithe.sql.MySqlite;

import java.util.Timer;
import java.util.TimerTask;

public class JindianActivity extends AppCompatActivity {

    private static TextView tv_jd_time;
    private LinearLayout ll_0;
    private static int lllenth = 4;
    private LinearLayout[] ll = new LinearLayout[lllenth];
    private static int fklenth = 3;
    static int js = 60;
    private static Fangkuai[][] fk = new Fangkuai[lllenth][fklenth];
    private Timer timer1 = new Timer();
    private Timer timer2 = new Timer();
    static int sjs = 0;
    static double sjms = 0;
    int sjsjl;
    double sjmsjl;

    private SQLiteDatabase db;

    private static boolean flag = false;
    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                flag = true;
                js--;
//                System.out.println(js+"-----");
                for (int i = 3; i > 0; i--) {
                    for (int j = 0; j < fklenth; j++) {
                        fk[i][j].setI(fk[i - 1][j].getI());
                    }
                }
                if (js <= 3) {
                    for (int j = 0; j < fklenth; j++) {
                        fk[0][j].setI(1);
                    }
                } else {
                    //下移后重新设置第一行的属性
                    for (int j = 0; j < fklenth; j++) {
                        fk[0][j].setI(2);
                    }
                    fk[0][(int) (Math.random() * 3)].setI(0);
                    //将最后一行的白块设置为可点击状态
                    for (int j = 0; j < fklenth; j++) {
                        if (fk[3][j].getI() == 0) {
                            fk[3][j].setCanclick(3);
                        }
                    }
                }
            } else if (msg.what == 3) {
//                System.out.println(sjs+"'"+sjms+"''");
                tv_jd_time.setText(sjs + "'" + sjms + "''");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jindian);

        MySqlite ms = new MySqlite(this);
        db = ms.getWritableDatabase();
        //查询出最高分，用于与当前分数比较
        Cursor cursor = db.query("miao", null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
//            gf = cursor.getInt(cursor.getColumnIndex("fs"));
            sjsjl = cursor.getInt(cursor.getColumnIndex("sjs"));
            sjmsjl = cursor.getDouble(cursor.getColumnIndex("sjms"));
        }

        tv_jd_time = (TextView) findViewById(R.id.tv_jd_time);
        ll_0 = (LinearLayout) findViewById(R.id.ll_1);
        //将Linnerlayout绘制到布局文件中
        for (int i = 0; i < lllenth; i++) {
            ll[i] = new LinearLayout(this);
            ll[i].setOrientation(LinearLayout.HORIZONTAL);
            ll_0.addView(ll[i]);
            //将方块绘制到布局文件中
            for (int j = 0; j < fklenth; j++) {
                fk[i][j] = new Fangkuai(this);
                ll[i].addView(fk[i][j]);
            }
            //随机设置每一行的白格
            int randnum = (int) (Math.random() * 3);
            fk[i][randnum].setI(0);
        }

        //将最后一行的白块设置为可点击状态
        for (int j = 0; j < fklenth; j++) {
            if (fk[3][j].getI() == 0) {
                fk[3][j].setCanclick(3);
            }
        }
        timer1.schedule(timerTask, 0, 100);
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (flag) {
                timer2.schedule(task, 0, 10);
                timer1.cancel();
            }
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (js == 0) {
                if (sjs < sjsjl & sjms < sjmsjl) {
                    db.execSQL("update miao set sjs = ?, sjms = ? where _id = ?", new Object[]{sjs, sjms, 1});
                    sjsjl = sjs;
                    sjmsjl = sjms;
                }
                Intent it = new Intent(JindianActivity.this, Main2Activity.class);
                it.putExtra("stat", 2);
                it.putExtra("sjs", sjs);
                it.putExtra("sjms", sjms);
                it.putExtra("sjsjl", sjsjl);
                it.putExtra("sjmsjl", sjmsjl);
                System.out.println(sjsjl + "'---" + sjmsjl + "''---");

                startActivity(it);
                cancel();
                finish();
            }
            sjms = sjms + (7 / 3);
            if (sjms >= 100) {
                sjms = 0;
                sjs++;
            }
            handler.sendEmptyMessage(3);
        }
    };

    public void cancel() {
        if (timer2 != null) {
            timer2.cancel();
        }
        sjs = 0;
        sjms = 0;
        js = 60;
        //页面销毁时，将游戏开始状态设置为false
        flag = false;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,HomeActivity.class));
        cancel();
    }

    @Override
    protected void onDestroy() {
        cancel();
        super.onDestroy();
    }
}
