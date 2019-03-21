package com.oyty.mvpframe.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtil {

	public static <T> T json2Bean(String result , Class<T> clazz){
		Gson gson = getGson();
		T t = gson.fromJson(result, clazz);
		return t;
	}

	public static <T> T jsonRawBean(String result , Class<T> clazz){
		Gson gson = new Gson();
		T t = gson.fromJson(result, clazz);
		return t;
	}

    public static String bean2Json(Object obj) {
        Gson gson = getGson();
        return gson.toJson(obj);
    }

    public static <T> String array2Json(List<T> lists) {

        Type listType = new TypeToken<List<T>>(){}.getType();
        Gson gson = getGson();
        return gson.toJson(lists, listType);
    }

    public static <T> List<T> json2Array(String result, TypeToken<List<T>> typeToken) {
        Gson gson = getGson();
        return gson.fromJson(result, typeToken.getType());
    }

    public static <T> ArrayList<T> json2ArrayList(String result, TypeToken<ArrayList<T>> typeToken) {
        Gson gson = getGson();
        return gson.fromJson(result, typeToken.getType());
    }

    public static <T> List<T> json2ArrayRaw(String result, TypeToken<List<T>> typeToken) {
        Gson gson = new Gson();
        return gson.fromJson(result, typeToken.getType());
    }

    public static Map<String,Object> json2Map(String result){
        Gson gson = getGson();
        return gson.fromJson(result,Map.class);
    }

    public static Gson getGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public static String toJsonObj(String key, String value) {
        JSONObject object = new JSONObject();
        try {
            object.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

}
