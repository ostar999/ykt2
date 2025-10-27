package com.plv.livescenes.document;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.PLVELogsService;
import com.plv.foundationsdk.manager.PLVChatDomainManager;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.web.PLVWebview;
import com.plv.livescenes.feature.interact.vo.PLVInteractNativeAppParams;
import com.plv.livescenes.log.ppt.PLVPPTElog;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes4.dex */
public class PLVDocumentWebView extends PLVWebview {
    private static final String LOAD_URL = "https://player.polyv.net/resp/ppt-h5/latest/page/pptForMobile.html?hasScrollForPPT=true&version=1&hasPageBtn=0";
    private static final String TAG = "PLVDocumentWebView";
    private static final String V2_GET_NATIVE_APP_PARAMS_INFO = "getNativeAppParamsInfo";
    private float lastTouchX;
    private float lastTouchY;
    private boolean needDarkBackground;
    private boolean needGestureAction;

    public PLVDocumentWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.needDarkBackground = true;
        this.needGestureAction = true;
        this.lastTouchX = Float.MIN_VALUE;
        this.lastTouchY = Float.MIN_VALUE;
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void addCallback() {
        setPageLoadCallback(new PLVWebview.WebPageLoadCallback() { // from class: com.plv.livescenes.document.PLVDocumentWebView.2
            @Override // com.plv.foundationsdk.web.PLVWebview.WebPageLoadCallback
            public void onLoadFinish(WebView webView, String str) {
                PLVELogsService.getInstance().addStaticsLog(PLVPPTElog.class, PLVPPTElog.PPTEvent.PPT_LOAD_FINISH, "load finish :https://player.polyv.net/resp/ppt-h5/latest/page/pptForMobile.html?hasScrollForPPT=true&version=1&hasPageBtn=0", new String[0]);
            }

            @Override // com.plv.foundationsdk.web.PLVWebview.WebPageLoadCallback
            public void onLoadSslFailed(WebView webView, String str) {
                PLVELogsService.getInstance().addStaticsLog(PLVPPTElog.class, PLVPPTElog.PPTEvent.PPT_LOAD_FAILED, "load failed :https://player.polyv.net/resp/ppt-h5/latest/page/pptForMobile.html?hasScrollForPPT=true&version=1&hasPageBtn=0", new String[0]);
            }

            @Override // com.plv.foundationsdk.web.PLVWebview.WebPageLoadCallback
            public void onLoadStart(WebView webView, String str) {
            }
        });
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void loadWeb() {
        String chatApiDomain = PLVChatDomainManager.getInstance().getChatDomain().getChatApiDomain();
        if (!TextUtils.isEmpty(chatApiDomain) && chatApiDomain.startsWith("http")) {
            try {
                chatApiDomain = new URL(chatApiDomain).getHost();
            } catch (MalformedURLException e2) {
                e2.printStackTrace();
            }
        }
        PLVCommonLog.d(TAG, "domainName:" + chatApiDomain);
        String str = LOAD_URL + "&domainName=" + chatApiDomain;
        if (this.needDarkBackground) {
            str = str + "&whiteBackColor=#313540";
        }
        loadUrl(str + "&security=" + PLVSignCreator.getSecurity());
        PLVELogsService.getInstance().addStaticsLog(PLVPPTElog.class, PLVPPTElog.PPTEvent.PPT_LOAD_START, "load url :https://player.polyv.net/resp/ppt-h5/latest/page/pptForMobile.html?hasScrollForPPT=true&version=1&hasPageBtn=0", new String[0]);
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.lastTouchX = motionEvent.getX();
            this.lastTouchY = motionEvent.getY();
        } else if (motionEvent.getAction() == 1) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            if (Float.compare(this.lastTouchX, x2) == 0 && Float.compare(this.lastTouchY, y2) == 0) {
                performClick();
            }
        }
        if (this.needGestureAction) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // com.plv.foundationsdk.web.PLVWebview
    public void registerHandler() {
        registerHandler("getNativeAppParamsInfo", new BridgeHandler() { // from class: com.plv.livescenes.document.PLVDocumentWebView.1
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                callBackFunction.onCallBack(PLVGsonUtil.toJsonSimple(new PLVInteractNativeAppParams().setAppId(PolyvLiveSDKClient.getInstance().getAppId()).setAppSecret(PolyvLiveSDKClient.getInstance().getAppSecret())));
            }
        });
    }

    public void setNeedDarkBackground(boolean z2) {
        this.needDarkBackground = z2;
    }

    public void setNeedGestureAction(boolean z2) {
        this.needGestureAction = z2;
    }

    public PLVDocumentWebView(Context context) {
        super(context);
        this.needDarkBackground = true;
        this.needGestureAction = true;
        this.lastTouchX = Float.MIN_VALUE;
        this.lastTouchY = Float.MIN_VALUE;
    }

    public PLVDocumentWebView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.needDarkBackground = true;
        this.needGestureAction = true;
        this.lastTouchX = Float.MIN_VALUE;
        this.lastTouchY = Float.MIN_VALUE;
    }
}
