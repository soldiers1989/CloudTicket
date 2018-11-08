package com.ycgrp.cloudticket.api;



import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * desc: retrofit请求的回调
 */

public abstract class BaseCallBackListener<T> {

    /**
     * 开始请求之前
     */
    public void onBefore(Disposable d) {
    }


    /**
     * 请求成功
     *
     * @param result
     */
    public void onSuccess(T result) {

    }

    /**
     * 请求后，服务器返回错误信息
     *
     * @param errorLog
     */
    public void errorByResult(String errorLog) {
    }

    /**
     * 请求出错
     *
     * @param e
     */
    public void onError(@NonNull Throwable e) {

    }

    /**
     * 请求完成
     */
    public void onComplete() {
    }
}
