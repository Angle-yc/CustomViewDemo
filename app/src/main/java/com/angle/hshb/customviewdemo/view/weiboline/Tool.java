package com.angle.hshb.customviewdemo.view.weiboline;

import android.content.Context;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/30.
 */

public class Tool {
    public static float getTextViewLength(TextView textView){
        TextPaint paint=textView.getPaint();
        return paint.measureText(textView.getText().toString());
    }

    public static float getTextViewLength(TextView textView,float textsize){
        TextPaint paint=textView.getPaint();
        paint.setTextSize(textsize);
        return paint.measureText(textView.getText().toString());
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm =new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
