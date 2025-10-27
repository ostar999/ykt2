package com.aliyun.player.aliyunplayerbase.net;

/* loaded from: classes2.dex */
public class ServiceCommon {
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static int RESPONSE_SUCCESS = 200;
    public static String HOST = "https://alivc-demo.aliyuncs.com";
    public static String GET_VIDEO_PLAY_AUTH = HOST + "/player/getVideoPlayAuth";
    public static String GET_VIDEO_PLAY_STS = HOST + "/player/getVideoSts";
    public static String GET_VIDEO_PLAY_MPS = HOST + "/player/getVideoMps";
    public static String GET_VIDEO_PLAY_INFO = HOST + "/player/getVideoPlayInfo";
    public static String GET_VIDEO_PLAY_LIST = HOST + "/player/getVideoList";
    public static String GET_VIDEO_DEFAULT_LIST = HOST + "/demo/getVideoList?cateId=1000025282";
    public static String GET_RANDOM_USER = "https://alivc-demo.aliyuncs.com/user/randomUser";
    public static String GET_VIDEO_LIST_INFO = "https://alivc-demo.aliyuncs.com/vod/getRecommendVideoList";
    public static String GET_LIVE_PLAY_STS = HOST + "/player/getLiveEncryptSts";

    public interface RequestKey {
        public static final String FORM_KEY_ID = "id";
        public static final String FORM_KEY_PACKAGE_NAME = "PACKAGE_NAME";
        public static final String FORM_KEY_PAGE_INDEX = "pageIndex";
        public static final String FORM_KEY_PAGE_SIZE = "pageSize";
        public static final String FORM_KEY_TOKEN = "token";
    }
}
