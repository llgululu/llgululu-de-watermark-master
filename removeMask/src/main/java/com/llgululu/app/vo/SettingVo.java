package com.llgululu.app.vo;

import java.io.Serializable;

public class SettingVo implements Serializable {
    
    /**
     * 是否开启广告（1是0否）
     */
    private boolean seIsOpenAd;

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
    

    public boolean getSeIsOpenAd() {
        return seIsOpenAd;
    }

    public void setSeIsOpenAd(boolean seIsOpenAd) {
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
}
