package com.angle.hshb.customviewdemo.view.weiboline;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/30.
 */

public class ViewPagerTitle extends HorizontalScrollView {
    private String[] titles;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private OnTextViewClick textViewClick;
    private DynamicLine dynamicLine;
    private ViewPager viewPager;
    private MyOnPageChangeListener onPageChangeListener;
    private int margin;
    private LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams textviewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private float defaultTextSize = 18;
    private float selectedTextSize = 22;
    private int defaultTextColor = Color.GRAY;
    private int selectedTextColor = Color.BLACK;
    private int allTextViewLength;
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            setCurrentItem((int) v.getTag());
            viewPager.setCurrentItem((int) v.getTag());
            if (textViewClick != null) {
                textViewClick.textViewClick((TextView) v, (int) v.getTag());
            }
        }
    };


    public ViewPagerTitle(Context context) {
        super(context, null);
    }

    public ViewPagerTitle(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public ViewPagerTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void initData(String[] titles, ViewPager viewPager, int defaultIndex) {
        this.titles = titles;
        this.viewPager = viewPager;
        createDynamicLine();
        createTextViews(titles);

        int fixLeftDis = getFixLeftDis();
        onPageChangeListener = new MyOnPageChangeListener(getContext(), viewPager, dynamicLine, this, allTextViewLength, margin, fixLeftDis);
        setDefaultIndex(defaultIndex);
    }

    private void createTextViews(String[] titles) {
        LinearLayout contentL1 = new LinearLayout(getContext());
        contentL1.setBackgroundColor(Color.parseColor("#fffacd"));
        contentL1.setLayoutParams(contentParams);
        contentL1.setOrientation(LinearLayout.VERTICAL);
        addView(contentL1);

        LinearLayout textViewL1 = new LinearLayout(getContext());
        textViewL1.setLayoutParams(contentParams);
        textViewL1.setOrientation(LinearLayout.HORIZONTAL);

        margin = getTextViewMargins(titles);

        textviewParams.setMargins(margin, 0, margin, 0);

        for (int i = 0; i < titles.length; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(titles[i]);
            textView.setTextColor(Color.GRAY);
            textView.setTextSize(defaultTextSize);
            textView.setLayoutParams(textviewParams);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setOnClickListener(onClickListener);
            textView.setTag(i);
            textViews.add(textView);
            textViewL1.addView(textView);
        }
        contentL1.addView(textViewL1);
        contentL1.addView(dynamicLine);
    }

    public ArrayList<TextView> getTextView(){
        return textViews;
    }

    private int getTextViewMargins(String[] titles) {
        int defaultMargin = 30;
        float countLength = 0;
        TextView textView = new TextView(getContext());
        textView.setTextSize(defaultTextSize);
        TextPaint paint = textView.getPaint();
        for (int i = 0; i < titles.length; i++) {
            countLength += defaultMargin + paint.measureText(titles[i]) + defaultMargin;
        }

        int screenWith = Tool.getScreenWidth(getContext());
        if (countLength <= screenWith) {
            allTextViewLength = screenWith;
            return (screenWith / titles.length - (int) paint.measureText(titles[0])) / 2;
        } else {
            allTextViewLength = (int) countLength;
            return defaultMargin;
        }
    }

    private void createDynamicLine() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dynamicLine = new DynamicLine(getContext());
        dynamicLine.setLayoutParams(params);
    }

    public int getFixLeftDis() {
        TextView textView = new TextView(getContext());
        textView.setTextSize(defaultTextSize);
        textView.setText(titles[0]);
        float defaultTextSize = Tool.getTextViewLength(textView, this.defaultTextSize);
        textView.setTextSize(selectedTextSize);
        float selectTextSize = Tool.getTextViewLength(textView, this.selectedTextSize);
        return (int) (selectTextSize - defaultTextSize) / 2;
    }

    public void setDefaultIndex(int defaultIndex) {
        setCurrentItem(defaultIndex);
    }

    public void setCurrentItem(int index) {
        for (int i = 0; i < textViews.size(); i++) {
            if (i == index) {
                textViews.get(i).setTextColor(selectedTextColor);
                textViews.get(i).setTextSize(selectedTextSize);
            } else {
                textViews.get(i).setTextColor(defaultTextColor);
                textViews.get(i).setTextSize(defaultTextSize);
            }
        }
    }

    /**
     * textview点击监听
     */
    public interface OnTextViewClick {
        void textViewClick(TextView textView, int index);
    }

    public void setOnTextViewClickListener(OnTextViewClick textViewClick) {
        this.textViewClick = textViewClick;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        viewPager.removeOnPageChangeListener(onPageChangeListener);
    }
}
