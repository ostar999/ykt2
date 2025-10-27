package com.plv.livescenes.download;

import androidx.annotation.Nullable;
import com.plv.livescenes.model.PLVPlaybackVideoVO;
import com.plv.livescenes.model.PLVTempStorePlaybackVideoVO;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVPlaybackCacheVO {
    public static final int RESULT_HAVE_LOCAL_PLAYBACK_CACHE = 1;
    public static final int RESULT_LOCAL_PLAYBACK_CACHE_ERROR = 3;
    public static final int RESULT_NOT_LOCAL_PLAYBACK_CACHE = 2;
    private String duration;
    private String firstImage;
    private String jsPath;
    private String pptDir;
    private final int result;
    private String title;
    private String videoPath;
    private Long videoSize;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ValidatePlaybackCacheReturnType {
    }

    public PLVPlaybackCacheVO() {
        this.result = 2;
    }

    public PLVPlaybackCacheVO fillDataByPlaybackVideoVO(PLVPlaybackVideoVO pLVPlaybackVideoVO) {
        if (pLVPlaybackVideoVO != null && pLVPlaybackVideoVO.getData() != null) {
            setTitle(pLVPlaybackVideoVO.getData().getTitle());
            setDuration(pLVPlaybackVideoVO.getData().getDuration());
            setVideoSize(Long.valueOf(PLVDownloader.getVideoDownloadSize(pLVPlaybackVideoVO)));
            setFirstImage(pLVPlaybackVideoVO.getData().getFirstImage());
        }
        return this;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getFirstImage() {
        return this.firstImage;
    }

    @Nullable
    public String getJsPath() {
        return this.jsPath;
    }

    @Nullable
    public String getPptDir() {
        return this.pptDir;
    }

    public int getResult() {
        return this.result;
    }

    public String getTitle() {
        return this.title;
    }

    @Nullable
    public String getVideoPath() {
        return this.videoPath;
    }

    public Long getVideoSize() {
        return this.videoSize;
    }

    public PLVPlaybackCacheVO setDuration(String str) {
        this.duration = str;
        return this;
    }

    public PLVPlaybackCacheVO setFirstImage(String str) {
        this.firstImage = str;
        return this;
    }

    public void setJsPath(String str) {
        this.jsPath = str;
    }

    public void setPptDir(String str) {
        this.pptDir = str;
    }

    public PLVPlaybackCacheVO setTitle(String str) {
        this.title = str;
        return this;
    }

    public void setVideoPath(String str) {
        this.videoPath = str;
    }

    public PLVPlaybackCacheVO setVideoSize(Long l2) {
        this.videoSize = l2;
        return this;
    }

    public PLVPlaybackCacheVO(int i2) {
        this(i2, null, null, null, null);
    }

    public PLVPlaybackCacheVO(int i2, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable PLVPlaybackVideoVO pLVPlaybackVideoVO) {
        this.result = i2;
        this.jsPath = str;
        this.videoPath = str2;
        this.pptDir = str3;
        fillDataByPlaybackVideoVO(pLVPlaybackVideoVO);
    }

    public PLVPlaybackCacheVO fillDataByPlaybackVideoVO(PLVTempStorePlaybackVideoVO pLVTempStorePlaybackVideoVO) {
        if (pLVTempStorePlaybackVideoVO != null && pLVTempStorePlaybackVideoVO.getData() != null) {
            setTitle(pLVTempStorePlaybackVideoVO.getData().getFilename());
            setDuration(pLVTempStorePlaybackVideoVO.getData().getDuration());
            setVideoSize(Long.valueOf(PLVDownloader.getVideoDownloadSize(pLVTempStorePlaybackVideoVO)));
        }
        return this;
    }
}
