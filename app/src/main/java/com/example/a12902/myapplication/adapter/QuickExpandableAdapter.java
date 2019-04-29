package com.example.a12902.myapplication.adapter;

import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.app.MyApp;
import com.example.a12902.myapplication.entity.ExpandItem0;
import com.example.a12902.myapplication.entity.ExpandItem1;

import java.util.List;

public class QuickExpandableAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_LEVEL_2 = 2;

    public QuickExpandableAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expand0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expand1);
        addItemType(TYPE_LEVEL_2, R.layout.item_expand2);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        int itemViewType = helper.getItemViewType();
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final ExpandItem0 item0 = (ExpandItem0) item;
                helper.setText(R.id.title, item0.getTitle());
                helper.getView(R.id.title).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MyApp.getInstance().getApplicationContext(),"跳转界面",Toast.LENGTH_SHORT).show();
                    }
                });
                helper.getView(R.id.detail).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = helper.getAdapterPosition();
                        if (item0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;

            case TYPE_LEVEL_1:
                final ExpandItem1 item1 = (ExpandItem1) item;
                helper.setText(R.id.text, item1.getTitle());
                helper.getView(R.id.text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = helper.getAdapterPosition();
                        if (item1.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_2:
                break;
        }
    }
}
