package com.angle.hshb.customviewdemo.nicedialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.angle.hshb.customviewdemo.R;

/**
 * Created by Administrator on 2017/9/12.
 */

public class ConfirmDialog extends BaseNiceDialog{

    private String title="";
    private String message="";
    private ViewClickListener viewClickListener;

    public static ConfirmDialog newIntence(String title,String message){
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        title = bundle.getString("title");
        message = bundle.getString("message");
    }

    @Override
    public int intLayoutId() {
        return R.layout.confirm_layout;
    }

    @Override
    public void convertView(final ViewHolder holder, final BaseNiceDialog dialog) {
        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (viewClickListener!=null){
                    viewClickListener.clickView(holder);
                }
            }
        });
        holder.setText(R.id.title,title);
        holder.setText(R.id.message,message);
    }

    public ConfirmDialog setOnViewConfirmListener(ViewClickListener viewClickListener){
        this.viewClickListener=viewClickListener;
        return this;
    }
}
