package com.llgululu.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author llgululu
 * @since 2023-08-27
 */
public class Userinfo implements Serializable, Comparable<Userinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "u_id", type = IdType.AUTO)
    private Integer uId;

    /**
     * 微信用户唯一标识码
     */
    private String uOpenId;

    /**
     * 用户总共已使用次数
     */
    private Integer uTotalUse;

    /**
     * 用户每天设置次数（默认为10，可修改）
     */
    private Integer uSysCount;
    

    /**
     * 用户注册时间
     */
    private Date uRegTime;

    /**
     * 用户最新登录时间
     */
    private Date uLoginTime;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getuOpenId() {
        return uOpenId;
    }

    public void setuOpenId(String uOpenId) {
        this.uOpenId = uOpenId;
    }

    public Integer getuTotalUse() {
        return uTotalUse;
    }

    public void setuTotalUse(Integer uTotalUse) {
        this.uTotalUse = uTotalUse;
    }

    public Integer getuSysCount() {
        return uSysCount;
    }

    public void setuSysCount(Integer uSysCount) {
        this.uSysCount = uSysCount;
    }


    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getuRegTime() {
        return uRegTime;
    }

    public void setuRegTime(Date uRegTime) {
        this.uRegTime = uRegTime;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getuLoginTime() {
        return uLoginTime;
    }

    public void setuLoginTime(Date uLoginTime) {
        this.uLoginTime = uLoginTime;
    }

    @Override
    public String toString() {
        return "Userinfo{" +
        "uId = " + uId +
        ", uOpenId = " + uOpenId +
        ", uTotalUse = " + uTotalUse +
        ", uSysCount = " + uSysCount +
        ", uRegTime = " + uRegTime +
        ", uLoginTime = " + uLoginTime +
        "}";
    }

    @Override
    public int compareTo(Userinfo o) {
        return this.uId-o.uId;
    }
}
