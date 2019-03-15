package com.oyty.mvpframe.base;

import android.content.Context;

import com.jess.arms.http.GlobalHttpHandler;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by oyty on 2019/3/15.
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {

    private final Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    /**
     * 这里可以先客户端一步拿到 Http 的请求结果
     * @param httpResult
     * @param chain
     * @param response
     * @return
     */
    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        return response;
    }

    /**
     * 这里可以再请求服务器之前拿到 {@link Request}, 可以统一加一些 header 或 token，或者一些加密参数
     * @param chain
     * @param request
     * @return
     */
    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {

        return chain.request().newBuilder()
                .header("token", "token")
                .build();
//        return request;
    }
}
