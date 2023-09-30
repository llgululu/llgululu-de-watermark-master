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
 * @since 2023-08-28
 */
public class Newdownload implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "n_id", type = IdType.AUTO)
    private Integer nId;

    /**
     * 微信小程序新增downloadfile域名
     */
    private String nUrl;

    public Integer getnId() {
        return nId;
    }

    public void setnId(Integer nId) {
        this.nId = nId;
    }

    public String getnUrl() {
        return nUrl;
    }

    public void setnUrl(String nUrl) {
        this.nUrl = nUrl;
    }

    @Override
    public String toString() {
        return "Newdownload{" +
        "nId = " + nId +
        ", nUrl = " + nUrl +
        "}";
    }
}
