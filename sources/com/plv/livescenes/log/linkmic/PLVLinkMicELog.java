package com.plv.livescenes.log.linkmic;

import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.livescenes.log.PLVStatisticsBaseLive;

/* loaded from: classes4.dex */
public class PLVLinkMicELog extends PLVStatisticsBaseLive {
    private static final String LINK_MIC_MODULE = "link";

    public interface LinkMicTraceLogEvent {
        public static final String ADJUST_RECODING_VOLUME = "adjustRecordingSignalVolume";
        public static final String ENABLE_LOCAL_AUDIO = "enableLocalAudio";
        public static final String ENABLE_LOCAL_VIDEO = "enableLocalVideo";
        public static final String JOIN_CHANNEL = "joinChannel";
        public static final String LEAVE_CHANNEL = "leaveChannel";
        public static final String OCCUR_ERROR = "linkMicDidOccurError";
        public static final String ON_LIVE_STREAMING_START = "onLiveStreamingStart";
        public static final String ON_SERVER_TIME_OUT_DUE_TO_NET_BROKEN = "onServerTimeoutDueToNetBroken";
        public static final String ON_TOKEN_EXPIRED = "onTokenExpired";
        public static final String PERMISSION_DENIED = "permissionDenied";
        public static final String REQUEST_LINK_MIC_TOKEN = "requestLinkMicToken";
        public static final String SET_BITRATE = "setBitrate";
        public static final String START_LIVE_STREAM = "startLiveStream";
        public static final String STOP_LIVE_STREAM = "stopLiveStream";
        public static final String SWITCH_CAMERA = "switchCamera";
        public static final String USER_CANCEL_LINK_MIC = "waitingUserDidCancelLinkMic";
        public static final String USER_CLOSE_LINK_MIC = "joinedUserDidCloseLinkMic";
    }

    public PLVLinkMicELog(PLVLogFileBase pLVLogFileBase, String str) {
        super(pLVLogFileBase, str);
        this.module = "link";
    }

    @Override // com.plv.foundationsdk.model.log.PLVStatisticsBase
    public boolean isNeedBatches() {
        return true;
    }
}
