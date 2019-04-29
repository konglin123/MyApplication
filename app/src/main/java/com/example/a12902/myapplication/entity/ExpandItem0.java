package com.example.a12902.myapplication.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.a12902.myapplication.adapter.QuickExpandableAdapter;

public class ExpandItem0 extends AbstractExpandableItem<ExpandItem1> implements MultiItemEntity {
    private String title;

    public ExpandItem0(String title) {
        this.title = title;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return QuickExpandableAdapter.TYPE_LEVEL_0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
