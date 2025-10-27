package com.aliyun.player.alivcplayerexpand.constants;

import com.aliyun.player.IPlayer;
import com.aliyun.vod.log.core.AliyunLogCommon;
import java.io.File;

/* loaded from: classes2.dex */
public class GlobalPlayerConfig {
    public static boolean AUTH_TYPE_CHECKED;
    public static final String CACHE_DIR_PATH;
    public static final String DOWNLOAD_DIR_PATH;
    public static final String ENCRYPT_DIR_PATH;
    public static boolean IS_BARRAGE;
    public static boolean IS_MARQUEE;
    public static boolean IS_PICTRUE;
    public static boolean IS_TRAILER;
    public static boolean IS_VIDEO;
    public static boolean IS_WATERMARK;
    public static boolean LIVE_STS_TYPE_CHECKED;
    public static boolean MPS_TYPE_CHECKED;
    public static final String SNAP_SHOT_PATH;
    public static boolean STS_TYPE_CHECKED;
    public static boolean URL_TYPE_CHECKED;
    public static MUTIRATE mCurrentMutiRate;
    public static PLAYTYPE mCurrentPlayType;
    public static boolean mEnableHardDecodeType;
    public static String mLiveExpiration;
    public static String mLivePlayAuth;
    public static String mLiveStsAccessKeyId;
    public static String mLiveStsAccessKeySecret;
    public static String mLiveStsApp;
    public static String mLiveStsDomain;
    public static String mLiveStsSecurityToken;
    public static String mLiveStsStream;
    public static String mLiveStsUrl;
    public static IPlayer.MirrorMode mMirrorMode;
    public static String mMpsAccessKeyId;
    public static String mMpsAccessKeySecret;
    public static String mMpsAuthInfo;
    public static String mMpsHlsUriToken;
    public static String mMpsRegion;
    public static String mMpsSecurityToken;
    public static String mPlayAuth;
    public static int mPreviewTime;
    public static String mRegion;
    public static IPlayer.RotateMode mRotateMode;
    public static String mStsAccessKeyId;
    public static String mStsAccessKeySecret;
    public static String mStsSecurityToken;
    public static String mUrlPath;
    public static String mVid;

    public static class Intent_Key {
        public static final String LOCAL_VIDEO_PATH = "local_video_path";
        public static final String NEED_ONLY_FULL_SCREEN = "need_only_full_screen";
    }

    public enum MUTIRATE {
        RATE_400(400),
        RATE_900(900),
        RATE_1500(1500),
        RATE_3000(3000),
        RATE_3500(3500),
        RATE_6000(6000);

        private int value;

