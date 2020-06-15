package com.angle.hshb.customviewdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.angle.hshb.customviewdemo.R;

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
    private float mCircleRadius = 50;
    //  圆心
    private float mCirclePointX, mCirclePointY;
    //  可拖动的高度
    private int mDragHeight = 400;
    //  进度值
    private float mProgress;
    //  目标宽度
    private int mTargetWidth = 400;
    //  贝塞尔曲线的路径和画笔
    private Path mPath = new Path();
    private Paint mPathPaint;
    //  重心点最终高度 决定控制点的 Y 坐标
    private int mTargetGravityHeight = 10;
    //  角度变化 0 ～ 135度的区间
    private int mTargetAngle = 105;

    //  进度插值器
    private Interpolator mProgressInterpolator = new DecelerateInterpolator();
    //  路径插值器
    private Interpolator mTanentAngleInterpolator;

    private Drawable mContent = null;
    private int mContentMargin = 0;


    public TouchPullView(Context context) {
        this(context, null);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化方法
     */
    private void init(AttributeSet attrs) {

        final Context context = getContext();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TouchPullView, 0, 0);
        int color = array.getColor(R.styleable.TouchPullView_pColor, 0x20000000);
        mCircleRadius = array.getDimension(R.styleable.TouchPullView_pRadius, mCircleRadius);
        mDragHeight = array.getDimensionPixelOffset(R.styleable.TouchPullView_pDragHeight, mDragHeight);
        mTargetAngle = array.getInteger(R.styleable.TouchPullView_pTangentAngle, mTargetAngle);
        mTargetWidth = array.getDimensionPixelOffset(R.styleable.TouchPullView_pTargetWidth, mTargetWidth);
        mTargetGravityHeight = array.getDimensionPixelOffset(R.styleable.TouchPullView_pTargetGravityHeight, mTargetGravityHeight);
        mContent = array.getDrawable(R.styleable.TouchPullView_pCenterDrawable);
        mContentMargin = array.getDimensionPixelOffset(R.styleable.TouchPullView_pCenterDrawableMargin, 0);
        //  销毁
        array.recycle();


        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置抗锯齿
        p.setAntiAlias(true);
        //设置防抖动
        p.setDither(true);
        //设置为填充的方式
        p.setStyle(Paint.Style.FILL);
        //设置颜色
        p.setColor(color);
        mCirclePaint = p;


        //  初始化路径部分画笔
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置抗锯齿
        p.setAntiAlias(true);
        //设置防抖动
        p.setDither(true);
        //设置为填充的方式
        p.setStyle(Paint.Style.FILL);
        //设置颜色
        p.setColor(color);
        mPathPaint = p;


        //  切角路径插值器
        mTanentAngleInterpolator = PathInterpolatorCompat.create(
                (mCircleRadius * 2.0f) / mDragHeight,
                90.0f / mTargetAngle
        );


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int count = canvas.save();
        float tranX = (getWidth() - getValueByLine(getWidth(), mTargetWidth, mProgress)) / 2;
        //移动画布
        canvas.translate(tranX, 0);

        //  画贝塞尔曲线
        canvas.drawPath(mPath, mPathPaint);

        //  画圆
        canvas.drawCircle(mCirclePointX,
                mCirclePointY,
                mCircleRadius,
                mCirclePaint);

        Drawable drawable = mContent;
        if (drawable != null) {
            canvas.save();
            //  剪切矩形区域
            canvas.clipRect(drawable.getBounds());
            //  绘制Drawable
            drawable.draw(canvas);

            canvas.restore();
        }

        //画布复位
        canvas.restoreToCount(count);
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

        int iWidth = (int) (2 * mCircleRadius + getPaddingLeft() + getPaddingRight());
        int iHeight = (int) ((mDragHeight * mProgress + 0.5f) + getPaddingTop() + getPaddingBottom());

        int measureWidth, measureHeight;
        if (widthMode == MeasureSpec.EXACTLY) {
            //  确切的，准确的值
            measureWidth = width;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //  最多
            measureWidth = Math.min(width, iWidth);
        } else {
            measureWidth = iWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            //  确切的，准确的值
            measureHeight = width;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //  最多
            measureHeight = Math.min(height, iHeight);
        } else {
            measureHeight = iHeight;
        }

        //  设置测量的高度
        setMeasuredDimension(measureWidth, measureHeight);


    }

    /**
     * 当大小改变是触发
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //  当高度变化时进行路径更新
        updatePathLayout();
    }

    /***
     * 设置进度
     * @param progress
     */
    public void setProgress(float progress) {
        Log.d(TAG, " >>>>>> " + progress);
        mProgress = progress;

        //请求重新进行测量
        requestLayout();
    }


    /**
     * 更新路径等相关操作
     */
    private void updatePathLayout() {
        //  获取进度
        final float progress = mProgressInterpolator.getInterpolation(mProgress);
        //  获取可绘制区域的宽度和高度
        final float w = getValueByLine(getWidth(), mTargetWidth, mProgress);
        final float h = getValueByLine(0, mDragHeight, mProgress);
        //  x对称轴的参数，圆的圆心x坐标
        final float cPointX = w / 2.0f;
        //  圆的半径
        final float cRadius = mCircleRadius;
        //  圆的圆心Y的坐标
        final float cPointY = h - cRadius;
        //  控制点结束Y的值
        final float endControlY = mTargetGravityHeight;

        //  更新圆的坐标
        mCirclePointX = cPointX;
        mCirclePointY = cPointY;

        final Path path = mPath;
        //  路径重置
        path.reset();
        path.moveTo(0, 0);

        //  左边部分，结束点和控制点
        float lEndPointX, lEndPointY;
        float lControlPointX, lControlPointY;

        //  获取当前切线弧度
        float angle = mTargetAngle * mTanentAngleInterpolator.getInterpolation(progress);
        double radian = Math.toRadians(angle);
        float x = (float) (Math.sin(radian) * cRadius);
        float y = (float) (Math.cos(radian) * cRadius);

        lEndPointX = cPointX - x;
        lEndPointY = cPointY + y;

        //  控制点的Y坐标变化
        lControlPointY = getValueByLine(0, endControlY, progress);

        //  控制点于结束点之间 的高度
        float tHeight = lControlPointY - lControlPointY;
        //  控制点与X的坐标距离
        float tWidth = (float) (tHeight / Math.tan(radian));

        lControlPointX = lEndPointX - tWidth;

        //  贝塞尔曲线
        path.quadTo(lControlPointX, lControlPointY, lEndPointX, lEndPointY);
        //  链接到右边
        path.lineTo(cPointX + (cPointX - lEndPointX), lEndPointY);
        //  右边的贝塞尔曲线
        path.quadTo(cPointX + cPointX - lControlPointX, lControlPointY, w, 0);

        //  更新内容部分Drawble
        updateContentLayout(cPointX, cPointY, cRadius);
    }


    /***
     * 对内容部分进行测量，并进行绘制
     * @param cx    圆心X
     * @param cy    圆心Y
     * @param radius    半径
     */
    private void updateContentLayout(float cx, float cy, float radius) {
        Drawable drawable = mContent;
        if (drawable != null) {
            int margin = mContentMargin;
            int l = (int) (cx - radius + margin);
            int r = (int) (cx + radius - margin);
            int t = (int) (cy - radius + margin);
            int b = (int) (cy + radius - margin);
            drawable.setBounds(l, t, r, b);
        }

    }

    /**
     * 获取当前值
     *
     * @param start    起始值
     * @param end      结束值
     * @param progress 当前进度的值
     * @return
     */
    private float getValueByLine(float start, float end, float progress) {
        return start + (end - start) * progress;
    }


    private ValueAnimator valueAnimator;

    /**
     * 添加释放操作
     */
    public void release() {
        if (valueAnimator == null) {
            ValueAnimator animator = ValueAnimator.ofFloat(mProgress, 0f);
            animator.setInterpolator(new DecelerateInterpolator());//由块到慢的插值器
            animator.setDuration(400);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Object val = valueAnimator.getAnimatedValue();
                    if (val instanceof Float) {
                        setProgress((Float) val);
                    }
                }
            });
            valueAnimator = animator;
        } else {
            valueAnimator.cancel();
            valueAnimator.setFloatValues(mProgress, 0f);
        }
        valueAnimator.start();
    }


}
