package com.easefun.polyv.livecommon.module.modules.interact.app;

import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractJSBridgeEventConst;
import com.easefun.polyv.livescenes.chatroom.PolyvChatroomManager;
import com.easefun.polyv.livescenes.chatroom.PolyvSocketCallbackListener;
import com.easefun.polyv.livescenes.feature.interact.IPLVInteractJSBridge;
import com.easefun.polyv.livescenes.feature.interact.PLVInteractAppAbs;
import com.easefun.polyv.livescenes.model.PolyvInteractiveCallbackVO;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;

/* loaded from: classes3.dex */
public class PLVInteractCommonControl extends PLVInteractAppAbs implements IPLVInteractSendServerResultToJs {
    private static final String TAG = "PLVInteractCommonControl";
    private OnInteractCommonControlListener onInteractCommonControlListener;

    public interface OnInteractCommonControlListener {
        void onWebViewHide();

        void onWebViewLoadFinished();
    }

    public PLVInteractCommonControl() {
        PolyvChatroomManager.getInstance().setSocketCallbackListener(new PolyvSocketCallbackListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractCommonControl.1
            @Override // com.easefun.polyv.livescenes.chatroom.PolyvSocketCallbackListener
            public void socketCallback(PolyvInteractiveCallbackVO callbackVO) {
                String jsonSimple = PLVGsonUtil.toJsonSimple(callbackVO);
                PLVCommonLog.d(PLVInteractCommonControl.TAG, "PLVInteractCommonControl.socketCallback\n" + jsonSimple);
                PLVInteractCommonControl.this.sendServerResultToJs(jsonSimple);
            }
        });
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void processSocketMsg(String msg, String event) {
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void registerMsgReceiverFromJs(IPLVInteractJSBridge interactJSBridge) {
        interactJSBridge.registerMsgReceiverFromJs(PLVInteractJSBridgeEventConst.CLOSE_WEB_VIEW_METHOD, new BridgeHandler() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractCommonControl.3
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String data, CallBackFunction function) {
                PLVCommonLog.d(PLVInteractCommonControl.TAG, "CLOSE_WEB_VIEW_METHOD " + data);
                if (PLVInteractCommonControl.this.onInteractCommonControlListener != null) {
                    PLVInteractCommonControl.this.onInteractCommonControlListener.onWebViewHide();
                }
            }
        });
        interactJSBridge.registerMsgReceiverFromJs(PLVInteractJSBridgeEventConst.WEB_VIEW_LOAD_FINISHED, new BridgeHandler() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractCommonControl.4
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String data, CallBackFunction function) {
                PLVCommonLog.d(PLVInteractCommonControl.TAG, "WEB_VIEW_LOAD_FINISHED " + data);
                if (PLVInteractCommonControl.this.onInteractCommonControlListener != null) {
                    PLVInteractCommonControl.this.onInteractCommonControlListener.onWebViewLoadFinished();
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.app.IPLVInteractSendServerResultToJs
    public void sendServerResultToJs(String msg) {
        sendMsgToJs(PLVInteractJSBridgeEventConst.INTERACTIVE_CALLBACK, msg, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractCommonControl.2
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String data) {
                PLVCommonLog.d(PLVInteractCommonControl.TAG, "INTERACTIVE_CALLBACK " + data);
            }
        });
        notifyShow();
    }

    public void setOnInteractCommonControlListener(OnInteractCommonControlListener onInteractCommonControlListener) {
        this.onInteractCommonControlListener = onInteractCommonControlListener;
    }
}
