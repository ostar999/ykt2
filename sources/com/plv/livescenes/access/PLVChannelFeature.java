package com.plv.livescenes.access;

import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.livescenes.config.PLVLiveChannelType;

/* loaded from: classes2.dex */
public class PLVChannelFeature<T> {
    public static PLVChannelFeature<Boolean> LIVE_CHATROOM_MANAGER_CHAT;
    public static PLVChannelFeature<Boolean> STREAMER_ALONE_ALLOW_CHANGE_PUSH_RESOLUTION_RATIO;
    public static PLVChannelFeature<PLVLinkMicConstant.PushResolutionRatio> STREAMER_ALONE_DEFAULT_PUSH_RESOLUTION_RATIO;
    public static PLVChannelFeature<Boolean> STREAMER_BEAUTY_ENABLE;
    public static PLVChannelFeature<Boolean> STREAMER_GUEST_TRANSFER_SPEAKER_ENABLE;
    private final T defaultValue;
    public static PLVChannelFeature<PLVLiveChannelType> LIVE_CHANNEL_TYPE = new PLVChannelFeature<>(null);
    public static PLVChannelFeature<Long> LIVE_CHATROOM_RESTRICT_MAX_VIEWER = new PLVChannelFeature<>(-1L);

    static {
        Boolean bool = Boolean.FALSE;
        LIVE_CHATROOM_MANAGER_CHAT = new PLVChannelFeature<>(bool);
        STREAMER_BEAUTY_ENABLE = new PLVChannelFeature<>(bool);
        STREAMER_ALONE_ALLOW_CHANGE_PUSH_RESOLUTION_RATIO = new PLVChannelFeature<>(bool);
        STREAMER_ALONE_DEFAULT_PUSH_RESOLUTION_RATIO = new PLVChannelFeature<>(null);
        STREAMER_GUEST_TRANSFER_SPEAKER_ENABLE = new PLVChannelFeature<>(bool);
    }

    private PLVChannelFeature(T t2) {
        this.defaultValue = t2;
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public int hashCode() {
        return super.hashCode();
    }
}
