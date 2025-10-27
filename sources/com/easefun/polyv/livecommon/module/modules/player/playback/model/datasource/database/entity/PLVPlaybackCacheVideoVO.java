package com.easefun.polyv.livecommon.module.modules.player.playback.model.datasource.database.entity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.easefun.polyv.livecommon.module.modules.player.playback.model.enums.PLVPlaybackCacheDownloadStatusEnum;
import java.util.Locale;

@Entity(tableName = "playback_cache_video_table")
/* loaded from: classes3.dex */
public class PLVPlaybackCacheVideoVO {
    private String channelSessionId;
    private PLVPlaybackCacheDownloadStatusEnum downloadStatusEnum;
    private Long downloadedBytes;
    private Boolean enableDownload;
    private String firstImageUrl;
    private String jsPath;
    private String liveType;
    private String originSessionId;
    private String pptPath;

    @IntRange(from = 0, to = 100)
    private Integer progress;
    private String title;
    private Long totalBytes;
    private String videoDuration;
    private String videoId;
    private String videoPath;

    @NonNull
    @PrimaryKey
    private String videoPoolId;

    @Embedded
    private PLVPlaybackCacheViewerInfoVO viewerInfoVO = new PLVPlaybackCacheViewerInfoVO();

    @NonNull
    public static String bytesToFitSizeString(@Nullable Long byteNum) {
        if (byteNum == null) {
            return "-";
        }
        long jLongValue = byteNum.longValue();
        return jLongValue < 0 ? "-" : jLongValue < 1024 ? String.format(Locale.CHINA, "%.1fB", Double.valueOf(jLongValue)) : jLongValue < 1048576 ? String.format(Locale.CHINA, "%.1fK", Double.valueOf(jLongValue / 1024.0d)) : jLongValue < 1073741824 ? String.format(Locale.CHINA, "%.1fM", Double.valueOf(jLongValue / 1048576.0d)) : String.format(Locale.CHINA, "%.1fG", Double.valueOf(jLongValue / 1.073741824E9d));
    }

    public void clearDownloadStatus() {
        this.progress = 0;
        this.downloadedBytes = 0L;
        this.downloadStatusEnum = PLVPlaybackCacheDownloadStatusEnum.NOT_IN_DOWNLOAD_LIST;
        this.videoPath = null;
        this.jsPath = null;
        this.pptPath = null;
    }

    public PLVPlaybackCacheVideoVO copy() {
        PLVPlaybackCacheVideoVO pLVPlaybackCacheVideoVO = new PLVPlaybackCacheVideoVO();
        pLVPlaybackCacheVideoVO.setVideoPoolId(this.videoPoolId);
        pLVPlaybackCacheVideoVO.setVideoId(this.videoId);
        pLVPlaybackCacheVideoVO.setTitle(this.title);
        pLVPlaybackCacheVideoVO.setFirstImageUrl(this.firstImageUrl);
        pLVPlaybackCacheVideoVO.setVideoDuration(this.videoDuration);
        pLVPlaybackCacheVideoVO.setLiveType(this.liveType);
        pLVPlaybackCacheVideoVO.setChannelSessionId(this.channelSessionId);
        pLVPlaybackCacheVideoVO.setOriginSessionId(this.originSessionId);
        pLVPlaybackCacheVideoVO.setEnableDownload(this.enableDownload);
        pLVPlaybackCacheVideoVO.setProgress(this.progress);
        pLVPlaybackCacheVideoVO.setDownloadedBytes(this.downloadedBytes);
        pLVPlaybackCacheVideoVO.setTotalBytes(this.totalBytes);
        pLVPlaybackCacheVideoVO.setVideoPath(this.videoPath);
        pLVPlaybackCacheVideoVO.setPptPath(this.pptPath);
        pLVPlaybackCacheVideoVO.setJsPath(this.jsPath);
        pLVPlaybackCacheVideoVO.setDownloadStatusEnum(this.downloadStatusEnum);
        pLVPlaybackCacheVideoVO.setViewerInfoVO(this.viewerInfoVO.copy());
        return pLVPlaybackCacheVideoVO;
    }

