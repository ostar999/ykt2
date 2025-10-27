package com.plv.business.model.video;

/* loaded from: classes4.dex */
public class PLVPlaybackVideoParams extends PLVBaseVideoParams {
    public static final String ENABLE_ACCURATE_SEEK = "enable_accurate_seek";
    public static final String ENABLE_AUTO_PLAY_TEMP_STORE_VIDEO = "enable_auto_play_temp_store_video";
    public static final String LOCAL_VIDEO_CACHE_LIST = "local_video_cache_list";
    public static final String VIDEO_LISTTYPE = "video_listtype";

    private PLVPlaybackVideoParams(String str, String str2) {
        super(str, str2);
    }

    private PLVPlaybackVideoParams(String str, String str2, String str3) {
        super(str, str2, str3);
    }

    public PLVPlaybackVideoParams(String str, String str2, String str3, String str4) {
        super(str, str2, str3, str4);
    }
}
