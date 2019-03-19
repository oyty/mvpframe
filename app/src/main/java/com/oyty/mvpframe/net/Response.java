package com.oyty.mvpframe.net;

import org.json.JSONObject;

/**
 * Created by oyty on 2019/3/18.
 */
public class Response {
    public String message;

    public String code;
    public String result;

    public Response(JSONObject jsonObject) {
        this.code = jsonObject.optString("code");
        this.message = jsonObject.optString("message");
        this.result = jsonObject.optString("data");
    }
}
