package com.angle.hshb.customviewdemo.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.angle.hshb.customviewdemo.R;
import com.angle.hshb.customviewdemo.view.wirtingpan.new_code.IPenConfig;
import com.angle.hshb.customviewdemo.view.wirtingpan.new_code.NewDrawPenView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment implements View.OnClickListener {

    private Button mBrushPen;
    private Button mBtnStrokePen;
    private Button mBtnClearCanvas;
    private Button mBtnGetBitmap;
    private NewDrawPenView mDrawPenView;
    private ImageView mIvImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_three, container, false);
        findViews(view);
        doSomeThing();
        return view;
    }

    private void doSomeThing() {
        mBtnStrokePen.setOnClickListener(this);
        mBtnClearCanvas.setOnClickListener(this);
        mBrushPen.setOnClickListener(this);
        mBtnGetBitmap.setOnClickListener(this);
    }

    private void findViews(View view) {
        mBtnStrokePen = (Button) view.findViewById(R.id.btn_stroke_pen);
        mDrawPenView = (NewDrawPenView) view.findViewById(R.id.draw_pen_view);
        mBtnClearCanvas = (Button) view.findViewById(R.id.btn_clear_canvas);
        mBtnGetBitmap = (Button) view.findViewById(R.id.btn_get_bitmap);
        mBrushPen = (Button) view.findViewById(R.id.btn_brush_pen);
        mIvImage = (ImageView) view.findViewById(R.id.iv_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_stroke_pen:
                mDrawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_PEN);
                break;
            case R.id.btn_clear_canvas:
                mDrawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_ERASER);
                break;
            case R.id.btn_brush_pen:
                mDrawPenView.setCanvasCode(IPenConfig.STROKE_TYPE_BRUSH);
                break;
            case R.id.btn_get_bitmap:
                Bitmap bitmap = mDrawPenView.getmBitmap();
                if (bitmap != null)
                    mIvImage.setImageBitmap(bitmap);
                break;
        }
    }
}
