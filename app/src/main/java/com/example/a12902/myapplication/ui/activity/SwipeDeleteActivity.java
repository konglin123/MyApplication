package com.example.a12902.myapplication.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.adapter.SwipeDeleteAdapter;
import com.example.a12902.myapplication.base.BaseActivity;
import com.example.a12902.myapplication.entity.NormalModel;
import com.example.a12902.myapplication.util.DataUtil;

import java.util.List;

import butterknife.BindView;

public class SwipeDeleteActivity extends BaseActivity {
    @BindView(R.id.rv_swipe_delete_data)
    RecyclerView mRv;
    private SwipeDeleteAdapter swipeDeleteAdapter;

    @Override
    protected void obtainIntent() {

    }

    @Override
    protected void initListener() {
        swipeDeleteAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                swipeDeleteAdapter.closeOpenedSwipeItemLayoutWithAnim();
                swipeDeleteAdapter.remove(position);
            }
        });

        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState) {
                    swipeDeleteAdapter.closeOpenedSwipeItemLayoutWithAnim();
                }
            }
        });

    }

    @Override
    protected void initData() {
        //设置是否仅仅跟踪左侧边缘的滑动返回
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(false);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        List<NormalModel> normalModels = DataUtil.loadNormalModelDatas();
        swipeDeleteAdapter = new SwipeDeleteAdapter(R.layout.item_bgaswipe, normalModels);
        mRv.setAdapter(swipeDeleteAdapter);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_swipe_delete;
    }
}
