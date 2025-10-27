package com.plv.livescenes.feature.pagemenu.product;

import android.content.Context;
import android.util.AttributeSet;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.feature.interact.vo.PLVInteractNativeAppParams;
import com.plv.livescenes.webview.PLVSocketWebView;

/* loaded from: classes4.dex */
public class PLVProductWebView extends PLVSocketWebView {
    private static final String EVENT_CLICK_PRODUCT_BUTTON = "clickProductButton";
    private static final String EVENT_ON_NEED_UPDATE_NATIVE_APP_PARAMS_INFO = "getNativeAppParamsInfo";
    private static final String EVENT_UPDATE_NATIVE_APP_PARAMS_INFO = "updateNativeAppParamsInfo";
    private static final String LOAD_URL = "https://websdk.videocc.net/interactions-receive-sdk-ui-webview/latest/product.html";
    private static final String TAG = "PLVProductWebView";
    private BridgeHandler onNeedUpdateNativeAppParamsInfoHandler;
    private BridgeHandler onReceiveEventClickProductButtonHandler;

    public PLVProductWebView(Context context) {
        super(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNeedUpdateNativeAppParamsInfo(String str, CallBackFunction callBackFunction) {
        BridgeHandler bridgeHandler = this.onNeedUpdateNativeAppParamsInfoHandler;
        if (bridgeHandler != null) {
            bridgeHandler.handler(str, callBackFunction);
        } else {
            PLVCommonLog.w(TAG, "onNeedUpdateNativeAppParamsInfoHandler is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReceiveEventClickProductButton(String str, CallBackFunction callBackFunction) {
        BridgeHandler bridgeHandler = this.onReceiveEventClickProductButtonHandler;
        if (bridgeHandler != null) {
            bridgeHandler.handler(str, callBackFunction);
        } else {
            PLVCommonLog.w(TAG, "onReceiveEventClickProductButtonHandler is null");
        }
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void loadWeb() {
        loadUrl("https://websdk.videocc.net/interactions-receive-sdk-ui-webview/latest/product.html?security=" + PLVSignCreator.getSecurity());
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void registerHandler() {
        registerHandler("getNativeAppParamsInfo", new BridgeHandler() { // from class: com.plv.livescenes.feature.pagemenu.product.PLVProductWebView.2
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PLVProductWebView.this.onNeedUpdateNativeAppParamsInfo(str, callBackFunction);
            }
        });
        registerHandler(EVENT_CLICK_PRODUCT_BUTTON, new BridgeHandler() { // from class: com.plv.livescenes.feature.pagemenu.product.PLVProductWebView.3
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PLVProductWebView.this.onReceiveEventClickProductButton(str, callBackFunction);
            }
        });
    }

    public PLVProductWebView setOnNeedUpdateNativeAppParamsInfoHandler(BridgeHandler bridgeHandler) {
        this.onNeedUpdateNativeAppParamsInfoHandler = bridgeHandler;
        return this;
    }

    public PLVProductWebView setOnReceiveEventClickProductButtonHandler(BridgeHandler bridgeHandler) {
        this.onReceiveEventClickProductButtonHandler = bridgeHandler;
        return this;
    }

    public void updateNativeAppParamsInfo(PLVInteractNativeAppParams pLVInteractNativeAppParams) {
        callHandler("updateNativeAppParamsInfo", PLVGsonUtil.toJsonSimple(pLVInteractNativeAppParams), new CallBackFunction() { // from class: com.plv.livescenes.feature.pagemenu.product.PLVProductWebView.1
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str) {
                PLVCommonLog.d(PLVProductWebView.TAG, "updateNativeAppParamsInfo callback: " + str);
            }
        });
    }

    public PLVProductWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PLVProductWebView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
