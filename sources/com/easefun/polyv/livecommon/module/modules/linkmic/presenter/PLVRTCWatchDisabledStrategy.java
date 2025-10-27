package com.easefun.polyv.livecommon.module.modules.linkmic.presenter;

import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicListShowModeGetter;
import com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy;
import com.easefun.polyv.livescenes.linkmic.IPolyvLinkMicManager;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.linkmic.model.PLVLinkMicJoinSuccess;
import com.plv.livescenes.linkmic.IPLVLinkMicManager;
import com.plv.livescenes.linkmic.listener.PLVLinkMicEventListener;

/* loaded from: classes3.dex */
public class PLVRTCWatchDisabledStrategy implements IPLVRTCInvokeStrategy {
    private static final String TAG = "PLVRTCWatchDisabledStrategy";
    private boolean isJoinLinkMic;
    private PLVLinkMicEventListener linkMicEventListener;
    private IPolyvLinkMicManager linkMicManager;
    private PLVLinkMicPresenter linkMicPresenter;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private IPLVRTCInvokeStrategy.OnBeforeJoinChannelListener onBeforeJoinChannelListener;
    private IPLVRTCInvokeStrategy.OnJoinLinkMicListener onJoinLinkMicListener;
    private IPLVRTCInvokeStrategy.OnLeaveLinkMicListener onLeaveLinkMicListener;

    /* renamed from: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVRTCWatchDisabledStrategy$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            PLVRTCWatchDisabledStrategy.this.linkMicManager.addEventHandler(PLVRTCWatchDisabledStrategy.this.linkMicEventListener = new PLVLinkMicEventListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVRTCWatchDisabledStrategy.1.1
                @Override // com.plv.linkmic.PLVLinkMicEventHandler
                public void onJoinChannelSuccess(String uid) {
                    PLVRTCWatchDisabledStrategy.this.isJoinLinkMic = true;
                    PLVRTCWatchDisabledStrategy.this.linkMicManager.switchRoleToBroadcaster();
                    PLVCommonLog.d(PLVRTCWatchDisabledStrategy.TAG, "PolyvLinkMicEventListenerImpl.onJoinChannelSuccess");
                    PLVRTCWatchDisabledStrategy.this.linkMicManager.sendJoinSuccessMsg(PLVRTCWatchDisabledStrategy.this.liveRoomDataManager.getSessionId(), new IPLVLinkMicManager.OnSendJoinSuccessMsgListener() { // from class: com.easefun.polyv.livecommon.module.modules.linkmic.presenter.PLVRTCWatchDisabledStrategy.1.1.1
                        @Override // com.plv.livescenes.linkmic.IPLVLinkMicManager.OnSendJoinSuccessMsgListener
                        public void onSendJoinSuccessMsg(PLVLinkMicJoinSuccess joinSuccess) {
                            PLVRTCWatchDisabledStrategy.this.onJoinLinkMicListener.onJoinLinkMic(joinSuccess);
                        }
                    });
                }
            });
        }
    }

    public PLVRTCWatchDisabledStrategy(PLVLinkMicPresenter linkMicPresenter, final IPolyvLinkMicManager linkMicManager, IPLVLiveRoomDataManager ipliveRoomDataManager, IPLVRTCInvokeStrategy.OnJoinLinkMicListener joinLinkMicListener) {
        this.linkMicPresenter = linkMicPresenter;
        this.linkMicManager = linkMicManager;
        this.liveRoomDataManager = ipliveRoomDataManager;
        this.onJoinLinkMicListener = joinLinkMicListener;
        setLinkMicEventListener();
    }

    private void setLinkMicEventListener() {
        this.linkMicPresenter.pendingActionInCaseLinkMicEngineInitializing(new AnonymousClass1());
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
        this.onJoinLinkMicListener = null;
        this.onLeaveLinkMicListener = null;
        this.onBeforeJoinChannelListener = null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public boolean isJoinChannel() {
        return this.isJoinLinkMic;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public boolean isJoinLinkMic() {
        return this.isJoinLinkMic;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setFirstScreenLinkMicId(String linkMicId, boolean mute) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setJoinLinkMic() {
        this.onBeforeJoinChannelListener.onBeforeJoinChannel(PLVLinkMicListShowModeGetter.getJoinedMicShowMode(this.linkMicPresenter.getIsAudioLinkMic()));
        this.linkMicManager.joinChannel();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setLeaveLinkMic() {
        this.linkMicManager.leaveChannel();
        this.linkMicManager.sendJoinLeaveMsg(this.liveRoomDataManager.getSessionId());
        this.linkMicPresenter.leaveChannel();
        this.onLeaveLinkMicListener.onLeaveLinkMic();
        this.isJoinLinkMic = false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setLiveEnd() {
        this.linkMicManager.leaveChannel();
        this.linkMicPresenter.leaveChannel();
        this.isJoinLinkMic = false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.presenter.IPLVRTCInvokeStrategy
    public void setLiveStart() {
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
