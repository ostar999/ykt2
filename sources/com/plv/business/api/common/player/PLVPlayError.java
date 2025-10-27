package com.plv.business.api.common.player;

import cn.hutool.core.text.CharPool;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVPlayError {
    public static final int AUDIO_URL_EMPTY = 30020;
    public static final int BITRATE_ERROR = 20015;
    public static final int CAN_NOT_CHANGE_AUDIO = 30022;
    public static final int CAN_NOT_CHANGE_BITRATE = 30009;
    public static final int CAN_NOT_CHANGE_HLS_SPEED = 30010;
    public static final int CAN_NOT_CHANGE_VIDEO = 30023;
    public static final int CHANGE_BITRATE_NOT_EXIST = 30014;
    public static final int CHANGE_EQUAL_BITRATE = 30007;
    public static final int CHANGE_EQUAL_HLS_SPEED = 30008;
    public static final int ERROR_HEADERS_IS_NULL = -1006;
    public static final int ERROR_NETWORK_NOT_AVAILABLE = -1010;
    public static final int ERROR_OPTION_KEY_HOST_EMPTY = -1002;
    public static final int ERROR_PLAYPATH_IS_NULL = -1000;
    public static final int ERROR_REQUEST_TIMEOUT = -1020;
    public static final int EXCEPTION_COMPLETION = 30017;
    public static final int HLS2_URL_ERROR = 30016;
    public static final int HLS_15X_ERROR = 30004;
    public static final int HLS_15X_INDEX_EMPTY = 30003;
    public static final int HLS_15X_URL_ERROR = 30005;
    public static final int HLS_SPEED_TYPE_NULL = 30001;
    public static final int HLS_URL_ERROR = 30013;
    public static final int INNER_PLAY_ERROR = -10000;
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
    public static final int PLAY_STAGE_HEADAD = 1;
    public static final int PLAY_STAGE_MAIN_LIVE = 1002;
    public static final int PLAY_STAGE_MAIN_VOD = 1001;
    public static final int PLAY_STAGE_TAILAD = 3;
    public static final int PLAY_STAGE_TEASER = 2;
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
    public final int errorCode;
    public String errorDescribe;
    public final String playPath;
    public final int playStage;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayErrorCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayStage {
    }

    public PLVPlayError(String str, int i2, String str2, int i3) {
        this.playPath = str == null ? "" : str;
        this.errorCode = i2;
        this.errorDescribe = str2;
        this.playStage = i3;
    }

    private static String getErrorDescribe(int i2) {
        return i2 != -1020 ? i2 != -1006 ? i2 != -1002 ? i2 != -1000 ? i2 != 200 ? PLVErrorMessageUtils.getPlayErrorMessage(i2) : "Valid Play" : "PlayPath Is Null" : "Host Is Empty" : "Headers Is Null" : "Request Timeout";
    }

    public static PLVPlayError toErrorObj(String str, int i2, int i3) {
        return toErrorObj(str, i2, getErrorDescribe(i2), i3);
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public boolean isMainStage() {
        int i2 = this.playStage;
        return i2 == 1001 || i2 == 1002;
    }

    public String toString() {
        return "PLVPlayError{playPath='" + this.playPath + CharPool.SINGLE_QUOTE + ", errorCode=" + this.errorCode + ", errorDescribe='" + this.errorDescribe + CharPool.SINGLE_QUOTE + ", playStage=" + this.playStage + '}';
    }

    public static PLVPlayError toErrorObj(String str, int i2, String str2, int i3) {
        return new PLVPlayError(str, i2, str2, i3);
    }
}
