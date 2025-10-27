package com.plv.business.api.common.ppt;

import com.easefun.polyv.businesssdk.web.IPolyvWebMessageProcessor;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.business.web.PLVWebview;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.tencent.tbs.one.BuildConfig;

/* loaded from: classes4.dex */
public class PLVLivePPTProcessor extends IPolyvWebMessageProcessor<LiveJSCallback> {
    public static final String AUTHORIZATION_PPT_PAINT = "setPaintPermission";
    public static final String CHANGE_COLOR = "changeColor";
    public static final String CHANGE_PPT_PAGE = "changePPTPage";
    public static final String CHAT_LOGIN = "setUser";
    public static final String ERASE_STATUS = "toDelete";
    public static final String PAUSE_PPT = "PAUSE_PPT";
    public static final String PPT_CLICK_BTN = "handleClickBtns";
    public static final String PPT_PAINT_STATUS = "setPaintStatus";
    public static final String PPT_STATUS_CHANGE = "pptStatusChange";
    public static final String RELOAD_PPT = "RELOAD_PPT";
    public static final String SCREEN_BS_SWITCH_PPT = "SCREEN_BS_SWITCH_PPT";
    public static final String SCREEN_PL_SWITCH_PPT = "SCREEN_PL_SWITCH_PPT";
    public static final String SEND_SOCKET_EVENT = "sendSocketEvent";
    public static final String SETSEIDATA = "setSeiData";
    public static final String START_PPT = "START_PPT";
    private static final String TAG = "PLVLivePPTProcessor";
    public static final String UPDATE_PPT = "refreshPPT";

    public interface LiveJSCallback {
        void backTopActivity();

        void brushPPT(String str);

        void onPPTStatusChange(String str);

        void reloadVideo();

        void screenBSSwitch(boolean z2);

        void screenPLSwitch(boolean z2);

        void startOrPause(boolean z2);
    }

    public PLVLivePPTProcessor(PLVWebview pLVWebview) {
        super(pLVWebview);
        this.protocols.add("refreshPPT");
        this.protocols.add("setUser");
        this.protocols.add("setPaintPermission");
        this.protocols.add("setPaintStatus");
        this.protocols.add("changeColor");
        this.protocols.add("toDelete");
        this.protocols.add(SETSEIDATA);
        this.protocols.add("changePPTPage");
    }

    @Override // com.plv.foundationsdk.web.PLVWebMessageProcessor
    public void callMessage(String str, String str2) {
        if (this.protocols.contains(str)) {
            this.webview.callHandler(str, str2, new CallBackFunction() { // from class: com.plv.business.api.common.ppt.PLVLivePPTProcessor.1
                @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                public void onCallBack(String str3) {
                    PLVCommonLog.d(PLVLivePPTProcessor.TAG, "call handler data ：" + str3);
                }
            });
        }
    }

    @Override // com.plv.foundationsdk.web.PLVWebMessageProcessor
    public void destroy() {
        PLVCommonLog.d(TAG, "destory");
    }

    @Override // com.plv.foundationsdk.web.PLVWebMessageProcessor
    public void registerJSHandler(final LiveJSCallback liveJSCallback) {
        this.webview.registerHandler("sendSocketEvent", new BridgeHandler() { // from class: com.plv.business.api.common.ppt.PLVLivePPTProcessor.2
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                LiveJSCallback liveJSCallback2 = liveJSCallback;
                if (liveJSCallback2 != null) {
                    liveJSCallback2.brushPPT(str);
                }
            }
        });
        this.webview.registerHandler(SCREEN_BS_SWITCH_PPT, new BridgeHandler() { // from class: com.plv.business.api.common.ppt.PLVLivePPTProcessor.3
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                LiveJSCallback liveJSCallback2 = liveJSCallback;
                if (liveJSCallback2 != null) {
                    liveJSCallback2.screenPLSwitch(true);
                }
            }
        });
        this.webview.registerHandler(SCREEN_PL_SWITCH_PPT, new BridgeHandler() { // from class: com.plv.business.api.common.ppt.PLVLivePPTProcessor.4
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                LiveJSCallback liveJSCallback2 = liveJSCallback;
                if (liveJSCallback2 != null) {
                    liveJSCallback2.screenPLSwitch(true);
                }
            }
        });
        this.webview.registerHandler(PAUSE_PPT, new BridgeHandler() { // from class: com.plv.business.api.common.ppt.PLVLivePPTProcessor.5
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                LiveJSCallback liveJSCallback2 = liveJSCallback;
                if (liveJSCallback2 != null) {
                    liveJSCallback2.startOrPause(false);
                }
            }
        });
        this.webview.registerHandler(START_PPT, new BridgeHandler() { // from class: com.plv.business.api.common.ppt.PLVLivePPTProcessor.6
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                LiveJSCallback liveJSCallback2 = liveJSCallback;
                if (liveJSCallback2 != null) {
                    liveJSCallback2.startOrPause(true);
                }
            }
        });
        this.webview.registerHandler(RELOAD_PPT, new BridgeHandler() { // from class: com.plv.business.api.common.ppt.PLVLivePPTProcessor.7
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                LiveJSCallback liveJSCallback2 = liveJSCallback;
                if (liveJSCallback2 != null) {
                    liveJSCallback2.reloadVideo();
                }
            }
        });
        this.webview.registerHandler(PPT_CLICK_BTN, new BridgeHandler() { // from class: com.plv.business.api.common.ppt.PLVLivePPTProcessor.8
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                LiveJSCallback liveJSCallback2;
                if ("back".equals(str)) {
                    LiveJSCallback liveJSCallback3 = liveJSCallback;
                    if (liveJSCallback3 != null) {
                        liveJSCallback3.backTopActivity();
                        return;
                    }
                    return;
                }
                if (!BuildConfig.FLAVOR.equals(str) || (liveJSCallback2 = liveJSCallback) == null) {
                    return;
                }
                liveJSCallback2.screenPLSwitch(true);
            }
        });
        this.webview.registerHandler("pptStatusChange", new BridgeHandler() { // from class: com.plv.business.api.common.ppt.PLVLivePPTProcessor.9
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PLVCommonLog.d(PLVLivePPTProcessor.TAG, str);
                LiveJSCallback liveJSCallback2 = liveJSCallback;
                if (liveJSCallback2 != null) {
                    liveJSCallback2.onPPTStatusChange(str);
                }
            }
        });
    }
}
