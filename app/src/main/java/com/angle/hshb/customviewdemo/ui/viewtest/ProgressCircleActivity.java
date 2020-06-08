package com.angle.hshb.customviewdemo.ui.viewtest;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.angle.hshb.customviewdemo.R;
import com.angle.hshb.customviewdemo.view.CircleProgressView;

public class ProgressCircleActivity extends AppCompatActivity {
    private CircleProgressView mCircleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_circle);
        mCircleProgressView = findViewById(R.id.circle_view);


        init();
    }

    private void init() {
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
                Toast.makeText(ProgressCircleActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
