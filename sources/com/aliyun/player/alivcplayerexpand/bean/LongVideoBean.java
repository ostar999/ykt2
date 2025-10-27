package com.aliyun.player.alivcplayerexpand.bean;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes2.dex */
public class LongVideoBean implements Serializable {
    private String censorStatus;
    private String coverUrl;
    private String creationTime;
    private String description;
    private List<DotBean> dotList;
    private boolean downloaded;
    private boolean downloading;
    private String duration;
    private String firstFrameUrl;
    private String isHomePage;
    private String isRecommend;
    private boolean isVip;
    private String saveUrl;
    private boolean selected;
    private String size;
    private String snapshotStatus;
    private String sort;
    private String status;
    private String tags;
    private String title;
    private String total;
    private String transcodeStatus;
    private String tvCoverUrl;
    private String tvId;
    private String tvName;
    private String videoId;
    private String watchDuration;
    private int watchPercent;

    public boolean equals(Object obj) {
        return TextUtils.isEmpty(getVideoId()) ? super.equals(obj) : this.videoId.equals(((LongVideoBean) obj).videoId);
    }

    public String getCensorStatus() {
        return this.censorStatus;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public String getCreationTime() {
        return this.creationTime;
    }

    public String getDescription() {
        return this.description;
    }

    public List<DotBean> getDot() {
        return this.dotList;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getFirstFrameUrl() {
        return this.firstFrameUrl;
    }

    public String getIsHomePage() {
        return this.isHomePage;
    }

    public String getIsRecommend() {
        return this.isRecommend;
    }

    public boolean getIsVip() {
        return this.isVip;
    }

    public String getSaveUrl() {
        return this.saveUrl;
    }

    public String getSize() {
        return this.size;
    }

    public String getSnapshotStatus() {
        return this.snapshotStatus;
    }

    public String getSort() {
        if (this.sort == null) {
            this.sort = "1";
        }
        return this.sort;
    }

    public String getStatus() {
        return this.status;
    }

    public String getTags() {
        return this.tags;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTotal() {
        return this.total;
    }

    public String getTranscodeStatus() {
        return this.transcodeStatus;
    }

    public String getTvCoverUrl() {
        return this.tvCoverUrl;
    }

    public String getTvId() {
        return this.tvId;
    }

    public String getTvName() {
        return this.tvName;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public String getWatchDuration() {
        return this.watchDuration;
    }

    public int getWatchPercent() {
        return this.watchPercent;
    }

    public boolean isDownloaded() {
        return this.downloaded;
    }

    public boolean isDownloading() {
        return this.downloading;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setCensorStatus(String str) {
        this.censorStatus = str;
    }

    public void setCoverUrl(String str) {
        this.coverUrl = str;
    }

    public void setCreationTime(String str) {
        this.creationTime = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setDot(List<DotBean> list) {
        this.dotList = list;
    }

    public void setDownloaded(boolean z2) {
        this.downloaded = z2;
    }

    public void setDownloading(boolean z2) {
        this.downloading = z2;
    }

    public void setDuration(String str) {
        this.duration = str;
    }

    public void setFirstFrameUrl(String str) {
        this.firstFrameUrl = str;
    }

    public void setIsHomePage(String str) {
        this.isHomePage = str;
    }

    public void setIsRecommend(String str) {
        this.isRecommend = str;
    }

    public void setIsVip(boolean z2) {
        this.isVip = z2;
    }

    public void setSaveUrl(String str) {
        this.saveUrl = str;
    }

    public void setSelected(boolean z2) {
        this.selected = z2;
    }

    public void setSize(String str) {
        this.size = str;
    }

    public void setSnapshotStatus(String str) {
        this.snapshotStatus = str;
    }

    public void setSort(String str) {
        this.sort = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setTags(String str) {
        this.tags = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setTotal(String str) {
        this.total = str;
    }

    public void setTranscodeStatus(String str) {
        this.transcodeStatus = str;
    }

    public void setTvCoverUrl(String str) {
        this.tvCoverUrl = str;
    }

    public void setTvId(String str) {
        this.tvId = str;
    }

    public void setTvName(String str) {
        this.tvName = str;
    }

    public void setVideoId(String str) {
        this.videoId = str;
    }

    public void setWatchDuration(String str) {
        this.watchDuration = str;
    }

    public void setWatchPercent(int i2) {
        this.watchPercent = i2;
    }
}
