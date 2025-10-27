package com.plv.livescenes.feature.interact;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;

/* loaded from: classes4.dex */
public interface IPLVInteractJSBridge {
    void registerMsgReceiverFromJs(String str, BridgeHandler bridgeHandler);

    void sendMsgToJs(String str, String str2, CallBackFunction callBackFunction);
}
