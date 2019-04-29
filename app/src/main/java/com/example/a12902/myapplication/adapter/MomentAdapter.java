package com.example.a12902.myapplication.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.entity.Moment;

import java.util.List;

import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;

public class MomentAdapter extends BaseQuickAdapter<Moment, BaseViewHolder> {
    private BGANinePhotoLayout.Delegate delegate;
    public MomentAdapter(int layoutResId, @Nullable List<Moment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Moment item) {
        helper.setText(R.id.tv_item_moment_content, item.content);
        BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.npl_item_moment_photos);
        ninePhotoLayout.setDelegate(delegate);
        ninePhotoLayout.setData(item.photos);
    }

    public void setDelegate(BGANinePhotoLayout.Delegate delegate) {
        this.delegate = delegate;
    }
}
