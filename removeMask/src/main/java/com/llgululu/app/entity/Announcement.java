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
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通告id（删除这个一定要一同删除content里面的通知内容）
     */
    @TableId(value = "an_id", type = IdType.AUTO)
    private Integer anId;

    /**
     * 通告标签（必读、最新等，不超过5个字为好）
     */
    private String anTag;

    /**
     * 通告标签颜色（primary，success，error，warning，info）
     */
    private String anTagType;

    /**
     * 通告标题（不超过20个字为好）
     */
    private String anTitle;

    /**
     * 通知创建时间
     */
    private Date anCreateTime;
    private String anContent;

    public String getAnContent() {
        return anContent;
    }

    public void setAnContent(String anContent) {
        this.anContent = anContent;
    }

    public Integer getAnId() {
        return anId;
    }

    public void setAnId(Integer anId) {
        this.anId = anId;
    }

    public String getAnTag() {
        return anTag;
    }

    public void setAnTag(String anTag) {
        this.anTag = anTag;
    }

    public String getAnTagType() {
        return anTagType;
    }

    public void setAnTagType(String anTagType) {
        this.anTagType = anTagType;
    }

    public String getAnTitle() {
        return anTitle;
    }

    public void setAnTitle(String anTitle) {
        this.anTitle = anTitle;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getAnCreateTime() {
        return anCreateTime;
    }

    public void setAnCreateTime(Date anCreateTime) {
        this.anCreateTime = anCreateTime;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "anId=" + anId +
                ", anTag='" + anTag + '\'' +
                ", anTagType='" + anTagType + '\'' +
                ", anTitle='" + anTitle + '\'' +
                ", anCreateTime=" + anCreateTime +
                ", anContent='" + anContent + '\'' +
                '}';
    }
}
