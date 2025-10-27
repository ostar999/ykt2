package com.plv.business.api.common.ppt;

import android.util.Log;
import com.easefun.polyv.businesssdk.api.common.ppt.PolyvPPTVodProcessor;
import com.easefun.polyv.businesssdk.web.IPolyvWebMessageProcessor;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.business.web.PLVWebview;
import com.plv.foundationsdk.log.PLVCommonLog;
import k.a;

/* loaded from: classes4.dex */
public class PLVPPTVodProcessor extends IPolyvWebMessageProcessor<PolyvPPTVodProcessor.PolyvVideoPPTCallback> {
    public static final String CHANGE_PPT_POSITION = "changePPTPosition";
    public static final String PAUSE_PPT = "pptPause";
    public static final String PPTPREPARE = "pptPrepare";
    public static final String SEEK_PPT = "pptSeek";
    public static final String SET_OFFLINE_PATH = "setOfflinePath";
    public static final String START_PPT = "pptPlay";
    private static final String TAG = "PLVPPTVodProcessor";
    public static final String VIDEODURATION = "videoDuration";
    public static final String VIDEOSTART = "videoStart";

    public interface PLVVideoPPTCallback {
        void callVideoDuration(CallBackFunction callBackFunction);

        void pptPositionChange(boolean z2);

        void pptPrepare();
    }

    public PLVPPTVodProcessor(PLVWebview pLVWebview) {
        super(pLVWebview);
        this.protocols.add(START_PPT);
        this.protocols.add(PAUSE_PPT);
        this.protocols.add(SEEK_PPT);
        this.protocols.add("videoDuration");
        this.protocols.add(PPTPREPARE);
        this.protocols.add(VIDEOSTART);
        this.protocols.add(CHANGE_PPT_POSITION);
        this.protocols.add(SET_OFFLINE_PATH);
    }

    @Override // com.plv.foundationsdk.web.PLVWebMessageProcessor
    public void callMessage(String str, String str2) {
        if (this.protocols.contains(str)) {
            PLVCommonLog.d(TAG, "callMessage type:" + str + " message:" + str2);
            this.webview.callHandler(str, str2, new CallBackFunction() { // from class: com.plv.business.api.common.ppt.PLVPPTVodProcessor.1
                @Override // com.github.lzyzsd.jsbridge.CallBackFunction
                public void onCallBack(String str3) {
                    PLVCommonLog.d(PLVPPTVodProcessor.TAG, "receive call handler data ：" + str3);
                }
            });
        }
    }

    @Override // com.plv.foundationsdk.web.PLVWebMessageProcessor
    public void destroy() {
        PLVCommonLog.d(TAG, "destory");
    }

    @Override // com.plv.foundationsdk.web.PLVWebMessageProcessor
    public void registerJSHandler(final PolyvPPTVodProcessor.PolyvVideoPPTCallback polyvVideoPPTCallback) {
        this.webview.registerHandler("videoDuration", new BridgeHandler() { // from class: com.plv.business.api.common.ppt.PLVPPTVodProcessor.2
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PolyvPPTVodProcessor.PolyvVideoPPTCallback polyvVideoPPTCallback2 = polyvVideoPPTCallback;
                if (polyvVideoPPTCallback2 != null) {
                    polyvVideoPPTCallback2.callVideoDuration(callBackFunction);
                }
            }
        });
        this.webview.registerHandler(PPTPREPARE, new BridgeHandler() { // from class: com.plv.business.api.common.ppt.PLVPPTVodProcessor.3
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PolyvPPTVodProcessor.PolyvVideoPPTCallback polyvVideoPPTCallback2 = polyvVideoPPTCallback;
                if (polyvVideoPPTCallback2 != null) {
                    polyvVideoPPTCallback2.pptPrepare();
                }
                callBackFunction.onCallBack("function is success");
            }
        });
        this.webview.registerHandler(CHANGE_PPT_POSITION, new BridgeHandler() { // from class: com.plv.business.api.common.ppt.PLVPPTVodProcessor.4
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PolyvPPTVodProcessor.PolyvVideoPPTCallback polyvVideoPPTCallback2 = polyvVideoPPTCallback;
                if (polyvVideoPPTCallback2 != null) {
                    polyvVideoPPTCallback2.pptPositionChange(a.f27523u.equals(str));
                }
                Log.d(PLVPPTVodProcessor.TAG, "changePPTPosition:" + str);
            }
        });
    }
}
