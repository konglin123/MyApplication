package com.example.a12902.myapplication.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeBackActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.swipeBack)
    Button mSwipeBack;
    @BindView(R.id.swipeDelete)
    Button mSwipeDelete;

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void obtainIntent() {

    }

    @Override
    protected void initListener() {
        mSwipeBack.setOnClickListener(this);
        mSwipeDelete.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_swipe_back;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.swipeBack:
                mSwipeBackHelper.forward(TestActivity.class);
                break;
            case R.id.swipeDelete:
                mSwipeBackHelper.forward(SwipeDeleteActivity.class);
                break;
        }
    }
}
