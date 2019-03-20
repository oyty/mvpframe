package com.oyty.mvpframe.base;

import android.content.Context;

import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.utils.LogUtils;
import com.oyty.mvpframe.util.JniRsaUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.HttpUrl;
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
    public Response onHttpResultResponse(String httpResult, @NotNull Interceptor.Chain chain, @NotNull Response response) {
        return response;
    }

    /**
     * 这里可以再请求服务器之前拿到 {@link Request}, 可以统一加一些 header 或 token，或者一些加密参数
     * @param chain
     * @param request
     * @return
     */
    @Override
    public Request onHttpRequestBefore(@NotNull Interceptor.Chain chain, @NotNull Request request) {

        return chain.request().newBuilder()
                .header("token", "token")
                .header("appid", "1002")
                .header("deviceid", "1232432423432")
                .header("os", "android")
                .header("sign", getSign(request))
                .build();
//        return request;
    }

    private String getSign(Request request) {
        String url = request.url().url().toString();
        String host = request.url().host();
        String signUrl = url.split(host)[1];

        if(signUrl.contains("?")) {
            String[] urlSpilt = signUrl.split("\\?");
            String[] split = urlSpilt[1].split("&");
            List<String> paramList = Arrays.asList(split);
            Collections.sort(paramList);
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<paramList.size(); i++) {
                sb.append(paramList.get(i));
                sb.append("&");
            }
            String param = sb.toString();
            if (param.endsWith("&")) {
                param = param.substring(0, param.length() - 1);
            }
            String urlSB = urlSpilt[0] +
                    "?" +
                    param;
            return JniRsaUtil.jniSign(urlSB.getBytes());

        } else {
            return JniRsaUtil.jniSign((signUrl + "?").getBytes());
        }
    }
}
