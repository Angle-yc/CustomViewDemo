package com.angle.hshb.customviewdemo.view.pracdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 作者    angle
 * 时间    2020/12/1 11:26 AM
 * 文件    CustomViewDemo
 * 描述
 */
public class RadarView extends View {
    public static final String TAG = "RadarView";

    //网格最大半径
    private float radius;

    //中心X
    private int centerX;

    //中心Y
    private int centerY;

    //雷达区画笔
    private Paint radarPaint;

    //数据个数
    private int count = 6;

    //数据区画笔
    private Paint valuePaint;

    // 1度 = 1 * PI / 180   360度=2*PI
    // 那么我们每旋转一次的角度为 2 * PI / 内角个数
    // 中心与相邻两个内角相连的夹角角度
    float angle = (float) (2 * Math.PI / count);


    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //  初始化画笔
    private void init() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);//颜色
        paint.setAntiAlias(true);//抗锯齿
        paint.setStrokeWidth(1);//画笔粗细
        paint.setStyle(Paint.Style.STROKE);//描边
        radarPaint = paint;//雷达画笔

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);//填充
        valuePaint = paint;//数据画笔
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(w,h) / 2 * 0.9f;
        centerX = w / 2;
        centerY = h / 2;

        //  数据一旦发生改变，重新绘制
        postInvalidate();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        //  绘制多边形
        drawPolygon(canvas);

        //  绘制边
        drawLines(canvas);

        //  绘制结果
        drawData(canvas);
    }

    // 数据
    private double[] data = {2, 5, 1, 6, 4, 5};

    // 最大值
    private float maxValue = 6;

    //  绘制结果
    private void drawData(Canvas canvas) {
        Path path = new Path();

        for (int i = 0; i < count; i++) {
            double percent = data[i] / maxValue;
            float x = (float) (centerX + radius * Math.cos(angle * i) * percent);
            float y = (float) (centerY + radius * Math.sin(angle * i) * percent);
            if (i == 0){
                path.moveTo(x,centerY);
            }else {
                path.lineTo(x,y);
            }
            valuePaint.setAlpha(255);
            //  绘制坐标点
            canvas.drawCircle(x,y,10,valuePaint);
        }

        //  绘制填充区域
        valuePaint.setAlpha(10);
        valuePaint.setColor(Color.YELLOW);
        valuePaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path,valuePaint);
    }

    //  绘制边
    private void drawLines(Canvas canvas) {
        Path path = new Path();

        for (int i = 0; i < count; i++) {
            path.reset();

            path.moveTo(centerX,centerY);

            float x = (float) (centerX + radius * Math.cos(angle * i));

            float y = (float) (centerY + radius * Math.sin(angle * i));

            path.lineTo(x,y);

            canvas.drawPath(path,radarPaint);
        }
    }

    //  绘制多边形
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        //  每个蛛网之间的距离
        float gap = radius / (count -1);

        for (int i = 0; i < count; i++) {
            //  当前半径
            float curR= gap * i;
            //  重置path
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0){
                    path.moveTo(centerX + curR,centerY);
                }else {
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));
                    path.lineTo(x,y);
                }
            }
            path.close();
            canvas.drawPath(path,radarPaint);
        }
    }
}
