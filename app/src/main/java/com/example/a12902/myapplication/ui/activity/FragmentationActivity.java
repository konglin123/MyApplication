package com.example.a12902.myapplication.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.a12902.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentationActivity extends AppCompatActivity {
    @BindView(R.id.btn_flow)
    Button mBtnFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentation);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_flow)
    void flow() {
        startActivity(new Intent(FragmentationActivity.this, FragmentationFlowActivity.class));
    }
}
