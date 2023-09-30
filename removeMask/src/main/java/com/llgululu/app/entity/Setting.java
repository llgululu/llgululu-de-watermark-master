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
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设置信息id
     */
    @TableId(value = "se_id", type = IdType.AUTO)
    private Integer seId;

    /**
     * 是否开启广告（1是0否）
     */
    private String seIsOpenAd;

    /**
     * banner广告id
     */
    private String seAdBannerId;

    /**
     * 插屏广告id
     */
    private String seAdInterstitialId;

    /**
     * 激励视频广告id
     */
    private String seAdRewardedId;

    /**
     * 视频广告id
     */
    private String seAdVideoId;

    /**
     * 小程序appid
     */
    private String seAppId;

    /**
     * 小程序appSecret
     */
    private String seAppSecret;

    /**
     * 新用户每天可用次数（默认为10）
     */
    private Integer seUserDailyCount;
    /**
     * 观看激励视频增加次数（默认为4）
     */
    private Integer seWatchAdd;
    /**
     * 设置接口类型
     */
    private String seApi;

    public String getSeApi() {
        return seApi;
    }

    public void setSeApi(String seApi) {
        this.seApi = seApi;
    }

    public Integer getSeWatchAdd() {
        return seWatchAdd;
    }

    public void setSeWatchAdd(Integer seWatchAdd) {
        this.seWatchAdd = seWatchAdd;
    }

    public Integer getSeId() {
        return seId;
    }

    public void setSeId(Integer seId) {
        this.seId = seId;
    }

    public String getSeIsOpenAd() {
        return seIsOpenAd;
    }

    public void setSeIsOpenAd(String seIsOpenAd) {
        this.seIsOpenAd = seIsOpenAd;
    }

    public String getSeAdBannerId() {
        return seAdBannerId;
    }

    public void setSeAdBannerId(String seAdBannerId) {
        this.seAdBannerId = seAdBannerId;
    }

    public String getSeAdInterstitialId() {
        return seAdInterstitialId;
    }

    public void setSeAdInterstitialId(String seAdInterstitialId) {
        this.seAdInterstitialId = seAdInterstitialId;
    }

    public String getSeAdRewardedId() {
        return seAdRewardedId;
    }

    public void setSeAdRewardedId(String seAdRewardedId) {
        this.seAdRewardedId = seAdRewardedId;
    }

    public String getSeAdVideoId() {
        return seAdVideoId;
    }

    public void setSeAdVideoId(String seAdVideoId) {
        this.seAdVideoId = seAdVideoId;
    }

    public String getSeAppId() {
        return seAppId;
    }

    public void setSeAppId(String seAppId) {
        this.seAppId = seAppId;
    }

    public String getSeAppSecret() {
        return seAppSecret;
    }

    public void setSeAppSecret(String seAppSecret) {
        this.seAppSecret = seAppSecret;
    }

    public Integer getSeUserDailyCount() {
        return seUserDailyCount;
    }

    public void setSeUserDailyCount(Integer seUserDailyCount) {
        this.seUserDailyCount = seUserDailyCount;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "seId=" + seId +
                ", seIsOpenAd='" + seIsOpenAd + '\'' +
                ", seAdBannerId='" + seAdBannerId + '\'' +
                ", seAdInterstitialId='" + seAdInterstitialId + '\'' +
                ", seAdRewardedId='" + seAdRewardedId + '\'' +
                ", seAdVideoId='" + seAdVideoId + '\'' +
                ", seAppId='" + seAppId + '\'' +
                ", seAppSecret='" + seAppSecret + '\'' +
                ", seUserDailyCount=" + seUserDailyCount +
                ", seWatchAdd=" + seWatchAdd +
                ", seApi='" + seApi + '\'' +
                '}';
    }
}
