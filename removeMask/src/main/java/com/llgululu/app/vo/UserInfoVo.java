package com.llgululu.app.vo;

public class UserInfoVo {
    private Integer uId;
    /**
     * 用户总共已使用次数
     */
    private Integer uTotalUse;

    /**
     * 用户每天设置次数（默认为10，可修改）
     */
    private Integer uSysCount;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
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
}
