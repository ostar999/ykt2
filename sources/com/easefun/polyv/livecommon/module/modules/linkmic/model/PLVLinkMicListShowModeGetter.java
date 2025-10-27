package com.easefun.polyv.livecommon.module.modules.linkmic.model;

import com.easefun.polyv.livescenes.linkmic.manager.PolyvLinkMicConfig;

/* loaded from: classes3.dex */
public class PLVLinkMicListShowModeGetter {
    public static PLVLinkMicListShowMode getJoinedMicShowMode(boolean isAudio) {
        return PolyvLinkMicConfig.getInstance().isPureRtcWatchEnabled() ? PolyvLinkMicConfig.getInstance().isPureRtcOnlySubscribeMainScreenVideo() ? isAudio ? PLVLinkMicListShowMode.SHOW_FIRST_SCREEN : PLVLinkMicListShowMode.SHOW_FIRST_SCREEN_AND_SELF : isAudio ? PLVLinkMicListShowMode.SHOW_TEACHER_AND_GUEST : PLVLinkMicListShowMode.SHOW_ALL : isAudio ? PLVLinkMicListShowMode.SHOW_TEACHER_AND_GUEST : PLVLinkMicListShowMode.SHOW_ALL;
    }

    public static PLVLinkMicListShowMode getLeavedMicShowMode() {
        return PolyvLinkMicConfig.getInstance().isPureRtcOnlySubscribeMainScreenVideo() ? PLVLinkMicListShowMode.SHOW_FIRST_SCREEN : PLVLinkMicListShowMode.SHOW_ALL;
    }
}
