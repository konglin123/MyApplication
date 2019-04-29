package com.example.a12902.myapplication.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.a12902.myapplication.adapter.QuickExpandableAdapter;

public class ExpandItem2 implements MultiItemEntity{

    private String title;

    public ExpandItem2(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return QuickExpandableAdapter.TYPE_LEVEL_2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
