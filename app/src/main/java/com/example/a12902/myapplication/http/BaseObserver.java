package com.example.a12902.myapplication.http;

import android.widget.Toast;

import com.example.a12902.myapplication.app.MyApp;
import com.google.gson.JsonParseException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onHandleSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        //todo 分类集中常见的异常
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(MyApp.getInstance(), "网络超时", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(MyApp.getInstance(), "链接异常 ", Toast.LENGTH_SHORT).show();
        } else if (e instanceof UnknownHostException) {
            Toast.makeText(MyApp.getInstance(), "Host异常", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ClassCastException) {
            Toast.makeText(MyApp.getInstance(), "类型转换异常", Toast.LENGTH_SHORT).show();
        } else if (e instanceof JsonParseException) {
            Toast.makeText(MyApp.getInstance(), "解析异常", Toast.LENGTH_SHORT).show();
        } else {

        }

        //todo 隐藏进度条
    }

    @Override
    public void onComplete() {
        //todo 隐藏进度条
    }

    public abstract void onHandleSuccess(T t);


}
