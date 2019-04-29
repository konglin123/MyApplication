package com.example.a12902.myapplication.http;

import android.util.Log;

import com.example.a12902.myapplication.api.ApiConstants;
import com.example.a12902.myapplication.api.ApiService;
import com.example.a12902.myapplication.app.MyApp;
import com.example.a12902.myapplication.cache.CacheProvider;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitutil {
    private static ApiService apiService;
    private static final long TIMEOUT = 30;
    private static final String TAG = "Retrofitutil";
    private static CacheProvider cacheProvider;

    private Retrofitutil() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(TAG, "retrofitBack:" + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.TIANYUJIA)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        cacheProvider = new RxCache.Builder().persistence(MyApp.getInstance().getFilesDir(), new GsonSpeaker())
                .using(CacheProvider.class);

        apiService = retrofit.create(ApiService.class);

    }

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Retrofitutil.class) {
                if (apiService == null) {
                    new Retrofitutil();
                }
            }
        }
        return apiService;
    }

    public static CacheProvider getCacheProvider(){
        if(cacheProvider==null){
            synchronized (Retrofitutil.class){
                if(cacheProvider==null){
                    new Retrofitutil();
                }
            }
        }
        return cacheProvider;
    }
}
