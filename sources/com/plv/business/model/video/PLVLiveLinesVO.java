package com.plv.business.model.video;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes4.dex */
public class PLVLiveLinesVO implements PLVBaseVO {
    private String audioFlv;
    private String audioM3u8;
    private String bakFlv;
    private String bakM3u8;
    private String cdnType;
    private String flv;
    private boolean isSelected;
    private String m3u8;
    private String m3u81;
    private String m3u82;
    private String m3u83;
    private PLVBitrateVO multirateModel;
    private String quickLiveEnabled;
    private String quickLiveUrl;

    public String getAudioFlv() {
        return this.audioFlv;
    }

    public String getAudioM3u8() {
        return this.audioM3u8;
    }

    public String getBakFlv() {
        return this.bakFlv;
    }

    public String getBakM3u8() {
        return this.bakM3u8;
    }

    public String getCdnType() {
        return this.cdnType;
    }

    public String getFlv() {
        return this.flv;
    }

    public String getM3u8() {
        return this.m3u8;
    }

    public String getM3u81() {
        return this.m3u81;
    }

    public String getM3u82() {
        return this.m3u82;
    }

    public String getM3u83() {
        return this.m3u83;
    }

    public PLVBitrateVO getMultirateModel() {
        return this.multirateModel;
    }

    public String getQuickLiveEnabled() {
        return this.quickLiveEnabled;
    }

    public String getQuickLiveUrl() {
        return this.quickLiveUrl;
    }

    public boolean isQuickLiveEnabled() {
        return "Y".equals(this.quickLiveEnabled);
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setAudioFlv(String str) {
        this.audioFlv = str;
    }

    public void setAudioM3u8(String str) {
        this.audioM3u8 = str;
    }

    public void setBakFlv(String str) {
        this.bakFlv = str;
    }

    public void setBakM3u8(String str) {
        this.bakM3u8 = str;
    }

    public void setCdnType(String str) {
        this.cdnType = str;
    }

    public void setFlv(String str) {
        this.flv = str;
    }

    public void setM3u8(String str) {
        this.m3u8 = str;
    }

    public void setM3u81(String str) {
        this.m3u81 = str;
    }

    public void setM3u82(String str) {
        this.m3u82 = str;
    }

    public void setM3u83(String str) {
        this.m3u83 = str;
    }

    public void setMultirateModel(PLVBitrateVO pLVBitrateVO) {
        this.multirateModel = pLVBitrateVO;
    }

    public void setQuickLiveEnabled(String str) {
        this.quickLiveEnabled = str;
    }

    public void setQuickLiveUrl(String str) {
        this.quickLiveUrl = str;
    }

    public void setSelected(boolean z2) {
        this.isSelected = z2;
    }
}
