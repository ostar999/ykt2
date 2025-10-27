package com.plv.livescenes.streamer.manager;

import android.text.TextUtils;
import com.plv.livescenes.linkmic.vo.PLVLinkMicEngineParam;
import com.plv.livescenes.model.PLVLinkMicSipEvent;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.streamer.IPLVStreamerManager;
import com.plv.livescenes.streamer.listener.PLVStreamerEventListener;
import com.plv.livescenes.streamer.listener.PLVStreamerListener;
import com.plv.livescenes.streamer.mix.PLVRTCMixUser;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.user.PLVSocketUserConstant;
import io.reactivex.disposables.Disposable;
import java.util.List;

/* loaded from: classes5.dex */
class PLVLinkMicSipDecorator extends PLVStreamerManagerDecorator {
    private Disposable delay2MuteSIPVideo;
    private PLVStreamerEventListener sipEventHandler;

    public PLVLinkMicSipDecorator(IPLVStreamerManager iPLVStreamerManager) {
        super(iPLVStreamerManager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSipUser(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.endsWith("_sip");
    }

    @Override // com.plv.livescenes.streamer.manager.PLVStreamerManagerDecorator, com.plv.livescenes.streamer.IPLVStreamerManager
    public void addEventHandler(PLVStreamerEventListener pLVStreamerEventListener) {
        super.addEventHandler(new PLVStreamerEventListenerDecorator(pLVStreamerEventListener) { // from class: com.plv.livescenes.streamer.manager.PLVLinkMicSipDecorator.2
            @Override // com.plv.livescenes.streamer.manager.PLVStreamerEventListenerDecorator, com.plv.linkmic.PLVLinkMicEventHandler
            public void onUserMuteVideo(String str, boolean z2) {
                if (PLVLinkMicSipDecorator.this.isSipUser(str)) {
                    super.onUserMuteVideo(str, true);
                } else {
                    super.onUserMuteVideo(str, z2);
                }
            }
        });
    }

    @Override // com.plv.livescenes.streamer.manager.PLVStreamerManagerDecorator, com.plv.livescenes.streamer.IPLVStreamerManager
    public void destroy() {
        removeEventHandler(this.sipEventHandler);
        Disposable disposable = this.delay2MuteSIPVideo;
        if (disposable != null) {
            disposable.dispose();
        }
        super.destroy();
    }

    @Override // com.plv.livescenes.streamer.manager.PLVStreamerManagerDecorator, com.plv.livescenes.streamer.IPLVStreamerManager
    public void initEngine(PLVLinkMicEngineParam pLVLinkMicEngineParam, final PLVStreamerListener pLVStreamerListener) {
        super.initEngine(pLVLinkMicEngineParam, new PLVStreamerListener() { // from class: com.plv.livescenes.streamer.manager.PLVLinkMicSipDecorator.1
            @Override // com.plv.livescenes.streamer.listener.PLVStreamerListener
            public void onStreamerEngineCreatedSuccess() {
                pLVStreamerListener.onStreamerEngineCreatedSuccess();
                PLVLinkMicSipDecorator.this.sipEventHandler = new PLVStreamerEventListener() { // from class: com.plv.livescenes.streamer.manager.PLVLinkMicSipDecorator.1.1
                    @Override // com.plv.linkmic.PLVLinkMicEventHandler
                    public void onUserJoined(String str) {
                        super.onUserJoined(str);
                        if (PLVLinkMicSipDecorator.this.isSipUser(str)) {
                            PLVLinkMicSipEvent pLVLinkMicSipEvent = new PLVLinkMicSipEvent();
                            pLVLinkMicSipEvent.setUserId(str);
                            pLVLinkMicSipEvent.setNick("电话入会嘉宾");
                            pLVLinkMicSipEvent.setPic(PLVSocketUserConstant.STUDENT_AVATAR_URL_V2);
                            PLVSocketWrapper.getInstance().emit(PLVEventConstant.LinkMic.EVENT_ADD_GUEST_FROM_SIP, pLVLinkMicSipEvent);
                        }
                    }
                };
                PLVLinkMicSipDecorator pLVLinkMicSipDecorator = PLVLinkMicSipDecorator.this;
                pLVLinkMicSipDecorator.addEventHandler(pLVLinkMicSipDecorator.sipEventHandler);
            }

            @Override // com.plv.livescenes.streamer.listener.PLVStreamerListener
            public void onStreamerError(int i2, Throwable th) {
                pLVStreamerListener.onStreamerError(i2, th);
            }
        });
    }

    @Override // com.plv.livescenes.streamer.manager.PLVStreamerManagerDecorator, com.plv.livescenes.streamer.IPLVStreamerManager
    public void updateMixLayoutUsers(List<? extends PLVRTCMixUser> list) {
        for (PLVRTCMixUser pLVRTCMixUser : list) {
            if (isSipUser(pLVRTCMixUser.getUserId())) {
                pLVRTCMixUser.setHidden(true);
            }
        }
        super.updateMixLayoutUsers(list);
    }
}
