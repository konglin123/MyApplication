package com.example.a12902.myapplication.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.app.MyApp;
import com.example.a12902.myapplication.db.AccessoryDbEntity;

import java.util.List;

public class AccessoryAdapter extends BaseQuickAdapter<AccessoryDbEntity, BaseViewHolder> {
    private static final int TYPE_PIC = 1;
    private static final int TYPE_VOICE = 2;
    private static final int TYPE_VIDEO = 3;

    public AccessoryAdapter(int layoutResId, @Nullable List<AccessoryDbEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccessoryDbEntity entity) {
if(entity.getAccType()==TYPE_PIC){
    helper.setText(R.id.sj_tv,entity.getPssj());
    Glide.with(MyApp.getInstance()).load(entity.getThumbPath())
            .into((ImageView) helper.getView(R.id.thumb_iv));
}
    }
}
