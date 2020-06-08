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
 * 时间    2020-06-08 15:01
 * 文件    CustomViewDemo
 * 描述
 */
public class BezierView extends View {
    public BezierView(Context context) {
        this(context,null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Path mBezier = new Path();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private void init() {
        //  初始化画笔参数
        Paint paint = mPaint;
        //  抗锯齿
        paint.setAntiAlias(true);
        //  抗抖动
        paint.setDither(true);
        //  画笔样式
        paint.setStyle(Paint.Style.STROKE);
        //  设置线的宽度像素点
        paint.setStrokeWidth(10);

        new Thread(){
            @Override
            public void run() {
                initBezier();
            }
        }.start();
    }

    /***
     *  初始化贝塞尔曲线
     */
    private void initBezier(){
        // 坐标
        float[] xPoints = new float[]{0,300,200,500,700,900,1400,100,-500,1000};
        float[] yPoints = new float[]{0,300,700,500,1200,500,200,-800,-800,0};

        Path path = mBezier;

        int fps = 100;
        for (int i = 0; i <= fps; i++) {
            float progress = 1 / (float)fps;
            float x = calculateBezier(progress,xPoints);
            float y = calculateBezier(progress,yPoints);

            //  使用链接的方式，当x y 变化足够小的时候就是平滑的曲线
            path.lineTo(x,y);

            //  刷新界面
            postInvalidate();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        float progress = 0.2f;//t

        calculateBezier(progress,xPoints);
    }


    /***
     * 计算某一时刻的贝塞尔所处的值（x 或 y）
     * @param t   时间  （ 0 ～ 1 ）
     * @param values  贝塞尔点集合 （x 或 y）
     * @return  当前 t时刻贝塞尔所处的点
     */
    private float calculateBezier(float t, float... values){
        //  采用双重循环
        final  int len = values.length;
        for (int i = len -1 ; i > 0 ; i--){
            // 外层
            for (int j = 0; j < i ; j++){
                //  计算
                values[j] = values[j] + (values[j + 1] - values[j]) * t;
            }
        }
        //运算时结果保存在第一位，所以返回第一位
        return values[0];
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mBezier,mPaint);

    }
}
