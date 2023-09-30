package com.llgululu.app.util;

import java.util.Map;

public class R<T> {
    private int code;
    private String msg;
    private Map<Object, Object> data;
    private T obj;

    public R(int code, String msg, Map<Object, Object> data, T obj) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.obj = obj;
    }

    public static <T> R<T> ok(String msg, Map<Object, Object> data) {
        return new R<>(200, msg, data, null);
    }

    public static <T> R<T> ok(int code,String msg, T obj) {
        return new R<>(code, msg, null, obj);
    }

    public static <T> R<T> ok(T obj) {
        return new R<>(1, null, null, obj);
    }

    public static <T> R<T> ok(int code, String msg) {
        return new R<>(code, msg, null, null);
    }

    public static <T> R<T> error(String msg) {
        return new R<>(404, msg, null, null);
    }

    public static <T> R<T> error(int code, String msg) {
        return new R<>(code, msg, null, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<Object, Object> getData() {
        return data;
    }

    public void setData(Map<Object, Object> data) {
        this.data = data;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
