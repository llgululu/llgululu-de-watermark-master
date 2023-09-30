package com.llgululu.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author llgululu
 * @since 2023-08-27
 */
public class Swipe implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 首页轮播图id
     */
    @TableId(value = "sw_id", type = IdType.AUTO)
    private Integer swId;

    /**
     * 轮播图链接
     */
    private String swUrl;

    /**
     * 轮播图名称
     */
    private String swName;

    public Integer getSwId() {
        return swId;
    }

    public void setSwId(Integer swId) {
        this.swId = swId;
    }

    public String getSwUrl() {
        return swUrl;
    }

    public void setSwUrl(String swUrl) {
        this.swUrl = swUrl;
    }

    public String getSwName() {
        return swName;
    }

    public void setSwName(String swName) {
        this.swName = swName;
    }

    @Override
    public String toString() {
        return "Swipe{" +
        "swId = " + swId +
        ", swUrl = " + swUrl +
        ", swName = " + swName +
        "}";
    }
}
