package com.example.a12902.myapplication.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a12902.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QRCodeGenerateActivity extends AppCompatActivity {
    @BindView(R.id.generate)
    Button mGenerate;
    @BindView(R.id.iv)
    ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generate);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.generate)
    void generate() {
        //生成二维码是耗时操作
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
                //中文
//                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode("孔林", BGAQRCodeUtil.dp2px(QRCodeGenerateActivity.this, 150));
                //带logo
                Bitmap logoBitmap = BitmapFactory.decodeResource(QRCodeGenerateActivity.this.getResources(), R.mipmap.ic_launcher);
                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode("林", BGAQRCodeUtil.dp2px(QRCodeGenerateActivity.this, 150), Color.parseColor("#ff0000"), logoBitmap);
                emitter.onNext(bitmap);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        mIv.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
