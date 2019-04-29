package com.example.a12902.myapplication.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.a12902.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoActivity extends AppCompatActivity {
    @BindView(R.id.multi)
    Button mMultiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.multi)
    void multi() {
        startActivity(new Intent(PhotoActivity.this, MultiPhotoActivity.class));
    }
}
