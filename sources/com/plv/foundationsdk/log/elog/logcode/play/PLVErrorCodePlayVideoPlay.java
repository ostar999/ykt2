package com.plv.foundationsdk.log.elog.logcode.play;

/* loaded from: classes4.dex */
public class PLVErrorCodePlayVideoPlay extends PLVErrorCodePlayBase {
    private static final String EVENT = "videoPlayError";
    private static final int FIRST_TAG = 4;

    public interface ErrorCode {
        public static final int VIDEO_PLAY_ERROR = PLVErrorCodePlayVideoPlay.getCode(1);
        public static final int VIDEO_TIMEOUT_ERROR = PLVErrorCodePlayVideoPlay.getCode(2);
        public static final int VIDEO_PATH_ERROR = PLVErrorCodePlayVideoPlay.getCode(7);
        public static final int VIDEO_ID_ERROR = PLVErrorCodePlayVideoPlay.getCode(10);
        public static final int VIDEO_HEADERS_ERROR = PLVErrorCodePlayVideoPlay.getCode(22);
        public static final int VIDEO_HOST_ERROR = PLVErrorCodePlayVideoPlay.getCode(23);
    }

    public interface SecondCode {
        public static final int VIDEO_HEADERS_ERROR = 22;
        public static final int VIDEO_HOST_ERROR = 23;
        public static final int VIDEO_ID_ERROR = 10;
        public static final int VIDEO_PATH_ERROR = 7;
        public static final int VIDEO_PLAY_ERROR = 1;
        public static final int VIDEO_TIMEOUT_ERROR = 2;
    }

    public PLVErrorCodePlayVideoPlay(int i2) {
        super(i2);
    }

    public static int getCode(int i2) {
        return new PLVErrorCodePlayVideoPlay(i2).createModuleCode() + i2;
    }

    public static String getMessage(int i2) {
        return new PLVErrorCodePlayVideoPlay(i2).getErrorMessage(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 4;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        return i2 != 1 ? (i2 == 2 || i2 == 7 || i2 == 10 || i2 == 22 || i2 == 23) ? "视频无法播放，请联系管理员" : "unknown" : "视频无法播放，请检查网络设置";
    }
}
