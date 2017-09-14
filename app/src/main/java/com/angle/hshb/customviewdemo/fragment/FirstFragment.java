package com.angle.hshb.customviewdemo.fragment;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.angle.hshb.customviewdemo.R;
import com.angle.hshb.customviewdemo.nicedialog.ConfirmDialog;
import com.angle.hshb.customviewdemo.nicedialog.ViewClickListener;
import com.angle.hshb.customviewdemo.nicedialog.ViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_first, container, false);
        initView(ret);
        return ret;
    }

    private void initView(View ret) {
        TextView tvText= (TextView) ret.findViewById(R.id.tv_text);

        tvText.setText("我是一只小小小小鸟！");
//        tvText.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中间划线
        tvText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线

        ret.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog.newIntence("提示","你家后门着火了")
                        .setOnViewConfirmListener(new ViewClickListener() {
                            @Override
                            public void clickView(ViewHolder holder) {
                                Toast.makeText(getContext(),((TextView)holder.getView(R.id.message)).getText().toString(),Toast.LENGTH_LONG).show();
                            }
                        })
                        .setMargin(60)
                        .setDimAmount(0.5f)
                        .setOutCancel(true)
                        .show(getFragmentManager());
            }
        });

    }

}
