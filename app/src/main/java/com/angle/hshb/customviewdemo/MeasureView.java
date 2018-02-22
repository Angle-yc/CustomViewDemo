package com.angle.hshb.customviewdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by angle
 * 2017/12/4.
 */

public class MeasureView extends View {
    public static final String TAG = "MeasureView";
    public MeasureView(Context context) {
        super(context);
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = getSize(widthMode,widthMeasureSpec);
        int height = getSize(heightMode,heightMeasureSpec);

        Log.i(TAG,"测量模式："+"\n"+
        "模式：宽"+logMode(widthMode)+"====大小，"+getSize(widthMode,widthMeasureSpec)+"\n"+
        "模式：高"+logMode(heightMode)+"====大小，"+getSize(heightMode,heightMeasureSpec));
        setMeasuredDimension(width,height);
    }

    private String logMode(int mode){
        switch (mode){
            case MeasureSpec.AT_MOST:
                return "AT_MOST";
            case MeasureSpec.EXACTLY:
                return "EXACTLY";
            case MeasureSpec.UNSPECIFIED:
                return "UNSPECIFIED";
        }
        return "";
    }

    private int getSize(int mode,int sizeMeasureSpec){
        int specSize = 0;
        switch (mode){
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                specSize = MeasureSpec.getSize(sizeMeasureSpec);
                break;
            case MeasureSpec.UNSPECIFIED:
                specSize = 300;
                break;
        }
        return specSize;
    }
}
