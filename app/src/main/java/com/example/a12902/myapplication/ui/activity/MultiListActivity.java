package com.example.a12902.myapplication.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.adapter.MainAdapter;
import com.example.a12902.myapplication.adapter.QuickExpandableAdapter;
import com.example.a12902.myapplication.base.BaseActivity;
import com.example.a12902.myapplication.entity.ExpandItem0;
import com.example.a12902.myapplication.entity.ExpandItem1;
import com.example.a12902.myapplication.entity.ExpandItem2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MultiListActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView mRv;
    private QuickExpandableAdapter expandableAdapter;

    @Override
    protected void obtainIntent() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        expandableAdapter = new QuickExpandableAdapter(getExpandListData(10));
        expandableAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        mRv.setAdapter(expandableAdapter);
        expandableAdapter.expandAll(0, true);

    }

    private List<MultiItemEntity> getExpandListData(int count) {
        int lvCount = count;
        int lv1Count = 8;
        int lv2Count=5;

        List<MultiItemEntity> data = new ArrayList<>();
        for (int i = 0; i < lvCount; i++) {
            ExpandItem0 item0 = new ExpandItem0("一级列表标题" + i);
            for (int j = 0; j < lv1Count; j++) {
                ExpandItem1 item1 = new ExpandItem1("二级列表标题" + j);
                for (int k = 0; k < lv2Count; k++) {
                    item1.addSubItem(new ExpandItem2("三级列表标题" + k));
                }
                item0.addSubItem(item1);
            }
            data.add(item0);
        }

        return data;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_list;
    }
}
