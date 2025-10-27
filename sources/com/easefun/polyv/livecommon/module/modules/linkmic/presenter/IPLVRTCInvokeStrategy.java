package com.easefun.polyv.livecommon.module.modules.linkmic.presenter;

import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicListShowMode;
import com.plv.linkmic.model.PLVLinkMicJoinSuccess;

/* loaded from: classes3.dex */
public interface IPLVRTCInvokeStrategy {

    public interface OnBeforeJoinChannelListener {
        void onBeforeJoinChannel(PLVLinkMicListShowMode linkMicListShowMode);
    }

    public interface OnJoinLinkMicListener {
        void onJoinLinkMic(PLVLinkMicJoinSuccess data);
    }

    public interface OnLeaveLinkMicListener {
        void onLeaveLinkMic();
    }

    void destroy();

    boolean isJoinChannel();

    boolean isJoinLinkMic();

    void setFirstScreenLinkMicId(String linkMicId, boolean mute);

    void setJoinLinkMic();

    void setLeaveLinkMic();

    void setLiveEnd();

    void setLiveStart();

    void setOnBeforeJoinChannelListener(OnBeforeJoinChannelListener li);

    void setOnLeaveLinkMicListener(OnLeaveLinkMicListener li);
}
