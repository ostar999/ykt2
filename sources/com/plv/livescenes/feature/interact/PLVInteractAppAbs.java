package com.plv.livescenes.feature.interact;

import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.socket.socketio.PLVSocketIOClient;

/* loaded from: classes4.dex */
public abstract class PLVInteractAppAbs {
    private IPLVInteractJSBridge interactJSBridge;
    private OnInteractAppShowListener onInteractAppShowListener;
    protected boolean isDestroyed = false;
    protected String viewerName = PLVSocketIOClient.getInstance().getNickName();
    protected String channelId = PLVSocketIOClient.getInstance().getChannelId();
    protected String viewerId = PLVSocketIOClient.getInstance().getSocketUserId();

    public interface OnInteractAppShowListener {
        void onShow();
    }

    public void destroy() {
        this.isDestroyed = true;
    }

    public void notifyShow() {
        OnInteractAppShowListener onInteractAppShowListener = this.onInteractAppShowListener;
        if (onInteractAppShowListener != null) {
            onInteractAppShowListener.onShow();
        }
    }

    public abstract void processSocketMsg(String str, String str2);

    public abstract void registerMsgReceiverFromJs(com.easefun.polyv.livescenes.feature.interact.IPLVInteractJSBridge iPLVInteractJSBridge);

    public void sendMsgToJs(String str, String str2, CallBackFunction callBackFunction) {
        IPLVInteractJSBridge iPLVInteractJSBridge = this.interactJSBridge;
        if (iPLVInteractJSBridge != null) {
            iPLVInteractJSBridge.sendMsgToJs(str, str2, callBackFunction);
        }
    }

    public void setInteractJSBridge(com.easefun.polyv.livescenes.feature.interact.IPLVInteractJSBridge iPLVInteractJSBridge) {
        this.interactJSBridge = iPLVInteractJSBridge;
        registerMsgReceiverFromJs(iPLVInteractJSBridge);
    }

    public void setOnShowListener(OnInteractAppShowListener onInteractAppShowListener) {
        this.onInteractAppShowListener = onInteractAppShowListener;
    }
}
