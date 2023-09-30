package com.llgululu.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author llgululu
 * @since 2023-08-29
 */
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
    @TableId(value = "ad_id", type = IdType.AUTO)
    private Integer adId;

    /**
     * 登录账号
     */
    private String adName;

    /**
     * 账号密码
     */
    private String adPassword;

    /**
     * 登录时间
     */
    private Date adLoginTime;

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdPassword() {
        return adPassword;
    }

    public void setAdPassword(String adPassword) {
        this.adPassword = adPassword;
    }

    public Date getAdLoginTime() {
        return adLoginTime;
    }

    public void setAdLoginTime(Date adLoginTime) {
        this.adLoginTime = adLoginTime;
    }

    @Override
    public String toString() {
        return "Admin{" +
        "adId = " + adId +
        ", adName = " + adName +
        ", adPassword = " + adPassword +
        ", adLoginTime = " + adLoginTime +
        "}";
    }
}
