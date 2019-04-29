package com.example.a12902.myapplication.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.adapter.MainAdapter;
import com.example.a12902.myapplication.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

//主界面，用来添加各种功能的界面并跳转
public class MainActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    private MainAdapter mainAdapter;
    private ArrayList<String> strings = new ArrayList<>();


    @Override
    protected void obtainIntent() {

    }

    @Override
    protected void initListener() {
        mainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        //跳转拍照录音录视频界面
                        startActivity(new Intent(MainActivity.this, RecordActivity.class));
                        break;
                    case 1:
                        //跳转百度地图签到签退界面
                        startActivity(new Intent(MainActivity.this, BaiduMapActivity.class));
                        break;
                    case 2:
                        //跳转二维码扫描生成界面
                        startActivity(new Intent(MainActivity.this, QRCodeActivity.class));
                        break;
                    case 3:
                        //跳转签到定制界面
                        startActivity(new Intent(MainActivity.this, RiLiActivity.class));
                        break;
                    case 4:
                        //跳转BGA拍照相册选择界面
                        startActivity(new Intent(MainActivity.this, PhotoActivity.class));
                        break;
                    case 5:
                        //跳转BGA滑动返回界面
                        startActivity(new Intent(MainActivity.this, SwipeBackActivity.class));
                        break;
                    case 6:
                        //跳转Fragmentation管理fragment并自带滑动返回界面
                        startActivity(new Intent(MainActivity.this, FragmentationActivity.class));
                        break;

                    case 7:
                        //跳转下拉刷新和上拉加载更多界面
                        startActivity(new Intent(MainActivity.this, RefreshActivity.class));
                        break;
                    case 8:
                        //跳转二级或三级列表界面
                        startActivity(new Intent(MainActivity.this, MultiListActivity.class));
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        strings.add("拍照录音录视频界面");
        strings.add("百度地图签到签退界面");
        strings.add("二维码扫描生成界面");
        strings.add("签到日历定制界面");
        strings.add("BGA拍照相册选择界面");
        strings.add("BGA滑动返回界面(只支持activity)");
        strings.add("Fragmentation管理fragment并自带滑动返回界面");
        strings.add("下拉刷新和上拉加载更多界面");
        strings.add("二级或三级列表界面");
        rv.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(R.layout.item_main, strings);
        rv.setAdapter(mainAdapter);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
