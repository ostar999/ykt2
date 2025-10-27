package com.plv.business.api.common.player;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVPlayErrorReason {
    public static final int AUDIO_URL_EMPTY = 30020;
    public static final int BITRATE_ERROR = 20015;
    public static final int CAN_NOT_CHANGE_AUDIO = 30022;
    public static final int CAN_NOT_CHANGE_BITRATE = 30009;
    public static final int CAN_NOT_CHANGE_HLS_SPEED = 30010;
    public static final int CAN_NOT_CHANGE_VIDEO = 30023;
    public static final int CHANGE_BITRATE_NOT_EXIST = 30014;
    public static final int CHANGE_EQUAL_BITRATE = 30007;
    public static final int CHANGE_EQUAL_HLS_SPEED = 30008;
    public static final int EXCEPTION_COMPLETION = 30017;
    public static final int HLS2_URL_ERROR = 30016;
    public static final int HLS_15X_ERROR = 30004;
    public static final int HLS_15X_INDEX_EMPTY = 30003;
    public static final int HLS_15X_URL_ERROR = 30005;
    public static final int HLS_SPEED_TYPE_NULL = 30001;
    public static final int HLS_URL_ERROR = 30013;
    public static final int LOADING_VIDEO_ERROR = 30011;
    public static final int LOCAL_AUDIO_ERROR = 30024;
    public static final int LOCAL_VIEWO_ERROR = 20007;
    public static final int M3U8_15X_LINK_NUM_ERROR = 30006;
    public static final int M3U8_LINK_NUM_ERROR = 20018;
    public static final int MP4_LINK_NUM_ERROR = 20017;
    public static final int NETWORK_DENIED = 20003;
    public static final int NOT_LOCAL_AUDIO = 30021;
    public static final int NOT_LOCAL_VIDEO = 30002;
    public static final int NOT_PERMISSION = 20009;
    public static final int OUT_FLOW = 20004;
    public static final int QUESTION_ERROR = 30012;

    @Deprecated
    public static final int QUESTION_JSON_ERROR = 20012;

    @Deprecated
    public static final int QUESTION_JSON_PARSE_ERROR = 20013;
    public static final int SOURCE_URL_EMPTY = 30019;
    public static final int START_ERROR = 20008;
    public static final int TIMEOUT_FLOW = 20005;
    public static final int TOKEN_NULL = 30015;
    public static final int USER_TOKEN_ERROR = 20010;
    public static final int VIDEO_ERROR = 20001;
    public static final int VIDEO_NULL = 20016;
    public static final int VIDEO_STATUS_ERROR = 20011;
    public static final int VID_ERROR = 20014;
    public static final int WRITE_EXTERNAL_STORAGE_DENIED = 30018;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayErrorReason {
    }
}
