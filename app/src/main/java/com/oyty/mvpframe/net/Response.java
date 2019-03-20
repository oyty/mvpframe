package com.oyty.mvpframe.net;

/**
 * Created by oyty on 2019/3/18.
 */
public class Response<T> {

    private String message;
    private String code;
    private T data;


    public boolean isSuccess() {
        return "0000".equals(code);
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
