package com.example.a12902.myapplication.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.adapter.ContentPagerAdapter;
import com.example.a12902.myapplication.base.BaseActivity;

import butterknife.BindView;

public class TestActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.onlyTrackingLeftEdgeSwitch)
    SwitchCompat mSwitchTrackingLeft;
    @BindView(R.id.swipeBackEnableSwitch)
    SwitchCompat mSwitchBack;

    @Override
    protected void obtainIntent() {

    }

    @Override
    protected void initListener() {
        mSwitchTrackingLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                /**
                 * 设置是否仅仅跟踪左侧边缘的滑动返回
                 */
                mSwipeBackHelper.setIsOnlyTrackingLeftEdge(checked);
            }
        });

        mSwitchBack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                /**
                 * 设置滑动返回是否可用
                 */
                mSwipeBackHelper.setSwipeBackEnable(checked);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                // 测试只有ViewPager在第0页时才开启滑动返回
                mSwipeBackHelper.setSwipeBackEnable(position == 0);
                mSwitchBack.setChecked(position == 0);
            }
        });
    }

    @Override
    protected void initData() {
        mViewPager.setAdapter(new ContentPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }
}
