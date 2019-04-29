package com.example.a12902.myapplication.http;

import android.widget.Toast;

import com.example.a12902.myapplication.app.MyApp;
import com.example.a12902.myapplication.util.NetUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulers {
    public static <T> ObservableTransformer<T, T> compose() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (!NetUtil.isNetworkAvailable()) {
                                    Toast.makeText(MyApp.getInstance(), "请检查网络", Toast.LENGTH_SHORT).show();
                                    disposable.dispose();
                                } else {
                                    //TODO 显示dialog

                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                //TODO 隐藏dialog
                            }
                        });
            }
        };
    }
}
