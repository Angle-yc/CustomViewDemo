package com.angle.hshb.customviewdemo.view.weiboline;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/30.
 */

public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
    public static final String TAG="test_tag";
    private Context context;
    private int fixLeftDis;
    private ArrayList<TextView>textViews;
    private ViewPagerTitle viewPagerTitle;
    private DynamicLine dynamicLine;

    private ViewPager viewPager;
    private int pagerCount;
    private int screenWidth;
    private int lineWith;
    private int everyLength;
    private int lastPosition;
    private int dis;
    private int[] location=new int[2];


    public MyOnPageChangeListener(Context context,ViewPager viewPager,DynamicLine dynamicLine,ViewPagerTitle pagerTitle,int allLeng,int margin,int fixLeftDis) {
        this.context=context;
        this.viewPager=viewPager;
        this.dynamicLine=dynamicLine;
        this.viewPagerTitle=pagerTitle;
        textViews=pagerTitle.getTextView();
        pagerCount=textViews.size();
        screenWidth=Tool.getScreenWidth(context);

        lineWith= (int) Tool.getTextViewLength(textViews.get(0));

        everyLength=allLeng/pagerCount;
        dis=margin;
        this.fixLeftDis=fixLeftDis;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (lastPosition>position){
            dynamicLine.updateView((position+positionOffset)*everyLength+dis+fixLeftDis,(lastPosition+1)*everyLength-dis);
        }else {
            if (positionOffset>0.5f){
                positionOffset=0.5f;
            }
            dynamicLine.updateView(lastPosition*everyLength+dis+fixLeftDis,(position+positionOffset*2)*everyLength+dis+lineWith);
        }
    }

    @Override
    public void onPageSelected(int position) {
        viewPagerTitle.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        boolean scrollRight;
        if (state==ViewPager.SCROLL_STATE_SETTLING){
            scrollRight=lastPosition<viewPager.getCurrentItem();
            lastPosition=viewPager.getCurrentItem();
            if (lastPosition+1<textViews.size()&&lastPosition-1>=0){
                textViews.get(scrollRight?lastPosition+1:lastPosition-1).getLocationOnScreen(location);
                if (location[0]>screenWidth){
                    viewPagerTitle.smoothScrollBy(screenWidth/2,0);
                }else {
                    viewPagerTitle.smoothScrollBy(-screenWidth/2,0);
                }
            }
        }
    }
}
