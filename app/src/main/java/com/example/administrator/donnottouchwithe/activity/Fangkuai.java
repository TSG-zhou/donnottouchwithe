package com.example.administrator.donnottouchwithe.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.example.administrator.donnottouchwithe.activity.MainActivity;

/**
 * Created by Administrator on 2016/9/9.
 */
public class Fangkuai extends View{

    int canclick = 2;//0代表可以点击；1代表不能点击并且是黑块；2代表不能点击并且是白块
    int i = 2;//0代表未点击，1代表已点击，2代表白块


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics metrics = new DisplayMetrics();
        getDisplay().getMetrics(metrics);
        int layoutwidth = metrics.widthPixels/3;
        int layoutheight = (metrics.heightPixels-30-6)/4;
        setMeasuredDimension(layoutwidth, layoutheight);
    }

    public void setI(int i) {
        this.i = i;
        invalidate();
    }

    public int getI() {
        return i;
    }

    public void setCanclick(int canclick) {
        this.canclick = canclick;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        String paintcolor;
        switch (i){
            case 0://未点击颜色
                paintcolor = "#EEE4DA";
                break;
            case 1://点击之后的颜色
                paintcolor = "#CCC0B3";
                break;
            default://默认颜色
                paintcolor = "#EA7821";
                break;
        }
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(paintcolor));
        canvas.drawRect(1, 1, getWidth(), getHeight()-1, paint);
    }


    public Fangkuai(Context context) {
        super(context,null);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canclick == 0) {
                    if (i == 0) {
                        MainActivity.handler.sendEmptyMessage(0);
                        setI(1);
                    } else if (i == 1) {
                        return;
                    } else {
                        MainActivity.handler.sendEmptyMessage(2);
                    }
                }else if (canclick == 3){
                    if (i == 0) {
                        setI(1);
                        JindianActivity.handler.sendEmptyMessage(0);
                    }
                }
            }
        });
    }

    public Fangkuai(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public Fangkuai(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
}
