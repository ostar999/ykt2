package com.easefun.polyv.livecommon.module.modules.linkmic.presenter;

import android.app.Activity;
import android.text.TextUtils;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicListShowModeGetter;
import com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy;
import com.easefun.polyv.livescenes.linkmic.IPolyvLinkMicManager;
import com.easefun.polyv.livescenes.linkmic.manager.PolyvLinkMicConfig;
import com.plv.linkmic.model.PLVLinkMicJoinSuccess;
import com.plv.livescenes.linkmic.IPLVLinkMicManager;
import com.plv.livescenes.linkmic.listener.PLVLinkMicEventListener;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;

/* loaded from: classes3.dex */
public class PLVRTCWatchEnabledStrategy implements IPLVRTCInvokeStrategy {
    private String firstScreenLinkMicId = "";
    private boolean isJoinChannel;
    private boolean isJoinLinkMic;
    private PLVLinkMicEventListener linkMicEventListener;
    private IPolyvLinkMicManager linkMicManager;
    private PLVLinkMicPresenter linkMicPresenter;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private IPLVRTCInvokeStrategy.OnBeforeJoinChannelListener onBeforeJoinChannelListener;
    private IPLVRTCInvokeStrategy.OnJoinLinkMicListener onJoinLinkMicListener;
    private OnJoinRTCChannelWatchListener onJoinRTCChannelWatchListener;
    private IPLVRTCInvokeStrategy.OnLeaveLinkMicListener onLeaveLinkMicListener;

    public interface OnJoinRTCChannelWatchListener {
        void onJoinRTCChannelWatch();
    }

    public PLVRTCWatchEnabledStrategy(PLVLinkMicPresenter linkMicPresenter, final IPolyvLinkMicManager linkMicManager, IPLVLiveRoomDataManager ipliveRoomDataManager, OnJoinRTCChannelWatchListener joinRTCChannelWatchListener, IPLVRTCInvokeStrategy.OnJoinLinkMicListener joinLinkMicListener) {
        this.linkMicPresenter = linkMicPresenter;
        this.linkMicManager = linkMicManager;
        this.liveRoomDataManager = ipliveRoomDataManager;
        this.onJoinLinkMicListener = joinLinkMicListener;
        this.onJoinRTCChannelWatchListener = joinRTCChannelWatchListener;
        setLinkMicEventListener();
    }

    private void setLinkMicEventListener() {
        this.linkMicPresenter.pendingActionInCaseLinkMicEngineInitializing(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVRTCWatchEnabledStrategy.1
            @Override // java.lang.Runnable
            public void run() {
                PLVRTCWatchEnabledStrategy.this.linkMicManager.addEventHandler(PLVRTCWatchEnabledStrategy.this.linkMicEventListener = new PLVLinkMicEventListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVRTCWatchEnabledStrategy.1.1
                    @Override // com.plv.linkmic.PLVLinkMicEventHandler
                    public void onJoinChannelSuccess(String uid) {
                        PLVRTCWatchEnabledStrategy.this.isJoinChannel = true;
                        PLVRTCWatchEnabledStrategy.this.linkMicManager.switchRoleToAudience();
                        PLVRTCWatchEnabledStrategy.this.onJoinRTCChannelWatchListener.onJoinRTCChannelWatch();
                        IPLVLinkMicContract.IPLVLinkMicView linkMicView = PLVRTCWatchEnabledStrategy.this.linkMicPresenter.getLinkMicView();
                        if (linkMicView != null) {
                            linkMicView.onRTCPrepared();
                        }
                    }

                    @Override // com.plv.linkmic.PLVLinkMicEventHandler
                    public void onUserJoined(String uid) {
                        if (!PolyvLinkMicConfig.getInstance().isPureRtcOnlySubscribeMainScreenVideo() || TextUtils.isEmpty(PLVRTCWatchEnabledStrategy.this.firstScreenLinkMicId) || PLVRTCWatchEnabledStrategy.this.firstScreenLinkMicId.equals(uid)) {
                            return;
                        }
                        PLVRTCWatchEnabledStrategy.this.linkMicManager.muteRemoteVideo(uid, true);
                    }
                });
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void destroy() {
        PLVLinkMicEventListener pLVLinkMicEventListener = this.linkMicEventListener;
        if (pLVLinkMicEventListener != null) {
            this.linkMicManager.removeEventHandler(pLVLinkMicEventListener);
            this.linkMicEventListener = null;
        }
        this.linkMicPresenter = null;
        this.linkMicManager = null;
        this.liveRoomDataManager = null;
        this.onBeforeJoinChannelListener = null;
        this.onJoinRTCChannelWatchListener = null;
        this.onJoinLinkMicListener = null;
        this.onLeaveLinkMicListener = null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public boolean isJoinChannel() {
        return this.isJoinChannel;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public boolean isJoinLinkMic() {
        return this.isJoinLinkMic;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setFirstScreenLinkMicId(String linkMicId, boolean mute) {
        if (PolyvLinkMicConfig.getInstance().isPureRtcOnlySubscribeMainScreenVideo()) {
            this.linkMicManager.muteRemoteVideo(this.firstScreenLinkMicId, true);
            this.firstScreenLinkMicId = linkMicId;
            this.linkMicManager.muteRemoteVideo(linkMicId, mute);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setJoinLinkMic() {
        this.isJoinLinkMic = true;
        this.linkMicManager.switchRoleToBroadcaster();
        this.linkMicManager.sendJoinSuccessMsg(this.liveRoomDataManager.getSessionId(), new IPLVLinkMicManager.OnSendJoinSuccessMsgListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVRTCWatchEnabledStrategy.2
            @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager.OnSendJoinSuccessMsgListener
            public void onSendJoinSuccessMsg(PLVLinkMicJoinSuccess joinSuccess) {
                PLVRTCWatchEnabledStrategy.this.onJoinLinkMicListener.onJoinLinkMic(joinSuccess);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setLeaveLinkMic() {
        this.linkMicManager.switchRoleToAudience();
        this.linkMicManager.muteLocalVideo(true);
        this.linkMicManager.sendJoinLeaveMsg(this.liveRoomDataManager.getSessionId());
        this.onLeaveLinkMicListener.onLeaveLinkMic();
        this.isJoinLinkMic = false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setLiveEnd() {
        this.linkMicManager.leaveChannel();
        this.linkMicPresenter.leaveChannel();
        this.isJoinChannel = false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setLiveStart() {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity != null) {
            topActivity.setVolumeControlStream(0);
        }
        if (this.isJoinChannel) {
            return;
        }
        this.onBeforeJoinChannelListener.onBeforeJoinChannel(PLVLinkMicListShowModeGetter.getLeavedMicShowMode());
        this.linkMicManager.joinChannel();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setOnBeforeJoinChannelListener(IPLVRTCInvokeStrategy.OnBeforeJoinChannelListener li) {
        this.onBeforeJoinChannelListener = li;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setOnLeaveLinkMicListener(IPLVRTCInvokeStrategy.OnLeaveLinkMicListener li) {
        this.onLeaveLinkMicListener = li;
    }
}
