package com.dionysun.forum.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.entity.AbstractEntity;

import java.util.List;

/**
 * 返回结果类
 * @param <T> 结果参数
 */
public class Result<T extends AbstractEntity> extends JSONObject{
    private String statusKey = "status";
    private String msgKey = "msg";
    private String resultListKey = "result";

    public Result(Integer statusCode, String msg, List<T> entities) {
        this(statusCode, msg);
        JSONArray entityJsonArray = new JSONArray();
        entityJsonArray.addAll(entities);
        put(resultListKey, entityJsonArray);
    }

    public Result(Integer statusCode, String msg, T entity){
        this(statusCode, msg);
        put("entity",entity);
    }

    public Result(Integer statusCode, String msg) {
        put(statusKey, statusCode);
        put(msgKey, msg);
    }

}
