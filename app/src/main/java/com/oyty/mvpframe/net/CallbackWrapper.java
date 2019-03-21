package com.oyty.mvpframe.net;

import android.net.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by oyty on 2019/3/21.
 */
public abstract class CallbackWrapper<T> implements Observer<T> {

    private WeakReference<IView> weakReference;

    public CallbackWrapper(IView view) {
        this.weakReference = new WeakReference<>(view);
    }

    protected abstract void onSuccess(T data);

    protected void onFailed(String code, String msg) {
        weakReference.get().showToast(msg);
    }

    protected void onNetworkFailed(String msg) {
        weakReference.get().showToast(msg);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        weakReference.get().hideLoadingProgress();
        if(e instanceof ApiException) {
            ApiException api = (ApiException) e;
            onFailed(api.getCode(), api.getMessage());
        } else {
            String msg = "未知错误";
            if (e instanceof UnknownHostException) {
                msg = "网络不可用";
            } else if (e instanceof SocketTimeoutException) {
                msg = "请求网络超时";
            } else if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                msg = convertStatusCode(httpException);
            } else if (e instanceof JsonParseException || e instanceof ParseException || e instanceof JSONException || e instanceof JsonIOException) {
                msg = "数据解析错误";
            }
            onNetworkFailed(msg);
        }
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onSubscribe(Disposable d) {
    }
}
