package com.ycgrp.cloudticket.api;


import com.ycgrp.cloudticket.BuildConfig;
import com.ycgrp.cloudticket.CloudTicketApplication;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    private static volatile RetrofitClient mInstance;
    private Retrofit mRetrofit;
    private Cache cache;
    public static final String BASE_URL = "https://api.ycgrp.cn/";

    private RetrofitClient() {

        //创建缓存路径
        File cacheFile = new File(CloudTicketApplication.getContext().getCacheDir(), "HttpCache");
        cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(15, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(15, TimeUnit.SECONDS);
        okHttpClientBuilder.retryOnConnectionFailure(true);
        okHttpClientBuilder.cache(cache);
        okHttpClientBuilder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                builder.addHeader("Authorization", "Token token=" + getToken());
                Request request = builder.build();
                return chain.proceed(request);
            }
        });

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClientBuilder.build())
                .validateEagerly(BuildConfig.DEBUG)
                .build();
    }


    /**
     * 获取token
     * @return token
     */
    String getToken() {
        if (CloudTicketApplication.getInstance().getmLoginResponse() != null) {
            if (CloudTicketApplication.getInstance().getmLoginResponse().getToken()!=null) {
                return CloudTicketApplication.getInstance().getmLoginResponse().getToken();
            }
        }
        return null;
    }





    public Retrofit getmRetrofit() {
        return mRetrofit;
    }

    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }
}