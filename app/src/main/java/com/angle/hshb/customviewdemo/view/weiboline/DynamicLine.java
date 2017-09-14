package com.angle.hshb.customviewdemo.view.weiboline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/8/30.
 */

public class DynamicLine extends View {
    private float startX,stopX;
    private Paint paint;
    private RectF rectF=new RectF(startX,0,stopX,0);

    public DynamicLine(Context context) {
        super(context,null);
    }

    public DynamicLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public DynamicLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint=new Paint();
        paint.setAntiAlias(true);
        /**
         *  Paint.Style.STROKE 只绘制图形轮廓（描边）
            Paint.Style.FILL 只绘制图形内容
            Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容
         */
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        float widthPixels =Tool.getScreenWidth(getContext());
        paint.setShader(new LinearGradient(0,100, widthPixels,100,
                Color.parseColor("#ffc125"),Color.parseColor("#ff4500"), Shader.TileMode.MIRROR));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec=MeasureSpec.makeMeasureSpec(20,MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rectF=new RectF(startX,0,stopX,10);
        canvas.drawRoundRect(rectF,5,5,paint);
    }

    /**
     * 重新设置大小
     * @param startX
     * @param stopX
     */
    public void updateView(float startX,float stopX){
        this.startX=startX;
        this.stopX=stopX;
        invalidate();
    }
}
