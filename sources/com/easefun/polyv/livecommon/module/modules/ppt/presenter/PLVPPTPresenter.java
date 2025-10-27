package com.easefun.polyv.livecommon.module.modules.ppt.presenter;

import androidx.annotation.Nullable;
import com.easefun.polyv.businesssdk.model.ppt.PolyvPPTAuthentic;
import com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVPPTContract;
import com.easefun.polyv.livescenes.log.ppt.PolyvPPTElog;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.log.PLVELogSender;
import com.plv.livescenes.log.ppt.PLVPPTElog;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.event.ppt.PLVOnSliceIDEvent;
import com.plv.socket.event.ppt.PLVOnSliceStartEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class PLVPPTPresenter implements IPLVPPTContract.IPLVPPTPresenter {
    private static final int MSG_DELAY_TIME = 5000;
    private static final String TAG = "PLVPPTPresenter";
    private Disposable delaySendLoginEventDisposable;
    private int delayTime = 5000;
    private PLVSocketMessageObserver.OnMessageListener followTeacherPptVideoLocationListener;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;

    @Nullable
    private IPLVPPTContract.IPLVPPTView view;

    /* JADX INFO: Access modifiers changed from: private */
    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVPPTContract.IPLVPPTPresenter
    public void destroy() {
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.followTeacherPptVideoLocationListener);
        dispose(this.delaySendLoginEventDisposable);
        this.view = null;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVPPTContract.IPLVPPTPresenter
    public void init(final IPLVPPTContract.IPLVPPTView view) {
        this.view = view;
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.ppt.presenter.PLVPPTPresenter.1
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                PolyvPPTAuthentic polyvPPTAuthentic;
                if ("onSliceStart".equals(event) || "onSliceDraw".equals(event) || "onSliceControl".equals(event) || "onSliceOpen".equals(event) || "onSliceID".equals(event)) {
                    IPLVPPTContract.IPLVPPTView iPLVPPTView = view;
                    if (iPLVPPTView != null) {
                        iPLVPPTView.hideLoading();
                    }
                    if (PLVPPTPresenter.this.delayTime > 0) {
                        message = message.substring(0, message.lastIndexOf(125)) + ",\"delayTime\":" + PLVPPTPresenter.this.delayTime + "}";
                    }
                    PLVCommonLog.d(PLVPPTPresenter.TAG, "receive ppt message: delay" + message);
                    IPLVPPTContract.IPLVPPTView iPLVPPTView2 = view;
                    if (iPLVPPTView2 != null) {
                        iPLVPPTView2.sendMsgToWebView(message);
                        return;
                    }
                    return;
                }
                if ("LOGIN".equals(event)) {
                    final PLVLoginEvent pLVLoginEvent = (PLVLoginEvent) PLVEventHelper.toMessageEventModel(message, PLVLoginEvent.class);
                    if (pLVLoginEvent == null || !pLVLoginEvent.getUser().getUserId().equals(PLVSocketWrapper.getInstance().getLoginVO().getUserId())) {
                        return;
                    }
                    PLVPPTPresenter pLVPPTPresenter = PLVPPTPresenter.this;
                    pLVPPTPresenter.dispose(pLVPPTPresenter.delaySendLoginEventDisposable);
                    PLVPPTPresenter.this.delaySendLoginEventDisposable = PLVRxTimer.delay(1000L, new Consumer<Object>() { // from class: com.easefun.polyv.livecommon.module.modules.ppt.presenter.PLVPPTPresenter.1.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(Object o2) throws Exception {
                            IPLVPPTContract.IPLVPPTView iPLVPPTView3 = view;
                            if (iPLVPPTView3 != null) {
                                iPLVPPTView3.sendMsgToWebView("setUser", pLVLoginEvent.getUser().toString());
                            }
                        }
                    });
                    return;
                }
                if (!PLVEventConstant.Class.SE_SWITCH_PPT_MESSAGE.equals(event) || (polyvPPTAuthentic = (PolyvPPTAuthentic) PLVGsonUtil.fromJson(PolyvPPTAuthentic.class, message)) == null) {
                    return;
                }
                if ("1".equals(polyvPPTAuthentic.getStatus())) {
                    IPLVPPTContract.IPLVPPTView iPLVPPTView3 = view;
                    if (iPLVPPTView3 != null) {
                        iPLVPPTView3.switchPPTViewLocation(false);
                        return;
                    }
                    return;
                }
                IPLVPPTContract.IPLVPPTView iPLVPPTView4 = view;
                if (iPLVPPTView4 != null) {
                    iPLVPPTView4.switchPPTViewLocation(true);
                }
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener);
        this.followTeacherPptVideoLocationListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.ppt.presenter.PLVPPTPresenter.2
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                IPLVPPTContract.IPLVPPTView iPLVPPTView;
                if ("onSliceID".equals(event)) {
                    PLVOnSliceIDEvent pLVOnSliceIDEvent = (PLVOnSliceIDEvent) PLVEventHelper.toMessageEventModel(message, PLVOnSliceIDEvent.class);
                    if (pLVOnSliceIDEvent == null || !pLVOnSliceIDEvent.isInClass()) {
                        return;
                    }
                    IPLVPPTContract.IPLVPPTView iPLVPPTView2 = view;
                    if (iPLVPPTView2 != null) {
                        iPLVPPTView2.switchPPTViewLocation(pLVOnSliceIDEvent.getPptAndVedioPosition() == 0);
                    }
                }
                if ("onSliceStart".equals(event)) {
                    PLVOnSliceStartEvent pLVOnSliceStartEvent = (PLVOnSliceStartEvent) PLVEventHelper.toMessageEventModel(message, PLVOnSliceStartEvent.class);
                    if (pLVOnSliceStartEvent == null) {
                        return;
                    }
                    IPLVPPTContract.IPLVPPTView iPLVPPTView3 = view;
                    if (iPLVPPTView3 != null) {
                        iPLVPPTView3.switchPPTViewLocation(pLVOnSliceStartEvent.isPptOnMainScreen());
                    }
                }
                if (!PLVEventConstant.Class.FINISH_CLASS.equals(event) || (iPLVPPTView = view) == null) {
                    return;
                }
                iPLVPPTView.switchPPTViewLocation(false);
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.followTeacherPptVideoLocationListener);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVPPTContract.IPLVPPTPresenter
    public void notifyIsWatchLowLatency(boolean isLowLatency) {
        if (isLowLatency) {
            this.delayTime = 500;
        } else {
            this.delayTime = 5000;
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVPPTContract.IPLVPPTPresenter
    public void sendPPTBrushMsg(String message) {
        PLVELogSender.send(PolyvPPTElog.class, PLVPPTElog.PPTEvent.PPT_RECEIVE_WEB_MESSAGE, "event sendSocketEventreceive web message :" + message);
        PLVSocketWrapper.getInstance().emit("message", message);
    }
}
