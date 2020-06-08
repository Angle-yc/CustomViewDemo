package com.angle.hshb.customviewdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angle.hshb.customviewdemo.R;
import com.angle.hshb.customviewdemo.bean.ItemBean;
import com.angle.hshb.customviewdemo.ui.viewtest.TouchPullActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者    angle
 * 时间    2020-06-02 10:14
 * 文件    CustomViewDemo
 * 描述
 */
public class ItemTextAdapter extends RecyclerView.Adapter<ItemTextAdapter.TextViewHolder> {

    private Context mContext;
    private List<ItemBean> mData;

    public ItemTextAdapter(Context mContext, List<ItemBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_text,parent,false));
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, final int position) {
        holder.textView.setText(mData.get(position).getValue());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, mData.get(position).getClazz()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class TextViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public TextViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_text);
        }
    }
}
