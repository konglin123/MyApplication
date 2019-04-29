package com.example.a12902.myapplication.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.adapter.MainAdapter;
import com.example.a12902.myapplication.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RefreshActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefresh;

    private List<String> mDatas = new ArrayList<>();
    private MainAdapter mainAdapter;

    @Override
    protected void obtainIntent() {

    }

    @Override
    protected void initListener() {
        //刷新
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
                mRefresh.finishRefresh();
            }
        });
        //加载更多
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                for (int i = 0; i < 10; i++) {
                    mDatas.add("林氏一族" + i);
                }
                mainAdapter.notifyDataSetChanged();
                mRefresh.finishLoadMore();
            }
        });
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        mDatas.clear();
        for (int i = 0; i < 10; i++) {
            mDatas.add("林氏一族" + i);
        }
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(R.layout.item_main, mDatas);
        mRv.setAdapter(mainAdapter);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh;
    }
}
