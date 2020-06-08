package com.angle.hshb.customviewdemo.ui.viewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.angle.hshb.customviewdemo.R;
import com.angle.hshb.customviewdemo.view.TouchPullView;

public class TouchPullActivity extends AppCompatActivity {
    private TouchPullView mTouchPullView;
    public static final float TOUCH_MOVE_MAX_Y = 600;
    private float mTouhMoveStartY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_pull);
        initView();
        findViewById(R.id.rl_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mTouhMoveStartY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float y = event.getY();
                        if (y >= TOUCH_MOVE_MAX_Y){
                            float movieSize = y - TOUCH_MOVE_MAX_Y;
                            float progress = movieSize >= TOUCH_MOVE_MAX_Y ? 1 : movieSize / TOUCH_MOVE_MAX_Y;
                            mTouchPullView.setProgress(progress);
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /***
     * 初始化View
     */
    private void initView() {
        mTouchPullView = findViewById(R.id.touchPull);
    }
}
