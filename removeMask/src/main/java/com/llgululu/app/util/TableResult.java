package com.llgululu.app.util;

import java.util.List;

public class TableResult<T> {
    //    后台返回的状态码，0-成功； 其它值不成功
    private int code;
    // 后台返回的提示信息。如果请求失败时，数据表格会把提示信息显示出来
    private String msg;
    //表里的总记录数，用于计算分页
    private long count;
    //当前页显示的数据
    private List<T> data;
    //实体对象
    private T obj;

    public TableResult(int code, String msg, long count, List<T> data, T obj) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
        this.obj = obj;
    }

    public static <T> TableResult<T> ok(String msg, long count, List<T> data){
        return new TableResult<T>(0, msg, count, data, null);
    }

    public static <T> TableResult<T> ok(String msg){
        return new TableResult<T>(0, msg, 0, null, null);
    }

    public static <T> TableResult<T> ok(String msg, T obj){
        return new TableResult<T>(0, msg, 0, null, obj);
    }

    public static <T> TableResult<T> error(int code, String msg){
        return new TableResult<T>(1, msg, code, null, null);
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
