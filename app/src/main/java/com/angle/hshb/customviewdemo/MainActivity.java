package com.angle.hshb.customviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.angle.hshb.customviewdemo.fragment.EightFragment;
import com.angle.hshb.customviewdemo.fragment.FirstFragment;
import com.angle.hshb.customviewdemo.fragment.FiveFragment;
import com.angle.hshb.customviewdemo.fragment.FourFragment;
import com.angle.hshb.customviewdemo.fragment.SecondFragment;
import com.angle.hshb.customviewdemo.fragment.SevenFragment;
import com.angle.hshb.customviewdemo.fragment.SixFragment;
import com.angle.hshb.customviewdemo.fragment.ThreeFragment;
import com.angle.hshb.customviewdemo.view.progress.CircleProgressView2;
import com.angle.hshb.customviewdemo.view.weiboline.ViewPagerTitle;
import com.chaychan.library.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CircleProgressView2 progressView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressView = (CircleProgressView2) findViewById(R.id.progress);
        init();
    }

    private void init() {
        ArrayList<Fragment>datas=new ArrayList<>();
        ViewPagerTitle viewPagerTitle= (ViewPagerTitle) findViewById(R.id.title);
        ViewPager viewPager= (ViewPager) findViewById(R.id.view_pager);
        BottomBarLayout barLayout = (BottomBarLayout) findViewById(R.id.bbl);

        viewPagerTitle.initData(new String[]{"关注","推荐","视频","直播","图片","段子","精华","热门"},viewPager,0);

        datas.add(new FirstFragment());
        datas.add(new SecondFragment());
        datas.add(new ThreeFragment());
        datas.add(new FourFragment());
        datas.add(new FiveFragment());
        datas.add(new SixFragment());
        datas.add(new SevenFragment());
        datas.add(new EightFragment());

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),datas));
        barLayout.setViewPager(viewPager);
    }

    /**
     * 重新执行
     * @param view
     */
    public void restart(View view) {
        progressView.setProgress("0",100,true);
    }


    class MyPagerAdapter extends FragmentStatePagerAdapter{
        List<Fragment>data;
        public MyPagerAdapter(FragmentManager fm, List<Fragment>data) {
            super(fm);
            this.data=data;
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }
}
