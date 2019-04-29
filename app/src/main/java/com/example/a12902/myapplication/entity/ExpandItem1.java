package com.example.a12902.myapplication.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.a12902.myapplication.adapter.QuickExpandableAdapter;

public class ExpandItem1 extends AbstractExpandableItem<ExpandItem2> implements MultiItemEntity{

    private String title;

    public ExpandItem1(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return QuickExpandableAdapter.TYPE_LEVEL_1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
