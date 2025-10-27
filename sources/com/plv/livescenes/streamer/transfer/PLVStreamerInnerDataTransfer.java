package com.plv.livescenes.streamer.transfer;

import android.text.TextUtils;
import com.plv.livescenes.document.model.PLVPPTStatus;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.streamer.listener.IPLVOnGetSessionIdInnerListener;
import com.plv.livescenes.streamer.listener.PLVStreamerEventListener;
import com.plv.livescenes.streamer.manager.PLVStreamerManager;
import com.plv.livescenes.streamer.transfer.PLVStreamerClassOffJudge;
import com.plv.socket.event.ppt.PLVFinishClassEvent;
import com.plv.socket.event.ppt.PLVOnSliceStartEvent;
import com.plv.socket.eventbus.ppt.PLVOnSliceStartEventBus;

/* loaded from: classes5.dex */
public class PLVStreamerInnerDataTransfer {
    private PLVStreamerClassOffJudge classOffJudge;
    private String colinMicType;
    private String curChannelId;
    private String curLinkMicSessionId;
    private PLVPPTStatus curPPTStatus;
    private boolean hasEmitFinishClassEventDuringOneLive;
    private int interactNumLimit;
    private boolean isLiveStreamingWhenLogin;
    private boolean isOnlyAudio;
    private PLVStreamerManager linkMicWrapper;
    private boolean liveTranscodingEnabled;
    private int maxBitrate;
    private String pushStreamUrl;
    private String role;

    public static class InstanceHolder {
        private static final PLVStreamerInnerDataTransfer INSTANCE = new PLVStreamerInnerDataTransfer();

        private InstanceHolder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void emitFinishClassEvent() {
        PLVStreamerManager pLVStreamerManager;
        if (!"teacher".equals(this.role) || this.hasEmitFinishClassEventDuringOneLive || (pLVStreamerManager = this.linkMicWrapper) == null) {
            return;
        }
        String strValueOf = String.valueOf(pLVStreamerManager.getLinkMicUid());
        String str = this.curChannelId;
        PLVSocketWrapper.getInstance().emit("message", new PLVFinishClassEvent(str, str, strValueOf));
        this.hasEmitFinishClassEventDuringOneLive = true;
    }

    public static PLVStreamerInnerDataTransfer getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void destroyByLinkMic() {
        PLVStreamerClassOffJudge pLVStreamerClassOffJudge = this.classOffJudge;
        if (pLVStreamerClassOffJudge != null) {
            pLVStreamerClassOffJudge.destroy();
        }
        PLVStreamerManager pLVStreamerManager = this.linkMicWrapper;
        if (pLVStreamerManager == null || !pLVStreamerManager.isLiveStreaming()) {
            return;
        }
        emitFinishClassEvent();
    }

    public void destroyBySocketManager() {
        PLVStreamerManager pLVStreamerManager = this.linkMicWrapper;
        if (pLVStreamerManager == null || !pLVStreamerManager.isLiveStreaming()) {
            return;
        }
        emitFinishClassEvent();
    }

    public int getInteractNumLimit() {
        return this.interactNumLimit;
    }

    public int getSupportedMaxBitrate() {
        int i2 = this.maxBitrate;
        if (i2 >= 720) {
            return 3;
        }
        return i2 >= 360 ? 2 : 1;
    }

    public void initByLinkMic(final PLVStreamerManager pLVStreamerManager) {
        this.linkMicWrapper = pLVStreamerManager;
        pLVStreamerManager.setLiveTranscodingEnabled(this.liveTranscodingEnabled);
        pLVStreamerManager.setPushStreamUrl(this.pushStreamUrl);
        pLVStreamerManager.setSupportedMaxBitrate(this.maxBitrate);
        pLVStreamerManager.addGetSessionIdFromServerListener(new IPLVOnGetSessionIdInnerListener() { // from class: com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer.1
            @Override // com.plv.livescenes.streamer.listener.IPLVOnGetSessionIdInnerListener
            public void onGetSessionId(String str, String str2, String str3, boolean z2) {
                int step;
                int autoId;
                int pageId;
                PLVStreamerInnerDataTransfer.this.curLinkMicSessionId = str;
                PLVStreamerInnerDataTransfer.this.curChannelId = str2;
                PLVPPTStatus pLVPPTStatus = PLVStreamerInnerDataTransfer.this.curPPTStatus;
                if (pLVPPTStatus != null) {
                    autoId = pLVPPTStatus.getAutoId();
                    pageId = pLVPPTStatus.getPageId();
                    step = pLVPPTStatus.getStep();
                } else {
                    step = 0;
                    autoId = 0;
                    pageId = 0;
                }
                PLVOnSliceStartEvent pLVOnSliceStartEvent = new PLVOnSliceStartEvent(new PLVOnSliceStartEvent.DataBean(autoId, 0, pageId, step), System.currentTimeMillis(), str2, str, 0L, str3);
                PLVSocketWrapper.getInstance().emit("message", pLVOnSliceStartEvent);
                PLVOnSliceStartEventBus.post(pLVOnSliceStartEvent);
            }
        });
        pLVStreamerManager.addEventHandler(new PLVStreamerEventListener() { // from class: com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer.2
            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onStreamPublished(String str, int i2) {
                PLVStreamerInnerDataTransfer.this.hasEmitFinishClassEventDuringOneLive = false;
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onStreamUnpublished(String str) {
                PLVStreamerInnerDataTransfer.this.emitFinishClassEvent();
            }
        });
        this.classOffJudge = new PLVStreamerClassOffJudge(pLVStreamerManager, new PLVStreamerClassOffJudge.OnJudgeAsClassOffListener() { // from class: com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer.3
            @Override // com.plv.livescenes.streamer.transfer.PLVStreamerClassOffJudge.OnJudgeAsClassOffListener
            public void onJudgeAsClassOff() {
                PLVStreamerInnerDataTransfer.this.emitFinishClassEvent();
                pLVStreamerManager.notifyClassOffWhenReconnectIfServerTimeout();
            }
        });
    }

    public boolean isAutoLinkToGuest() {
        return TextUtils.isEmpty(this.colinMicType) || "auto".equals(this.colinMicType);
    }

    public boolean isLiveStreamingWhenLogin() {
        return this.isLiveStreamingWhenLogin;
    }

    public boolean isOnlyAudio() {
        return this.isOnlyAudio;
    }

    public void setColinMicType(String str) {
        this.colinMicType = str;
    }

    public void setInteractNumLimit(int i2) {
        this.interactNumLimit = i2;
    }

    public void setLiveStreamingWhenLogin(boolean z2) {
        this.isLiveStreamingWhenLogin = z2;
    }

    public void setLiveTranscodingEnabled(boolean z2) {
        this.liveTranscodingEnabled = z2;
    }

    public void setOnlyAudio(boolean z2) {
        this.isOnlyAudio = z2;
    }

    public void setPPTStatusForOnSliceStartEvent(PLVPPTStatus pLVPPTStatus) {
        this.curPPTStatus = pLVPPTStatus;
    }

    public void setPushStreamUrl(String str) {
        this.pushStreamUrl = str;
    }

    public void setRole(String str) {
        this.role = str;
    }

    public void setSupportedMaxBitrate(int i2) {
        this.maxBitrate = i2;
    }

    private PLVStreamerInnerDataTransfer() {
        this.liveTranscodingEnabled = true;
        this.hasEmitFinishClassEventDuringOneLive = false;
        this.isOnlyAudio = false;
        this.isLiveStreamingWhenLogin = false;
    }
}