    public String getChannelSessionId() {
        return this.channelSessionId;
    }

    public PLVPlaybackCacheDownloadStatusEnum getDownloadStatusEnum() {
        return this.downloadStatusEnum;
    }

    public Long getDownloadedBytes() {
        return this.downloadedBytes;
    }

    public String getFirstImageUrl() {
        return this.firstImageUrl;
    }

    public String getJsPath() {
        return this.jsPath;
    }

    public String getLiveType() {
        return this.liveType;
    }

    public String getOriginSessionId() {
        return this.originSessionId;
    }

    public String getPptPath() {
        return this.pptPath;
    }

    public Integer getProgress() {
        return this.progress;
    }

    public String getTitle() {
        return this.title;
    }

    public Long getTotalBytes() {
        return this.totalBytes;
    }

    public String getVideoDuration() {
        return this.videoDuration;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public String getVideoPoolId() {
        return this.videoPoolId;
    }

    public PLVPlaybackCacheViewerInfoVO getViewerInfoVO() {
        return this.viewerInfoVO;
    }

    public Boolean isEnableDownload() {
        return this.enableDownload;
    }

    public PLVPlaybackCacheVideoVO mergeFrom(PLVPlaybackCacheVideoVO old) {
        if (old == null) {
            return this;
        }
        if (getVideoId() == null) {
            setVideoId(old.getVideoId());
        }
        if (getTitle() == null) {
            setTitle(old.getTitle());
        }
        if (getFirstImageUrl() == null) {
            setFirstImageUrl(old.getFirstImageUrl());
        }
        if (getVideoDuration() == null) {
            setVideoDuration(old.getVideoDuration());
        }
        if (getLiveType() == null) {
            setLiveType(old.getLiveType());
        }
        if (getChannelSessionId() == null) {
            setChannelSessionId(old.getChannelSessionId());
        }
        if (getOriginSessionId() == null) {
            setOriginSessionId(old.getOriginSessionId());
        }
        if (isEnableDownload() == null) {
            setEnableDownload(old.isEnableDownload());
        }
        if (getProgress() == null) {
            setProgress(old.getProgress());
        }
        if (getDownloadedBytes() == null) {
            setDownloadedBytes(old.getDownloadedBytes());
        }
        if (getTotalBytes() == null) {
            setTotalBytes(old.getTotalBytes());
        }
        if (getVideoPath() == null) {
            setVideoPath(old.getVideoPath());
        }
        if (getPptPath() == null) {
            setPptPath(old.getPptPath());
        }
        if (getJsPath() == null) {
            setJsPath(old.getJsPath());
        }
        if (getDownloadStatusEnum() == null) {
            setDownloadStatusEnum(old.getDownloadStatusEnum());
        }
        if (getViewerInfoVO() == null) {
            setViewerInfoVO(old.getViewerInfoVO());
        }
        return this;
    }

    public void setChannelSessionId(String channelSessionId) {
        this.channelSessionId = channelSessionId;
    }

    public void setDownloadStatusEnum(PLVPlaybackCacheDownloadStatusEnum downloadStatusEnum) {
        this.downloadStatusEnum = downloadStatusEnum;
    }

    public void setDownloadedBytes(Long downloadedBytes) {
        this.downloadedBytes = downloadedBytes;
    }

    public void setEnableDownload(Boolean enableDownload) {
        this.enableDownload = enableDownload;
    }

    public void setFirstImageUrl(String firstImageUrl) {
        this.firstImageUrl = firstImageUrl;
    }

    public void setJsPath(String jsPath) {
        this.jsPath = jsPath;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public void setOriginSessionId(String originSessionId) {
        this.originSessionId = originSessionId;
    }

    public void setPptPath(String pptPath) {
        this.pptPath = pptPath;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalBytes(Long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setVideoPoolId(String videoPoolId) {
        this.videoPoolId = videoPoolId;
    }

    public void setViewerInfoVO(PLVPlaybackCacheViewerInfoVO viewerInfoVO) {
        this.viewerInfoVO = viewerInfoVO;
    }
}
