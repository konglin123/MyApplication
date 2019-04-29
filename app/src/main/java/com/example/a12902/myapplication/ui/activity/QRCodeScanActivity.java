package com.example.a12902.myapplication.ui.activity;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.a12902.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class QRCodeScanActivity extends AppCompatActivity implements QRCodeView.Delegate, View.OnClickListener {
    @BindView(R.id.zxingview)
    QRCodeView mQRCodeView;
    @BindView(R.id.start_spot)
    Button mStart;
    @BindView(R.id.stop_spot)
    Button mStop;
    @BindView(R.id.open_flashlight)
    Button mOpen;
    @BindView(R.id.close_flashlight)
    Button mClose;
    private static final String TAG = "QRCodeScanActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);
        ButterKnife.bind(this);
        mQRCodeView.setDelegate(this);
        mStart.setOnClickListener(this);
        mStop.setOnClickListener(this);
        mOpen.setOnClickListener(this);
        mClose.setOnClickListener(this);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        //扫码成功的回调
        vibrate();
        Log.e(TAG, "onScanQRCodeSuccess: "+result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        //打开相机失败的回调
        Log.e(TAG, "onScanQRCodeOpenCameraError: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();//打开相机
        mQRCodeView.showScanRect();//显示扫描框
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_spot:
                mQRCodeView.startSpot();
                break;
            case R.id.stop_spot:
                mQRCodeView.stopSpot();
                break;
            case R.id.open_flashlight:
                mQRCodeView.openFlashlight();
                break;
            case R.id.close_flashlight:
                mQRCodeView.closeFlashlight();
                break;


        }
    }

    //震动

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
