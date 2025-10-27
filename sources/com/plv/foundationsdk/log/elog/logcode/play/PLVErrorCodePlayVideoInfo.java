package com.plv.foundationsdk.log.elog.logcode.play;

/* loaded from: classes4.dex */
public class PLVErrorCodePlayVideoInfo extends PLVErrorCodePlayBase {
    private static final String EVENT = "videoInfoError";
    private static final int FIRST_TAG = 5;

    public interface ErrorCode {
        public static final int PLAYBACK_INFO_CODE_ERROR = PLVErrorCodePlayVideoInfo.getCode(8);
        public static final int PLAYBACK_INFO_DATA_ERROR = PLVErrorCodePlayVideoInfo.getCode(9);
        public static final int PLAYBACK_INFO_VID_ERROR = PLVErrorCodePlayVideoInfo.getCode(10);
        public static final int PLAYBACK_INFO_LOAD_ERROR = PLVErrorCodePlayVideoInfo.getCode(11);
        public static final int LIVE_INFO_LOAD_ERROR = PLVErrorCodePlayVideoInfo.getCode(12);
        public static final int LIVE_INFO_DATA_ERROR = PLVErrorCodePlayVideoInfo.getCode(13);
        public static final int LIVE_INFO_CODE_ERROR = PLVErrorCodePlayVideoInfo.getCode(14);
        public static final int LIVE_RESTRICT_WATCH_ERROR = PLVErrorCodePlayVideoInfo.getCode(15);
        public static final int LIVE_RESTRICT_LOAD_ERROR = PLVErrorCodePlayVideoInfo.getCode(16);
        public static final int LIVE_STATUS_DATA_PARSE_ERROR = PLVErrorCodePlayVideoInfo.getCode(17);
        public static final int LIVE_STATUS_CODE_ERROR = PLVErrorCodePlayVideoInfo.getCode(18);
        public static final int LIVE_STATUS_LOAD_ERROR = PLVErrorCodePlayVideoInfo.getCode(19);
        public static final int LIVE_UNKNOWN_CHANNEL_TYPE = PLVErrorCodePlayVideoInfo.getCode(20);
        public static final int LIVE_UNKNOWN_LIVE_STATUS = PLVErrorCodePlayVideoInfo.getCode(21);
        public static final int LIVE_CHANNEL_JSON_PARSE_ERROR = PLVErrorCodePlayVideoInfo.getCode(22);
        public static final int LIVE_FAILED_TO_REQUEST_SESSION_ID = PLVErrorCodePlayVideoInfo.getCode(23);
    }

    public interface SecondCode {
        public static final int LIVE_CHANNEL_JSON_PARSE_ERROR = 22;
        public static final int LIVE_FAILED_TO_REQUEST_SESSION_ID = 23;
        public static final int LIVE_INFO_CODE_ERROR = 14;
        public static final int LIVE_INFO_DATA_ERROR = 13;
        public static final int LIVE_INFO_LOAD_ERROR = 12;
        public static final int LIVE_RESTRICT_LOAD_ERROR = 16;
        public static final int LIVE_RESTRICT_WATCH_ERROR = 15;
        public static final int LIVE_STATUS_CODE_ERROR = 18;
        public static final int LIVE_STATUS_DATA_PARSE_ERROR = 17;
        public static final int LIVE_STATUS_LOAD_ERROR = 19;
        public static final int LIVE_UNKNOWN_CHANNEL_TYPE = 20;
        public static final int LIVE_UNKNOWN_LIVE_STATUS = 21;
        public static final int PLAYBACK_INFO_CODE_ERROR = 8;
        public static final int PLAYBACK_INFO_DATA_ERROR = 9;
        public static final int PLAYBACK_INFO_LOAD_ERROR = 11;
        public static final int PLAYBACK_INFO_VID_ERROR = 10;
    }

    public PLVErrorCodePlayVideoInfo(int i2) {
        super(i2);
    }

    public static int getCode(int i2) {
        return new PLVErrorCodePlayVideoInfo(i2).createModuleCode() + i2;
    }

    public static String getMessage(int i2) {
        return new PLVErrorCodePlayVideoInfo(i2).getErrorMessage(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 5;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        switch (i2) {
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
                return "视频加载失败，请联系管理员";
            case 10:
            case 15:
                return "视频无法播放，请联系管理员";
            case 16:
            case 19:
                return "数据获取出错";
            case 17:
                return "数据解析出错";
            case 18:
                return "数据校验出错";
            case 20:
                return "未知的直播频道类型";
            default:
                return "unknown";
        }
    }
}
