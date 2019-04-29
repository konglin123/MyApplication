package com.example.a12902.myapplication.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a12902.myapplication.R;

import java.util.List;

public class ContentAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public ContentAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item_test_title, item);
        helper.setText(R.id.tv_item_test_content, "内容" + (helper.getAdapterPosition() + 1));
    }
}
