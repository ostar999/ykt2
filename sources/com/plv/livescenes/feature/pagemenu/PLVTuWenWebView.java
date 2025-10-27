package com.plv.livescenes.feature.pagemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.business.web.PLVWebview;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.feature.interact.vo.PLVInteractNativeAppParams;
import com.plv.socket.event.PLVEventConstant;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;
import javax.xml.transform.TransformerException;
import org.eclipse.jetty.http.HttpMethods;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class PLVTuWenWebView extends PLVWebview {
    private static final String CALLBACK_DATA = "成功接收到web的回传数据：";
    private static final String LOAD_URL = "https://live.polyv.net/front/tuwen/index";
    private static final String LOAD_URL_BLACK = "https://live.polyv.net/front/tuwen/index?skin=black";
    private static final String TAG = "PLVTuWenWebView";
    private static final String V2_GET_NATIVE_APP_PARAMS_INFO = "getNativeAppParamsInfo";
    private boolean isImageShow;
    private boolean isUseBlackStyle;

    public static class JSConst {
        private static final String CONNECT = "CONNECT";
        private static final String CREATE_IMAGE_TEXT = "CREATE_IMAGE_TEXT";
        private static final String DELETE_IMAGE_TEXT = "DELETE_IMAGE_TEXT";
        private static final String INIT_TUWEN = "INIT_TUWEN";
        private static final String SET_IMAGE_TEXT_MSG = "SET_IMAGE_TEXT_MSG";
        private static final String SET_TOP_IMAGE_TEXT = "SET_TOP_IMAGE_TEXT";

        private JSConst() {
        }
    }

    public PLVTuWenWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void callCreate(String str) {
        callHandler(PLVEventConstant.TuWen.EVENT_CREATE_IMAGE_TEXT, str, new CallBackFunction() { // from class: com.plv.livescenes.feature.pagemenu.PLVTuWenWebView.6
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.dTag(PLVTuWenWebView.TAG, PLVTuWenWebView.CALLBACK_DATA + str2);
            }
        });
    }

    public void callDelete(String str) {
        callHandler(PLVEventConstant.TuWen.EVENT_DELETE_IMAGE_TEXT, str, new CallBackFunction() { // from class: com.plv.livescenes.feature.pagemenu.PLVTuWenWebView.7
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.dTag(PLVTuWenWebView.TAG, PLVTuWenWebView.CALLBACK_DATA + str2);
            }
        });
    }

    public void callInit(String str) {
        callHandler("INIT_TUWEN", str, new CallBackFunction() { // from class: com.plv.livescenes.feature.pagemenu.PLVTuWenWebView.4
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.dTag(PLVTuWenWebView.TAG, PLVTuWenWebView.CALLBACK_DATA + str2);
            }
        });
    }

    public void callRefresh(String str) {
        callHandler(HttpMethods.CONNECT, str, new CallBackFunction() { // from class: com.plv.livescenes.feature.pagemenu.PLVTuWenWebView.5
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.dTag(PLVTuWenWebView.TAG, PLVTuWenWebView.CALLBACK_DATA + str2);
            }
        });
    }

    public void callSetTop(String str) {
        callHandler(PLVEventConstant.TuWen.EVENT_SET_TOP_IMAGE_TEXT, str, new CallBackFunction() { // from class: com.plv.livescenes.feature.pagemenu.PLVTuWenWebView.8
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.dTag(PLVTuWenWebView.TAG, PLVTuWenWebView.CALLBACK_DATA + str2);
            }
        });
    }

    public void callUpdate(String str) {
        callHandler(PLVEventConstant.TuWen.EVENT_SET_IMAGE_TEXT_MSG, str, new CallBackFunction() { // from class: com.plv.livescenes.feature.pagemenu.PLVTuWenWebView.9
            @Override // com.github.lzyzsd.jsbridge.CallBackFunction
            public void onCallBack(String str2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.dTag(PLVTuWenWebView.TAG, PLVTuWenWebView.CALLBACK_DATA + str2);
            }
        });
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void loadWeb() {
        StringBuilder sb;
        String str;
        if (this.isUseBlackStyle) {
            sb = new StringBuilder();
            str = "https://live.polyv.net/front/tuwen/index?skin=black&security=";
        } else {
            sb = new StringBuilder();
            str = "https://live.polyv.net/front/tuwen/index?security=";
        }
        sb.append(str);
        sb.append(PLVSignCreator.getSecurity());
        loadUrl(sb.toString());
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        requestDisallowInterceptTouchEvent(this.isImageShow);
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void registerHandler() {
        registerHandler("clickTuwenImage", new BridgeHandler() { // from class: com.plv.livescenes.feature.pagemenu.PLVTuWenWebView.1
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PLVTuWenWebView.this.isImageShow = true;
            }
        });
        registerHandler("tuwenImageHide", new BridgeHandler() { // from class: com.plv.livescenes.feature.pagemenu.PLVTuWenWebView.2
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                PLVTuWenWebView.this.isImageShow = false;
            }
        });
        registerHandler("getNativeAppParamsInfo", new BridgeHandler() { // from class: com.plv.livescenes.feature.pagemenu.PLVTuWenWebView.3
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                callBackFunction.onCallBack(PLVGsonUtil.toJsonSimple(new PLVInteractNativeAppParams().setAppId(PolyvLiveSDKClient.getInstance().getAppId()).setAppSecret(PolyvLiveSDKClient.getInstance().getAppSecret())));
            }
        });
    }

    public void setUseBlackStyle(boolean z2) {
        this.isUseBlackStyle = z2;
    }

    public PLVTuWenWebView(Context context) {
        this(context, null);
    }

    public PLVTuWenWebView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isUseBlackStyle = true;
    }
}
