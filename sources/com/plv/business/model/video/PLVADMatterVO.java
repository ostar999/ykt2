package com.plv.business.model.video;

import com.plv.business.model.PLVBaseVO;
import com.plv.foundationsdk.config.PLVPlayOption;

/* loaded from: classes4.dex */
public class PLVADMatterVO extends PLVPlayOption.HeadAdOption implements PLVBaseVO {
    public static final int LOCATION_FIRST = 1;
    public static final int LOCATION_LAST = 3;
    public static final int LOCATION_PAUSE = 2;
    private int adHeight;
    private String adId;
    private int adType;
    private int adWidth;
    private String addrUrl;
    private long cataId;
    private String headAdvertClickUrl;
    private String headAdvertShowUrl;
    private String location;
    private String matterUrl;
    private String stopAdvertClickUrl;
    private String stopAdvertShowUrl;
    private int timeSize;

    public PLVADMatterVO(String str, int i2) {
        super(str, i2);
    }

    public int getAdHeight() {
        return this.adHeight;
    }

    public String getAdId() {
        return this.adId;
    }

    public int getAdType() {
        return this.adType;
    }

    public int getAdWidth() {
        return this.adWidth;
    }

    public String getAddrUrl() {
        return this.addrUrl;
    }

    public long getCataId() {
        return this.cataId;
    }

    public String getHeadAdvertClickUrl() {
        return this.headAdvertClickUrl;
    }

    public String getHeadAdvertShowUrl() {
        return this.headAdvertShowUrl;
    }

    public String getLocation() {
        return this.location;
    }

    public String getMatterUrl() {
        return this.matterUrl;
    }

    public String getStopAdvertClickUrl() {
        return this.stopAdvertClickUrl;
    }

    public String getStopAdvertShowUrl() {
        return this.stopAdvertShowUrl;
    }

    public int getTimeSize() {
        return this.timeSize;
    }

    public void setHeadAdvertClickUrl(String str) {
        this.headAdvertClickUrl = str;
    }

    public void setHeadAdvertShowUrl(String str) {
        this.headAdvertShowUrl = str;
    }

    public void setStopAdvertClickUrl(String str) {
        this.stopAdvertClickUrl = str;
    }

    public void setStopAdvertShowUrl(String str) {
        this.stopAdvertShowUrl = str;
    }
}
