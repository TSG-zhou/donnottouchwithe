package com.example.administrator.donnottouchwithe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int lllenth = 4;//Linnerlyout的个数
    private static final int fklenth = 3;//每一行白块的个数
    private static int df = 0;
    private int time = 30;
    private static boolean flag = false;
    private int gf;

    private LinearLayout ll_0;
    private LinearLayout[] ll = new LinearLayout[lllenth];
    private static Fangkuai[][] fk = new Fangkuai[lllenth][fklenth];
    private static TextView tv_df;
    private TextView tv_time;
    private Timer timer1 = new Timer();
    private Timer timer2 = new Timer();

    static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            if (msg.what==0){
                flag = true;//判断是否可以开始计时，即玩家第一次点击
                df = df + 1;
                tv_df.setText(df+"");//将得分实时显示到组件上
                //将所有组件下移实现变化效果
                for (int i = 3;i > 0;i--){
                    for (int j = 0;j<fklenth;j++){
                        fk[i][j].setI(fk[i-1][j].getI());
                    }
                }
                //下移后重新设置第一行的属性
                for (int j = 0;j<fklenth;j++){
                    fk[0][j].setI(2);
                }
                fk[0][(int) (Math.random()*3)].setI(0);
                //将最后一行的白块设置为可点击状态
                for (int j = 0; j < fklenth; j++) {
                    if (fk[3][j].getI() == 0){
                        fk[3][j].setCanclick(0);
                    }
                }
            }else if (msg.what==2){
                if (df>0) {
                    df = df - 1;
                    tv_df.setText(df + "");
                }
            }
        }
    };
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //应用创建时，创建数据库
        MySqlite ms = new MySqlite(this);
        db = ms.getWritableDatabase();
        //查询出最高分，用于与当前分数比较
        Cursor cursor = db.query("fenshu",null,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            gf = cursor.getInt(cursor.getColumnIndex("fs"));
        }

        tv_df = (TextView) findViewById(R.id.tv_df);
        tv_time = (TextView) findViewById(R.id.tv_time);
        ll_0 = (LinearLayout) findViewById(R.id.ll);
        //将Linnerlayout绘制到布局文件中
        for (int i = 0;i<lllenth;i++){
            ll[i] = new LinearLayout(this);
            ll[i].setOrientation(LinearLayout.HORIZONTAL);
            ll_0.addView(ll[i]);
        }
        //将方块绘制到布局文件中
        ll_0 = (LinearLayout) findViewById(R.id.ll);
        for (int i = 0; i< lllenth;i++){
            for (int j =0; j< fklenth; j++){
                fk[i][j] = new Fangkuai(this);
                ll[i].addView(fk[i][j]);
            }
            //随机设置每一行的白格
            int randnum = (int) (Math.random()*3);
            fk[i][randnum].setI(0);
        }
        //将最后一行的白块设置为可点击状态
        for (int j = 0; j < fklenth; j++) {
            if (fk[3][j].getI() == 0){
                fk[3][j].setCanclick(0);
            }
        }
        //设置计时器监听flag（即游戏开始状态）
        timer1.schedule(timerTask,0,100);
    }

    //计时器任务，flag为true后，开起倒计时，并将该计时器废弃
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (flag){
                timer2.schedule(task,1000,1000);
                timer1.cancel();
            }
        }
    };
    //计时任务，为游戏的倒计时计时
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (time==0){
                        timer2.cancel();
                        if (df > gf) {
                            //将高分与该局得分相比，如果高则添加到数据库中
                            db.execSQL("update fenshu set fs = ? where _id = ?", new Object[]{df, 1});
                        }
                        //显性跳转到另外一个布局文件中
                        Intent it = new Intent();
                        it.setClass(MainActivity.this,com.example.administrator.donnottouchwithe.Main2Activity.class);
                        //将该局得分传递过去
                        it.putExtra("df",df+"");
                        it.putExtra("gf",gf+"");
                        startActivity(it);
                        finish();
                    }
                    time = (time - 1)<0?0:(time-1);
                    tv_time.setText(time+"");
                }
            });
        }
    };

    @Override
    protected void onStop() {
        if (timer2!=null){
            timer2.cancel();
        }
        //页面销毁时，将静态得分设置为0；
        df = 0;
        //页面销毁时，将游戏开始状态设置为false
        flag = false;
        if (db!=null){
            db.close();
        }
        super.onStop();
    }
}