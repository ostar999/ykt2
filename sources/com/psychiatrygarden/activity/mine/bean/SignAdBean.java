package com.psychiatrygarden.activity.mine.bean;

import com.psychiatrygarden.bean.HomepageSmallAdBean;
import java.util.List;

/* loaded from: classes5.dex */
public class SignAdBean {
    private String code;
    private List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> data;
    private String message;
    private String server_time;
    private boolean success;

    public String getCode() {
        return this.code;
    }

    public List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
