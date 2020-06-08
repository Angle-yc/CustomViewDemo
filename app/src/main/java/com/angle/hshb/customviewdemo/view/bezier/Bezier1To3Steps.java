package com.angle.hshb.customviewdemo.view.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者    angle
 * 时间    2020-06-04 14:17
 * 文件    CustomViewDemo
 * 描述
 */
public class Bezier1To3Steps extends View {
    public Bezier1To3Steps(Context context) {
        this(context,null);
        init();
    }

    public Bezier1To3Steps(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        init();
    }

    public Bezier1To3Steps(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path mPath = new Path();
    private void init(){
        Paint paint = mPaint;
        //  抗锯齿
        paint.setAntiAlias(true);
        //  抗抖动
        paint.setDither(true);
        //  画笔样式
        paint.setStyle(Paint.Style.STROKE);//
        //  设置线的宽度像素点
        paint.setStrokeWidth(5);

        //一阶贝塞尔曲线
        Path path = mPath;
        path.moveTo(100,100);//将起始点移动至（x,y）
        path.lineTo(400,400);//从起始点到（x,y）画一条线

        //  二阶贝塞尔曲线
//        path.quadTo(500,0,700,300);
        //  相对于结束点的实现
        path.rQuadTo(200,-300,400,0);


        path.moveTo(400,800);
        //  三阶贝塞尔曲线
//         path.cubicTo(500,600,700,1000,800,800);
         path.rCubicTo(100,-200,300,400,400,0);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath,mPaint);

    }
}
