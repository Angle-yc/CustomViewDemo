package com.angle.hshb.customviewdemo.fragment;


import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.angle.hshb.customviewdemo.R;
import com.angle.hshb.customviewdemo.view.CircleProgressView;

/**
 * 圆形进度
 */
public class SecondFragment extends Fragment {

    private CircleProgressView mCircleProgressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_second, container, false);
        initView(ret);
        return ret;
    }

    public void initView(View ret){
        mCircleProgressView = (CircleProgressView)ret.findViewById(R.id.circle_view);

        //进度条从0到100
        ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
        animator.setDuration(4000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (float) animation.getAnimatedValue();
                mCircleProgressView.setmCurrent((int) current);
            }
        });
        animator.start();

        mCircleProgressView.setOnLoadingCompleteListener(new CircleProgressView.OnLoadingCompleteListener() {
            @Override
            public void complete() {
                Toast.makeText(getContext(), "加载完成", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
