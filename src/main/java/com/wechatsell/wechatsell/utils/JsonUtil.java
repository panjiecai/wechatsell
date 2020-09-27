package com.wechatsell.wechatsell.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json格式化
 */
public class JsonUtil {

    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
