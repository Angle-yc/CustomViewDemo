package com.angle.hshb.customviewdemo.view.pracdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者    angle
 * 时间    2020/12/1 9:09 AM
 * 文件    CustomViewDemo
 * 描述
 */
public class PracDrawView extends View {
    public PracDrawView(Context context) {
        this(context,null);
    }

    public PracDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PracDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //  画弧线
        drawArc(canvas);



    }


    //  画弧线
    private void drawArc(Canvas canvas) {
        Paint ovalPaint = new Paint();

        Paint arcPaint = new Paint();
        arcPaint.setStyle(Paint.Style.FILL);
        arcPaint.setColor(Color.GREEN);


        //  1.默认填充
        Path path = new Path();
        RectF rect = new RectF(100,100,200,200);
        //  指定角度
        path.arcTo(rect,0,180);
        //  绘制背景
        canvas.drawOval(rect,ovalPaint);
        //  绘制弧线
        canvas.drawPath(path,arcPaint);


        //  2.描边样式
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(10);
        path = new Path();
        rect = new RectF(300,100,400,200);
        //  指定角度
        path.arcTo(rect,0,90);
        //  绘制背景
        canvas.drawOval(rect,ovalPaint);
        //  绘制弧线
        canvas.drawPath(path,arcPaint);

    }







}
































