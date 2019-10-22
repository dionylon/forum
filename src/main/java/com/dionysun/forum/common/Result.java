package com.dionysun.forum.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Objects;

/**
 * 返回结果类
 */
public class Result<T> extends JSONObject{
    private Integer statusCode;
    private T object;

    public Result(Integer statusCode, T object) {
        this.statusCode = statusCode;
        this.object = object;
        put("status",statusCode);
        put("data",object);
    }
}
