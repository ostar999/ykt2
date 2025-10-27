package com.plv.livescenes.playback.vo;

import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.model.PLVPlaybackVO;
import com.plv.livescenes.model.PLVPlaybackVO2;
import com.plv.livescenes.playback.video.PLVPlaybackListType;

/* loaded from: classes5.dex */
public class PLVPlaybackDataVO {
    private String channelSessionId;
    private String fileId;
    private String fileUrl;
    private String liveType;
    private final boolean localPlay;
    private String originSessionId;
    private PLVPlaybackListType playbackListType;
    private String videoId;
    private String videoPoolId;

    /* renamed from: com.plv.livescenes.playback.vo.PLVPlaybackDataVO$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$plv$livescenes$playback$video$PLVPlaybackListType;

        static {
            int[] iArr = new int[PLVPlaybackListType.values().length];
            $SwitchMap$com$plv$livescenes$playback$video$PLVPlaybackListType = iArr;
            try {
                iArr[PLVPlaybackListType.TEMP_STORE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$plv$livescenes$playback$video$PLVPlaybackListType[PLVPlaybackListType.PLAYBACK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$plv$livescenes$playback$video$PLVPlaybackListType[PLVPlaybackListType.VOD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public PLVPlaybackDataVO(PLVPlaybackListType pLVPlaybackListType, PLVPlaybackVO.DataBean dataBean) {
        this.playbackListType = pLVPlaybackListType;
        this.liveType = dataBean.getLiveType();
        this.fileUrl = dataBean.getFileUrl();
        this.channelSessionId = dataBean.getChannelSessionId();
        this.originSessionId = dataBean.getOriginSessionId();
        this.fileId = dataBean.getFileId();
        this.videoPoolId = dataBean.getVideoPoolId();
        this.videoId = dataBean.getVideoId();
        this.localPlay = false;
    }

    public String getChannelSessionId() {
        return this.channelSessionId;
    }

    public String getFileId() {
        return this.fileId;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public String getLiveType() {
        return this.liveType;
    }

    public String getOriginSessionId() {
        return this.originSessionId;
    }

    public PLVPlaybackListType getPlaybackListType() {
        return this.playbackListType;
    }

    public String getPptPlayId() {
        return AnonymousClass1.$SwitchMap$com$plv$livescenes$playback$video$PLVPlaybackListType[this.playbackListType.ordinal()] != 1 ? this.videoId : this.fileId;
    }

    public String getPptPlayType() {
        return AnonymousClass1.$SwitchMap$com$plv$livescenes$playback$video$PLVPlaybackListType[this.playbackListType.ordinal()] != 1 ? "playback" : AliyunLogCommon.SubModule.RECORD;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public String getVideoPoolId() {
        return this.videoPoolId;
    }

    public boolean isLocalPlay() {
        return this.localPlay;
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(this.fileUrl);
    }

    public PLVPlaybackDataVO setChannelSessionId(String str) {
        this.channelSessionId = str;
        return this;
    }

    public PLVPlaybackDataVO setFileId(String str) {
        this.fileId = str;
        return this;
    }

    public PLVPlaybackDataVO setFileUrl(String str) {
        this.fileUrl = str;
        return this;
    }

    public PLVPlaybackDataVO setLiveType(String str) {
        this.liveType = str;
        return this;
    }

    public PLVPlaybackDataVO setOriginSessionId(String str) {
        this.originSessionId = str;
        return this;
    }

    public PLVPlaybackDataVO setPlaybackListType(PLVPlaybackListType pLVPlaybackListType) {
        this.playbackListType = pLVPlaybackListType;
        return this;
    }

    public PLVPlaybackDataVO setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public PLVPlaybackDataVO setVideoPoolId(String str) {
        this.videoPoolId = str;
        return this;
    }

    public PLVPlaybackDataVO(PLVPlaybackListType pLVPlaybackListType, PLVPlaybackVO2 pLVPlaybackVO2) {
        this.playbackListType = pLVPlaybackListType;
        if ("Y".equals(pLVPlaybackVO2.getData().getHasRecordFile())) {
            this.fileUrl = (String) PLVSugarUtil.firstNotNull(pLVPlaybackVO2.getData().getRecordFile().getM3u8(), pLVPlaybackVO2.getData().getRecordFile().getMp4());
            this.channelSessionId = pLVPlaybackVO2.getData().getRecordFile().getChannelSessionId();
            this.originSessionId = pLVPlaybackVO2.getData().getRecordFile().getOriginSessionId();
            this.fileId = pLVPlaybackVO2.getData().getRecordFile().getFileId();
            this.videoPoolId = pLVPlaybackVO2.getData().getRecordFile().getFileId();
            this.videoId = pLVPlaybackVO2.getData().getRecordFile().getFileId();
        } else if ("Y".equals(pLVPlaybackVO2.getData().getHasPlaybackVideo())) {
            this.fileUrl = pLVPlaybackVO2.getData().getTargetPlaybackVideo().getFileUrl();
            this.channelSessionId = pLVPlaybackVO2.getData().getTargetPlaybackVideo().getChannelSessionId();
            this.originSessionId = pLVPlaybackVO2.getData().getTargetPlaybackVideo().getOriginSessionId();
            this.fileId = pLVPlaybackVO2.getData().getTargetPlaybackVideo().getFileId();
            this.videoPoolId = pLVPlaybackVO2.getData().getTargetPlaybackVideo().getVideoPoolId();
            this.videoId = pLVPlaybackVO2.getData().getTargetPlaybackVideo().getVideoId();
        }
        this.localPlay = false;
    }

    public PLVPlaybackDataVO(PLVPlaybackLocalCacheVO pLVPlaybackLocalCacheVO) {
        this.playbackListType = pLVPlaybackLocalCacheVO.getPlaybackListType();
        this.liveType = pLVPlaybackLocalCacheVO.getLiveType();
        this.fileUrl = pLVPlaybackLocalCacheVO.getVideoPath();
        this.channelSessionId = pLVPlaybackLocalCacheVO.getChannelSessionId();
        this.originSessionId = pLVPlaybackLocalCacheVO.getOriginSessionId();
        this.fileId = pLVPlaybackLocalCacheVO.getVideoPoolId();
        this.videoPoolId = pLVPlaybackLocalCacheVO.getVideoPoolId();
        this.videoId = pLVPlaybackLocalCacheVO.getVideoId();
        this.localPlay = true;
    }
}
