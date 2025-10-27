package com.easefun.polyv.livecommon.module.modules.interact.app;

import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractJSBridgeEventConst;
import com.easefun.polyv.livescenes.chatroom.PolyvChatroomManager;
import com.easefun.polyv.livescenes.feature.interact.IPLVInteractJSBridge;
import com.easefun.polyv.livescenes.feature.interact.PLVInteractAppAbs;
import com.easefun.polyv.livescenes.model.signin.PolyvSignIn2JsVO;
import com.easefun.polyv.livescenes.model.signin.PolyvSignIn2SocketVO;
import com.easefun.polyv.livescenes.model.signin.PolyvSignInVO;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.PLVSocketEvent;
import com.plv.livescenes.model.PLVInteractiveCallbackVO;
import com.plv.livescenes.model.signin.PLVSignIn2SocketVO;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;
import javax.xml.transform.TransformerException;
import org.json.JSONException;

/* loaded from: classes3.dex */
public class PLVInteractSignIn extends PLVInteractAppAbs {
    private static final String TAG = "PLVInteractSignIn";
    private PolyvSignInVO signInVO;

    /* JADX INFO: Access modifiers changed from: private */
    public void sendResultToServer(PolyvSignIn2SocketVO socketVO) {
        PolyvChatroomManager.getInstance().sendInteractiveSocketMessage("message", socketVO, 3, PLVInteractiveCallbackVO.EVENT_SIGN);
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void processSocketMsg(String msg, String event) {
        event.hashCode();
        if (!event.equals(PLVSocketEvent.START_SIGN_IN)) {
            if (event.equals(PLVSocketEvent.STOP_SIGN_IN)) {
                sendMsgToJs(PLVInteractJSBridgeEventConst.SIGN_STOP, null, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractSignIn.2
                    @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                    public void onCallBack(String data) {
                        PLVCommonLog.d(PLVInteractSignIn.TAG, "SIGN_STOP " + data);
                    }
                });
                return;
            }
            return;
        }
        PolyvSignInVO polyvSignInVO = (PolyvSignInVO) PLVGsonUtil.fromJson(PolyvSignInVO.class, msg);
        this.signInVO = polyvSignInVO;
        if (polyvSignInVO == null) {
            return;
        }
        notifyShow();
        sendMsgToJs(PLVInteractJSBridgeEventConst.SIGN_START, new Gson().toJson(new PolyvSignIn2JsVO(this.signInVO.getData().getLimitTime(), this.signInVO.getData().getMessage())), new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractSignIn.1
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String data) {
                PLVCommonLog.d(PLVInteractSignIn.TAG, "SIGN_START " + data);
            }
        });
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void registerMsgReceiverFromJs(IPLVInteractJSBridge interactJSBridge) {
        interactJSBridge.registerMsgReceiverFromJs(PLVInteractJSBridgeEventConst.SIGN_SUBMIT, new BridgeHandler() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractSignIn.3
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String data, CallBackFunction function) throws JSONException, TransformerException, IllegalArgumentException {
                PLVCommonLog.d(PLVInteractSignIn.TAG, "SIGN_SUBMIT " + data);
                PolyvSignIn2SocketVO polyvSignIn2SocketVO = new PolyvSignIn2SocketVO();
                if (PLVInteractSignIn.this.signInVO == null) {
                    LogUtils.eTag(PLVInteractSignIn.TAG, "signInVO=null");
                    return;
                }
                polyvSignIn2SocketVO.setCheckinId(PLVInteractSignIn.this.signInVO.getData().getCheckinId());
                polyvSignIn2SocketVO.setRoomId(PLVInteractSignIn.this.signInVO.getRoomId());
                polyvSignIn2SocketVO.setUser(new PLVSignIn2SocketVO.UserBean(((com.plv.livescenes.feature.interact.PLVInteractAppAbs) PLVInteractSignIn.this).viewerName, ((com.plv.livescenes.feature.interact.PLVInteractAppAbs) PLVInteractSignIn.this).viewerId));
                PLVInteractSignIn.this.sendResultToServer(polyvSignIn2SocketVO);
            }
        });
    }
}
