package com.plv.business.model.video;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class PLVBaseVideoParams {
    public static final String HEAD_AD = "head_ad";
    public static final String IS_PPT_PLAY = "IS_PPT_PLAY";
    public static final String LOAD_SLOW_TIME = "loadSlowTime";
    public static final String MARQUEE = "marquee";
    public static final String PARAMS2 = "params2";
    public static final String PARAMS4 = "params4";
    public static final String PARAMS5 = "params5";
    public static final String VIDEO_ID = "VIDEO_ID";
    public static final String WAIT_AD = "wait_ad";
    public static final String WATERMARK = "watermark";
    private String channelId;
    private Map<String, Object> optionsMap = new HashMap();
    private String userId;
    private String videoId;
    private String viewerId;

    public PLVBaseVideoParams(String str, String str2) {
        this.videoId = str;
        this.viewerId = str2;
    }

    public PLVBaseVideoParams buildOptions(String str, Object obj) {
        this.optionsMap.put(str, obj);
        return this;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public <T> T getOptionValue(Class<T> cls, String str) {
        return (T) getOptionValue(cls, str, null);
    }

    public Map<String, Object> getOptionsMap() {
        return this.optionsMap;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public String getViewerId() {
        return this.viewerId;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void setVideoId(String str) {
        this.videoId = str;
    }

    public void setViewerId(String str) {
        this.viewerId = str;
    }

    public <T> T getOptionValue(Class<T> cls, String str, T t2) {
        T t3 = (T) this.optionsMap.get(str);
        return t3 == null ? t2 : t3;
    }

    public PLVBaseVideoParams(String str, String str2, String str3) {
        this.channelId = str;
        this.userId = str2;
        this.viewerId = str3;
    }

    public PLVBaseVideoParams(String str, String str2, String str3, String str4) {
        this.videoId = str;
        this.channelId = str2;
        this.userId = str3;
        this.viewerId = str4;
    }
}
