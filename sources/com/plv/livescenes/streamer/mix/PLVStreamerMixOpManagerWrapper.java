package com.plv.livescenes.streamer.mix;

import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.linkmic.model.PLVRTCMixActionVO;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.linkmic.repository.PLVLinkMicHttpRequestException;
import com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVStreamerMixOpManagerWrapper implements IPLVStreamerMixOpManager {
    private static final String MIX_ACTION_END = "end";
    private static final String MIX_ACTION_START = "start";
    private static final String MIX_ACTION_UPDATE = "update";
    public static final int MIX_LAYOUT_TYPE_SINGLE = 1;
    public static final int MIX_LAYOUT_TYPE_SPEAKER = 3;
    public static final int MIX_LAYOUT_TYPE_TILE = 2;
    private static final int MIX_STATUS_IDLE = 0;
    private static final int MIX_STATUS_STARTED = 1;
    private static final String TAG = "PLVStreamerMixOpManagerWrapper";
    public static final int USER_MIX_INPUT_TYPE_AUDIO_ONLY = 2;
    public static final int USER_MIX_INPUT_TYPE_VIDEO_AUDIO = 0;
    public static final int USER_MIX_INPUT_TYPE_VIDEO_ONLY = 1;
    public static final int USER_RENDER_MODE_FILL = 0;
    public static final int USER_RENDER_MODE_FIT = 1;
    public static final int USER_RENDER_MODE_FIT_BLACK_BG = 2;
    public static final int USER_STREAM_TYPE_CAMERA = 0;
    public static final int USER_STREAM_TYPE_SCREEN = 1;
    private String appId;
    private String appSecret;
    private PLVLinkMicDataRepository linkMicDataRepository;
    private int mixStatus = 0;
    private final PLVRTCMixActionVO mixActionVO = new PLVRTCMixActionVO();

    public PLVStreamerMixOpManagerWrapper(PLVLinkMicDataRepository pLVLinkMicDataRepository) {
        this.linkMicDataRepository = pLVLinkMicDataRepository;
    }

    private void invokeMixToCdn(@Nullable final IPLVStreamerMixOpManager.OnMixActionListener onMixActionListener) {
        this.linkMicDataRepository.mixToCdn(this.appId, this.appSecret, this.mixActionVO, new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<String>() { // from class: com.plv.livescenes.streamer.mix.PLVStreamerMixOpManagerWrapper.1
            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
            public void onFail(PLVLinkMicHttpRequestException pLVLinkMicHttpRequestException) {
                new Exception(pLVLinkMicHttpRequestException).printStackTrace();
                IPLVStreamerMixOpManager.OnMixActionListener onMixActionListener2 = onMixActionListener;
                if (onMixActionListener2 != null) {
                    onMixActionListener2.onFail(pLVLinkMicHttpRequestException);
                }
            }

            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener
            public void onSuccess(String str) {
                PLVCommonLog.d(PLVStreamerMixOpManagerWrapper.TAG, "invokeMixToCdn->onSuccess() called with: data = [" + str + StrPool.BRACKET_END);
                IPLVStreamerMixOpManager.OnMixActionListener onMixActionListener2 = onMixActionListener;
                if (onMixActionListener2 != null) {
                    onMixActionListener2.onSuccess();
                }
            }
        });
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void destroy() {
        PLVCommonLog.d(TAG, "destroy() called");
        stopMix(null);
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void init(String str, String str2, int i2, String str3, boolean z2, IPLVStreamerMixOpManager.EncodeParam encodeParam, String str4) {
        this.appId = str;
        this.appSecret = str2;
        this.mixActionVO.setRoomId(i2);
        PLVRTCMixActionVO.OutputParamVO outputParamVO = new PLVRTCMixActionVO.OutputParamVO();
        outputParamVO.setStreamId(str3);
        outputParamVO.setPureAudioStream(z2 ? 1 : 0);
        this.mixActionVO.setOutputParam(outputParamVO);
        PLVRTCMixActionVO.EncodeParamVO encodeParamVO = new PLVRTCMixActionVO.EncodeParamVO();
        encodeParamVO.setAudioSampleRate(encodeParam.audioSampleRate);
        encodeParamVO.setAudioBitrate(encodeParam.audioBitrate);
        encodeParamVO.setAudioChannels(encodeParam.audioChannels);
        encodeParamVO.setVideoWidth(encodeParam.videoWidth);
        encodeParamVO.setVideoHeight(encodeParam.videoHeight);
        encodeParamVO.setVideoBitrate(encodeParam.videoBitrate);
        encodeParamVO.setVideoFramerate(encodeParam.videoFramerate);
        encodeParamVO.setVideoGop(encodeParam.videoGop);
        encodeParamVO.setBackgroundColor(encodeParam.backgroundColor);
        encodeParamVO.setAudioCodec(encodeParam.audioCodec);
        this.mixActionVO.setEncodeParam(encodeParamVO);
        this.mixActionVO.setUserList(null);
        this.mixActionVO.setMixLayoutType(2);
        PLVRTCMixActionVO.PublishCdnParamVO publishCdnParamVO = new PLVRTCMixActionVO.PublishCdnParamVO();
        publishCdnParamVO.setCdnUrl(str4);
        this.mixActionVO.setPublishCdnParam(publishCdnParamVO);
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void setLiveTranscodingEnable(boolean z2) {
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void setMixLayoutType(int i2) {
        PLVCommonLog.d(TAG, "setMixLayoutType() called with: mixLayoutType = [" + i2 + StrPool.BRACKET_END);
        this.mixActionVO.setMixLayoutType(i2);
        if (this.mixStatus == 1) {
            invokeMixToCdn();
        }
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void startMix(IPLVStreamerMixOpManager.MixUser mixUser, IPLVStreamerMixOpManager.OnMixActionListener onMixActionListener) {
        PLVCommonLog.d(TAG, "startMix() called");
        this.mixStatus = 1;
        this.mixActionVO.setAction("start");
        ArrayList arrayList = new ArrayList(1);
        PLVRTCMixActionVO.UserListVO userListVO = new PLVRTCMixActionVO.UserListVO();
        userListVO.setMixInputType(mixUser.mixInputType);
        userListVO.setStreamType(mixUser.streamType);
        userListVO.setRenderMode(mixUser.renderMode);
        userListVO.setUserId(mixUser.userId);
        arrayList.add(userListVO);
        this.mixActionVO.setUserList(arrayList);
        invokeMixToCdn(onMixActionListener);
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void stopMix(IPLVStreamerMixOpManager.OnMixActionListener onMixActionListener) {
        PLVCommonLog.d(TAG, "stopMix() called");
        this.mixStatus = 0;
        this.mixActionVO.setAction("end");
        invokeMixToCdn(onMixActionListener);
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void updateMixUser(List<IPLVStreamerMixOpManager.MixUser> list) {
        if (this.mixStatus == 0) {
            return;
        }
        PLVCommonLog.d(TAG, "updateMixUser() called with: mixUserList = [" + list + StrPool.BRACKET_END);
        this.mixActionVO.setAction("update");
        ArrayList arrayList = new ArrayList(list.size());
        for (IPLVStreamerMixOpManager.MixUser mixUser : list) {
            PLVRTCMixActionVO.UserListVO userListVO = new PLVRTCMixActionVO.UserListVO();
            userListVO.setStreamType(mixUser.streamType);
            userListVO.setUserId(mixUser.userId);
            userListVO.setRenderMode(mixUser.renderMode);
            userListVO.setMixInputType(mixUser.mixInputType);
            userListVO.setHidden(mixUser.hidden);
            arrayList.add(userListVO);
        }
        this.mixActionVO.setUserList(arrayList);
        invokeMixToCdn();
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void updateSEIFrameTimeStamp(String str) {
    }

    @Override // com.plv.livescenes.streamer.mix.IPLVStreamerMixOpManager
    public void updateVideoEncodeParam(int i2, int i3, int i4) {
        String str = TAG;
        PLVCommonLog.d(str, "updateVideoEncodeParam() called with: videoWidth = [" + i2 + "], videoHeight = [" + i3 + "], bitrate = [" + i4 + StrPool.BRACKET_END);
        PLVRTCMixActionVO.EncodeParamVO encodeParam = this.mixActionVO.getEncodeParam();
        encodeParam.setVideoWidth(i2);
        encodeParam.setVideoHeight(i3);
        encodeParam.setVideoBitrate(i4);
        int i5 = this.mixStatus;
        if (i5 == 0) {
            PLVCommonLog.d(str, "updateVideoEncodeParam(), mixStatue=MIX_STATUS_IDLE");
        } else if (i5 == 1) {
            this.mixActionVO.setAction("update");
            invokeMixToCdn();
        }
    }

    private void invokeMixToCdn() {
        invokeMixToCdn(null);
    }
}
