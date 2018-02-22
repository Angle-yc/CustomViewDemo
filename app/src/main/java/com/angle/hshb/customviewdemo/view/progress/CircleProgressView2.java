package com.angle.hshb.customviewdemo.view.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.angle.hshb.customviewdemo.R;
import com.angle.hshb.customviewdemo.Utils;

/**
 * Created by angle
 * 2017/12/21.
 * 圆形进度条_自定义
 */

public class CircleProgressView2 extends View{

    private float progress = 100;// 显示的进度

    private String strProgress = "25";//显示的进度

    private int mLayoutSize = 100;//整个控件的尺寸(方形)

    public int mColor;//主要颜色

    public int mColorBackground;//背景颜色

    public int mProgressBackGroundColor;//进度条背景颜色

    private  Context mContext;

    private float now = 0;//当前的进度0-100之间

    private Paint mPaint;

    public CircleProgressView2(Context context) {
        this(context,null);
    }

    public CircleProgressView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CircleProgressView2);
        mColor = array.getColor(R.styleable.CircleProgressView2_progress_color, Color.YELLOW);
        mColorBackground = array.getColor(R.styleable.CircleProgressView2_backgroundColor,Color.RED);
        mProgressBackGroundColor = array.getColor(R.styleable.CircleProgressView2_progress_background_color,0x88d4b801);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);//设置空心
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int hightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        mLayoutSize = Math.min(widthSpecSize,hightSpecSize);

        if (mLayoutSize == 0){
            mLayoutSize = Math.max(widthSpecSize,hightSpecSize);
        }

        setMeasuredDimension(mLayoutSize,mLayoutSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //先画一个大圆
        int center = getWidth() / 2;
        float radius = center * 0.9f;
        mPaint.setColor(mProgressBackGroundColor);//透明的黄色
        canvas.drawCircle(center,center,radius,mPaint);

        //画一个稍小一点的和背景颜色一样的圆
        mPaint.setColor(mColorBackground);
        canvas.drawCircle(center,center,radius - Utils.dp2px(mContext,1f),mPaint);

        //再画一个颜色较浅一点的黄色的圆
        float gap = Utils.dp2px(mContext,18);
        RectF rectF = new RectF(gap,gap,mLayoutSize-gap,mLayoutSize-gap);
        mPaint.setColor(mProgressBackGroundColor);
        canvas.drawArc(rectF,0,360,true,mPaint);

        //画进度扇形
        int endR = (int) (360*(now / 100));
        mPaint.setColor(mColor);
        canvas.drawArc(rectF,-90,endR,true,mPaint);

        //画红圆
        mPaint.setColor(mColorBackground);
        mPaint.setStyle(Paint.Style.FILL);//设置空心
        radius = radius * 0.83f;//圆环半径,在原来基础上在缩小0.83
        canvas.drawCircle(center,center,radius,mPaint);

        //将外层经度分成15小格一个等份
        mPaint.setStrokeWidth(Utils.dp2px(mContext, 2));
        for (int r = 0; r < 360; r = r + 15) {
            canvas.drawLine(center,
                    center,
                    center - (float) ((center - gap) * Math.sin(Math.toRadians(r))),
                    center + (float) ((center - gap) * Math.cos(Math.toRadians(r))),
                    mPaint);
        }

        //外层再画一个浅浅的圆环
        mPaint.setColor(mProgressBackGroundColor);
        canvas.drawCircle(center, center, radius - Utils.dp2px(mContext, 2f), mPaint); //画出圆环
        mPaint.setColor(mColorBackground);
        canvas.drawCircle(center, center, radius - Utils.dp2px(mContext, 2.5f), mPaint); //画出圆环

        //画文字
        mPaint.setColor(mColor);
        String per = (int) now+"";
        if (TextUtils.isEmpty(strProgress)){//没有进度,中间画两条横线
            mPaint.setStrokeWidth(Utils.dp2px(mContext, 2));
            canvas.drawLine(center * 0.77f, center, center * 0.95f, center, mPaint);
            canvas.drawLine(center * 1.05f, center, center * 1.23f, center, mPaint);
        }else {
            mPaint.setTextSize(mLayoutSize/4);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setTextSize(mLayoutSize/12);
            paint.setColor(mColor);
            canvas.drawText(per,
                    center - 0.5f*(mPaint.measureText(per)),
                    center - 0.5f*(mPaint.ascent()+mPaint.descent()),
                    mPaint);
            canvas.drawText("分",
                    center + 0.5f * (mPaint.measureText(per) + paint.measureText("分")),
                    center - 0.05f*(paint.ascent()+paint.descent()),
                    paint);
        }

        //画外层圆上的小圆点
        center = getWidth() / 2;
        canvas.drawCircle(center + (float) ((center * 0.9f) * Math.sin(Math.toRadians(endR))),
                center - (float) ((center * 0.9f) * Math.cos(Math.toRadians(endR))), center * 0.03f, mPaint);

        //画指示器
        Path p = new Path();
        p.moveTo(center + (float) ((center * 0.81f) * Math.sin(Math.toRadians(endR))),
                center - (float) ((center * 0.81f) * Math.cos(Math.toRadians(endR))));//顶点
        p.lineTo(center + (float) ((center * 0.9f) * Math.sin(Math.toRadians(endR + 2.5))),
                center - (float) ((center * 0.9f) * Math.cos(Math.toRadians(endR + 2.5))));
        p.lineTo(center + (float) ((center * 0.9f) * Math.sin(Math.toRadians(endR - 2.5))),
                center - (float) ((center * 0.9f) * Math.cos(Math.toRadians(endR - 2.5))));
        p.close();
        canvas.drawPath(p, mPaint);

        //最后动起来
        if (now < progress - 1) {
            postInvalidate();
            now = now + 1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (now < progress) {
            now = (int) progress;
            postInvalidate();
        }

    }


    /**
     * 外部回调
     *
     * @param strprogress 显示调进度文字，如果是""，或者null了，则显示两条横线
     * @param progress    进度条调进度
     * @param isAnim      进度条是否需要动画
     */
    public void setProgress(String strprogress, float progress, boolean isAnim) {
        if (strprogress == null) {
            this.strProgress = "";
        } else {
            this.strProgress = strprogress;
        }
        this.now = 0;
        this.progress = progress;

        if (!isAnim) {
            now = progress;
        }
        postInvalidate();
    }
}
