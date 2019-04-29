package com.example.a12902.myapplication.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.a12902.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class QRCodeActivity extends AppCompatActivity {
    @BindView(R.id.scan)
    Button mScan;
    @BindView(R.id.generate)
    Button mGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.scan)
    void scan(){
        if (!EasyPermissions.hasPermissions(QRCodeActivity.this,
                Manifest.permission.CAMERA
        )) {
            EasyPermissions.requestPermissions(QRCodeActivity.this, "读写权限",
                    20,
                    Manifest.permission.CAMERA);
            return;
        }
        startActivity(new Intent(QRCodeActivity.this,QRCodeScanActivity.class));
    }

    @OnClick(R.id.generate)
    void generate(){
        startActivity(new Intent(QRCodeActivity.this,QRCodeGenerateActivity.class));
    }
}
