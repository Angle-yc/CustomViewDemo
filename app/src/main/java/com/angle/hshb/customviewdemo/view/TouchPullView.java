package com.angle.hshb.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 作者    angle
 * 时间    2020-06-02 13:57
 * 文件    CustomViewDemo
 * 描述
 */
public class TouchPullView extends View {
    public static final String TAG = "TouchPullView";
    //  圆的画笔
    private Paint mCirclePaint;
    //  圆的半径
    private int mCircleRadius = 200;
    //  圆心
    private int mCirclePointX,mCirclePointY;

    //  可拖动的高度
    private int mDragHeight = 800;

    //  进度值
    private float mProgress;

    public TouchPullView(Context context) {
        this(context,null);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置抗锯齿
        p.setAntiAlias(true);
        //设置防抖动
        p.setDither(true);
        //设置为填充的方式
        p.setStyle(Paint.Style.FILL);
        //设置颜色
        p.setColor(Color.BLACK);

        mCirclePaint = p;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCirclePointX,mCirclePointY,mCircleRadius,mCirclePaint);
    }

    /***
     * 当进行测量时触发
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //宽的意图
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int iWidth = 2*mCircleRadius+getPaddingLeft()+getPaddingRight();
        int iHeight = (int) ((mDragHeight * mProgress +0.5f)+getPaddingTop()+getPaddingBottom());

        int measureWidth,measureHeight;
        if (widthMode == MeasureSpec.EXACTLY){
            //  确切的，准确的值
            measureWidth = width;
        }else if (widthMode == MeasureSpec.AT_MOST){
            //  最多
            measureWidth = Math.min(width,iWidth);
        }else {
            measureWidth = iWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            //  确切的，准确的值
            measureHeight = width;
        }else if (heightMode == MeasureSpec.AT_MOST){
            //  最多
            measureHeight = Math.min(height,iHeight);
        }else {
            measureHeight = iHeight;
        }

        //  设置测量的高度
        setMeasuredDimension(measureWidth,measureHeight);


    }

    /**
     * 当大小改变是触发
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCirclePointX = getWidth()>>1;
        mCirclePointY = getHeight()>>1;
    }

    /***
     * 设置进度
     * @param progress
     */
    public void setProgress(float progress){
        Log.d(TAG," >>>>>> "+progress);
        mProgress = progress;

        //请求重新进行测量
        requestLayout();
    }

}
