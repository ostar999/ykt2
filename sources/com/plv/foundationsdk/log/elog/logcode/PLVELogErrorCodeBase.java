package com.plv.foundationsdk.log.elog.logcode;

/* loaded from: classes4.dex */
public class PLVELogErrorCodeBase {
    private static final int SHIFT = 7;

    public static class FirstTag {
        private static final int COUNT = 2;
        public static final int SHIFT = 2;
        private static final int TAIL = (int) Math.pow(10.0d, 2.0d);

        public static int getFirstTagCode(int i2) {
            return i2 * TAIL;
        }
    }

    public static class Module {
        public static final int CHAT;
        private static final int COUNT = 2;
        public static final int HTTP;
        public static final int LINK;
        public static final int PPT;
        public static final int RTMP;
        public static final int SHIFT = 4;
        public static final int SOCKET;
        private static final int TAIL;
        public static final int VIDEO_DOWNLOAD;
        public static final int VIDEO_PLAY;
        public static final int VIDEO_RECORD;
        public static final int VIDEO_UPLOAD;

        static {
            int iPow = (int) Math.pow(10.0d, 4.0d);
            TAIL = iPow;
            VIDEO_PLAY = iPow * 0;
            VIDEO_DOWNLOAD = iPow * 1;
            VIDEO_UPLOAD = iPow * 2;
            VIDEO_RECORD = iPow * 3;
            RTMP = iPow * 4;
            CHAT = iPow * 5;
            LINK = iPow * 6;
            PPT = iPow * 7;
            SOCKET = iPow * 9;
            HTTP = iPow * 10;
        }
    }

    public static class Platform {
        public static final int ANDROID_PLATFORM;
        private static final int COUNT = 1;
        private static final int SHIFT = 6;
        private static final int TAIL;

        static {
            int iPow = (int) Math.pow(10.0d, 6.0d);
            TAIL = iPow;
            ANDROID_PLATFORM = iPow * 1;
        }
    }
}
