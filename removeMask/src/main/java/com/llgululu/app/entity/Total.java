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
 * @since 2023-08-31
 */
public class Total implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "t_id", type = IdType.AUTO)
    private Integer tId;

    /**
     * 用户总使用量
     */
    private Integer tTotalUse;

    /**
     * 总用户数
     */
    private Integer tUser;

    /**
     * 数据整理的日期
     */
    private Date tTime;

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Integer gettTotalUse() {
        return tTotalUse;
    }

    public void settTotalUse(Integer tTotalUse) {
        this.tTotalUse = tTotalUse;
    }

    public Integer gettUser() {
        return tUser;
    }

    public void settUser(Integer tUser) {
        this.tUser = tUser;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date gettTime() {
        return tTime;
    }

    public void settTime(Date tTime) {
        this.tTime = tTime;
    }

    @Override
    public String toString() {
        return "Total{" +
        "tId = " + tId +
        ", tTotalUse = " + tTotalUse +
        ", tUser = " + tUser +
        ", tTime = " + tTime +
        "}";
    }
}