        MUTIRATE(int i2) {
            this.value = i2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum PLAYTYPE {
        DEFAULT,
        URL,
        STS,
        MPS,
        AUTH,
        LIVE_STS
    }

    public static class PlayCacheConfig {
        public static final boolean DEFAULT_ENABLE_CACHE = false;
        public static final int DEFAULT_MAX_DURATION_S = 100;
        public static final int DEFAULT_MAX_SIZE_MB = 200;
        public static String mDir = "";
        public static boolean mEnableCache = false;
        public static int mMaxDurationS = 100;
        public static int mMaxSizeMB = 200;
    }

    public static class PlayConfig {
        public static final boolean DEFAULT_ENABLE_CLEAR_WHEN_STOP = false;
        public static final boolean DEFAULT_ENABLE_SEI = false;
        public static final int DEFAULT_HIGH_BUFFER_DURATION;
        public static final int DEFAULT_MAX_BUFFER_DURATION;
        public static final int DEFAULT_MAX_DELAY_TIME;
        public static final int DEFAULT_NETWORK_RETRY_COUNT = 2;
        public static final int DEFAULT_NETWORK_TIMEOUT = 15000;
        public static final int DEFAULT_PROBE_SIZE = -1;
        public static final int DEFAULT_START_BUFFER_DURATION;
        private static final boolean IS_ARTC_URL;
        private static final boolean IS_ARTP_URL;
        private static int MAX_DELAY_TIME_RESULT;
        public static boolean mAutoSwitchOpen;
        public static boolean mEnableAccurateSeekModule;
        public static boolean mEnableClearWhenStop;
        public static boolean mEnablePlayBackground;
        public static boolean mEnableSei;
        public static int mHighBufferDuration;
        public static String mHttpProxy;
        public static int mMaxBufferDuration;
        public static int mMaxDelayTime;
        public static int mMaxProbeSize;
        public static int mNetworkRetryCount;
        public static int mNetworkTimeout;
        public static String mReferrer;
        public static int mStartBufferDuration;

        static {
            PLAYTYPE playtype = GlobalPlayerConfig.mCurrentPlayType;
            PLAYTYPE playtype2 = PLAYTYPE.URL;
            boolean z2 = playtype == playtype2 && GlobalPlayerConfig.mUrlPath.startsWith("artc");
            IS_ARTC_URL = z2;
            boolean z3 = GlobalPlayerConfig.mCurrentPlayType == playtype2 && GlobalPlayerConfig.mUrlPath.startsWith("artp");
            IS_ARTP_URL = z3;
            if (z2) {
                MAX_DELAY_TIME_RESULT = 0;
            } else if (z3) {
                MAX_DELAY_TIME_RESULT = 100;
            } else {
                MAX_DELAY_TIME_RESULT = 5000;
            }
            int i2 = z2 ? 10 : 500;
            DEFAULT_START_BUFFER_DURATION = i2;
            int i3 = z2 ? 10 : 3000;
            DEFAULT_HIGH_BUFFER_DURATION = i3;
            int i4 = z2 ? 150 : 50000;
            DEFAULT_MAX_BUFFER_DURATION = i4;
            int i5 = MAX_DELAY_TIME_RESULT;
            DEFAULT_MAX_DELAY_TIME = i5;
            mStartBufferDuration = i2;
            mHighBufferDuration = i3;
            mMaxBufferDuration = i4;
            mMaxDelayTime = i5;
            mMaxProbeSize = -1;
            mNetworkTimeout = 15000;
            mNetworkRetryCount = 2;
            mEnableSei = false;
            mEnableClearWhenStop = false;
            mAutoSwitchOpen = false;
            mEnableAccurateSeekModule = false;
            mEnablePlayBackground = false;
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("snapShot");
        String str = File.separator;
        sb.append(str);
        SNAP_SHOT_PATH = sb.toString();
        CACHE_DIR_PATH = "cache" + str;
        DOWNLOAD_DIR_PATH = AliyunLogCommon.SubModule.download + str;
        ENCRYPT_DIR_PATH = "encrypt" + str;
        mMirrorMode = IPlayer.MirrorMode.MIRROR_MODE_NONE;
        mEnableHardDecodeType = true;
        mRotateMode = IPlayer.RotateMode.ROTATE_0;
        mVid = "";
        mRegion = "cn-shanghai";
        mPreviewTime = 10;
        mStsAccessKeyId = "";
        mStsSecurityToken = "";
        mStsAccessKeySecret = "";
        mLiveStsAccessKeyId = "";
        mLiveStsSecurityToken = "";
        mLiveStsAccessKeySecret = "";
        mLiveExpiration = "";
        mLivePlayAuth = "";
        mLiveStsDomain = "";
        mLiveStsApp = "";
        mLiveStsStream = "";
        mPlayAuth = "";
        mUrlPath = "";
        mLiveStsUrl = "";
        mMpsRegion = "";
        mMpsAuthInfo = "";
        mMpsSecurityToken = "";
        mMpsAccessKeyId = "";
        mMpsAccessKeySecret = "";
        mMpsHlsUriToken = "";
        mCurrentPlayType = PLAYTYPE.DEFAULT;
        mCurrentMutiRate = MUTIRATE.RATE_3000;
        IS_VIDEO = false;
        IS_PICTRUE = false;
        IS_TRAILER = false;
        IS_BARRAGE = true;
        IS_WATERMARK = false;
        IS_MARQUEE = false;
        URL_TYPE_CHECKED = false;
        STS_TYPE_CHECKED = false;
        MPS_TYPE_CHECKED = false;
        AUTH_TYPE_CHECKED = false;
        LIVE_STS_TYPE_CHECKED = false;
    }
}
