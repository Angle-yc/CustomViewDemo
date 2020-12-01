package com.angle.hshb.customviewdemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.angle.hshb.customviewdemo.R;
import com.angle.hshb.customviewdemo.adapter.ItemTextAdapter;
import com.angle.hshb.customviewdemo.bean.ItemBean;
import com.angle.hshb.customviewdemo.ui.viewtest.BezierActivity;
import com.angle.hshb.customviewdemo.ui.viewtest.DrawBoardActivity;
import com.angle.hshb.customviewdemo.ui.viewtest.PathActivity;
import com.angle.hshb.customviewdemo.ui.viewtest.PracDrawActivity;
import com.angle.hshb.customviewdemo.ui.viewtest.ProgressCircleActivity;
import com.angle.hshb.customviewdemo.ui.viewtest.TouchPullActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        recyclerView = findViewById(R.id.recyclerview);

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ItemTextAdapter(this,getData()));
    }

    private List<ItemBean> getData() {
        List<ItemBean> list = new ArrayList<>();
        list.add(new ItemBean("进度圆", ProgressCircleActivity.class));
        list.add(new ItemBean("画板", DrawBoardActivity.class));
        list.add(new ItemBean("下拉刷新粘性圆效果", TouchPullActivity.class));
        list.add(new ItemBean("贝塞尔曲线的练习", BezierActivity.class));
        list.add(new ItemBean("自由练习", PracDrawActivity.class));
        list.add(new ItemBean("路径练习", PathActivity.class));
        return list;
    }
}
