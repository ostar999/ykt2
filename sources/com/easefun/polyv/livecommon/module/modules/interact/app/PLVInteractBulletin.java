package com.easefun.polyv.livecommon.module.modules.interact.app;

import android.app.Activity;
import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractJSBridgeEventConst;
import com.easefun.polyv.livecommon.module.utils.PLVWebUtils;
import com.easefun.polyv.livescenes.feature.interact.IPLVInteractJSBridge;
import com.easefun.polyv.livescenes.feature.interact.PLVInteractAppAbs;
import com.easefun.polyv.livescenes.model.bulletin.PolyvBulletinVO;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;

/* loaded from: classes3.dex */
public class PLVInteractBulletin extends PLVInteractAppAbs {
    private static final String TAG = "PLVInteractBulletin";
    private PolyvBulletinVO bulletinVO = new PolyvBulletinVO();
    private OnPLVInteractBulletinListener onPLVInteractBulletinListener;

    public interface OnPLVInteractBulletinListener {
        void onBulletinDelete();
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void processSocketMsg(String msg, String event) {
        event.hashCode();
        if (event.equals("REMOVE_BULLETIN")) {
            sendMsgToJs(PLVInteractJSBridgeEventConst.BULLETIN_REMOVE, null, new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractBulletin.2
                @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                public void onCallBack(String data) {
                    PLVCommonLog.d(PLVInteractBulletin.TAG, "BULLETIN_REMOVE " + data);
                    if (PLVInteractBulletin.this.bulletinVO != null) {
                        PLVInteractBulletin.this.bulletinVO.setContent("");
                    }
                    if (PLVInteractBulletin.this.onPLVInteractBulletinListener != null) {
                        PLVInteractBulletin.this.onPLVInteractBulletinListener.onBulletinDelete();
                    }
                }
            });
        } else if (event.equals("BULLETIN")) {
            this.bulletinVO = (PolyvBulletinVO) PLVGsonUtil.fromJson(PolyvBulletinVO.class, msg);
            showBulletin();
        }
    }

    @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs
    public void registerMsgReceiverFromJs(IPLVInteractJSBridge interactJSBridge) {
        interactJSBridge.registerMsgReceiverFromJs("linkClick", new BridgeHandler() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractBulletin.3
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String data, CallBackFunction function) {
                PLVCommonLog.d(PLVInteractBulletin.TAG, "BULLETIN_LINK_CLICK " + data);
                Activity topActivity = ActivityUtils.getTopActivity();
                if (topActivity != null) {
                    PLVWebUtils.openWebLink(data, topActivity);
                }
            }
        });
    }

    public void setOnPLVInteractBulletinListener(OnPLVInteractBulletinListener onPLVInteractBulletinListener) {
        this.onPLVInteractBulletinListener = onPLVInteractBulletinListener;
    }

    public void showBulletin() {
        if (this.bulletinVO == null) {
            return;
        }
        notifyShow();
        sendMsgToJs(PLVInteractJSBridgeEventConst.BULLETIN_SHOW, new Gson().toJson(this.bulletinVO), new CallBackFunction() { // from class: com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractBulletin.1
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String data) {
                PLVCommonLog.d(PLVInteractBulletin.TAG, "BULLETIN_SHOW " + data);
            }
        });
    }
}
