package com.plv.foundationsdk.web;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import cn.hutool.core.text.StrPool;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.plv.foundationsdk.PLVUAClient;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.model.web.PLVJSResponseVO;
import com.plv.foundationsdk.utils.PLVNetworkUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class PLVWebview extends BridgeWebView {
    public static final String MESSAGE_ERROR = "ERROR";
    public static final String MESSAGE_OK = "OK";
    public static final int STATUS_ERROR = 0;
    public static final int STATUS_SUCCESS = 1;
    private static final String TAG = "PolyvWebview";
    protected boolean hasLoadFinish;
    private boolean hasRegister;
    private WebPageLoadCallback pageLoadCallback;
    protected Map<String, String> pptPrepareParams;
    protected List<PLVWebMessageProcessor> processors;
    private BroadcastReceiver receiver;
    private PolyvBridgeWebViewClient webViewClient;

    public class PolyvBridgeWebViewClient extends BridgeWebViewClient {
        public PolyvBridgeWebViewClient(BridgeWebView bridgeWebView) {
            super(bridgeWebView);
        }

        public boolean loadUrlByBridgeWebViewClient(WebView webView, String str) {
            try {
                return super.shouldOverrideUrlLoading(webView, str);
            } catch (Exception e2) {
                PLVCommonLog.exception(e2);
                return true;
            }
        }

        @Override // com.github.lzyzsd.jsbridge.BridgeWebViewClient, android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            PLVCommonLog.d(PLVWebview.TAG, "onPageFinished");
            PLVWebview pLVWebview = PLVWebview.this;
            pLVWebview.hasLoadFinish = true;
            if (!pLVWebview.pptPrepareParams.isEmpty()) {
                for (Map.Entry<String, String> entry : PLVWebview.this.pptPrepareParams.entrySet()) {
                    PLVWebview.this.callMessage(entry.getKey(), entry.getValue());
                }
                PLVWebview.this.pptPrepareParams.clear();
            }
            if (PLVWebview.this.pageLoadCallback != null) {
                PLVWebview.this.pageLoadCallback.onLoadFinish(webView, str);
            }
        }

        @Override // com.github.lzyzsd.jsbridge.BridgeWebViewClient, android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            PLVCommonLog.d(PLVWebview.TAG, "onPageStarted");
            super.onPageStarted(webView, str, bitmap);
            PLVWebview pLVWebview = PLVWebview.this;
            pLVWebview.hasLoadFinish = false;
            if (pLVWebview.pageLoadCallback != null) {
                PLVWebview.this.pageLoadCallback.onLoadStart(webView, str);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (sslError.getPrimaryError() == 5) {
                sslErrorHandler.proceed();
            } else {
                sslErrorHandler.cancel();
            }
            if (PLVWebview.this.pageLoadCallback != null) {
                PLVWebview.this.pageLoadCallback.onLoadSslFailed(webView, sslError.getUrl());
            }
        }

        @Override // com.github.lzyzsd.jsbridge.BridgeWebViewClient, android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            return PLVWebview.this.overrideUrlLoading(webView, str);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResponseStatus {
    }

    public interface WebPageLoadCallback {
        void onLoadFinish(WebView webView, String str);

        void onLoadSslFailed(WebView webView, String str);

        void onLoadStart(WebView webView, String str);
    }

    public PLVWebview(Context context) {
        this(context, null);
    }

    private void initBridgeWebViewClient() {
        PolyvBridgeWebViewClient polyvBridgeWebViewClient = new PolyvBridgeWebViewClient(this);
        this.webViewClient = polyvBridgeWebViewClient;
        setWebViewClient(polyvBridgeWebViewClient);
    }

    private void initialView(Context context) {
        setBackgroundColor(0);
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMixedContentMode(2);
        settings.setUserAgentString(PLVUAClient.getUserAgent());
        setDefaultHandler(new BridgeHandler() { // from class: com.plv.foundationsdk.web.PLVWebview.1
            @Override // com.github.lzyzsd.jsbridge.BridgeHandler
            public void handler(String str, CallBackFunction callBackFunction) {
                Log.d(PLVWebview.TAG, "DefaultHandler() called with: s = [" + str + "], callBackFunction = [" + callBackFunction + StrPool.BRACKET_END);
                callBackFunction.onCallBack(new Gson().toJson(PLVWebview.this.getResponse(0, "没有注册此方法的调用", null, null)));
            }
        });
        initBridgeWebViewClient();
    }

    private void registerNetWorkReceiver() {
        if (this.hasRegister) {
            return;
        }
        getContext().registerReceiver(this.receiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.hasRegister = true;
    }

    private void unregisterReceiver() {
        if (this.receiver == null || !this.hasRegister) {
            return;
        }
        getContext().unregisterReceiver(this.receiver);
        this.hasRegister = false;
        this.receiver = null;
    }

    public void addCallback() {
    }

    public void callMessage(String str, String str2) {
        if (!this.hasLoadFinish) {
            this.pptPrepareParams.put(str, str2);
            return;
        }
        Iterator<PLVWebMessageProcessor> it = this.processors.iterator();
        while (it.hasNext()) {
            it.next().callMessage(str, str2);
        }
    }

    @Override // android.webkit.WebView
    public void destroy() {
        super.destroy();
        List<PLVWebMessageProcessor> list = this.processors;
        if (list != null) {
            list.clear();
            this.processors = null;
        }
    }

    public <T> PLVJSResponseVO<T> getResponse(int i2, String str, Class<T> cls, T t2) {
        PLVJSResponseVO<T> pLVJSResponseVO = new PLVJSResponseVO<>();
        pLVJSResponseVO.setStatus(i2);
        pLVJSResponseVO.setMessage(str);
        if (cls != null && t2 != null) {
            pLVJSResponseVO.setData(t2);
        }
        return pLVJSResponseVO;
    }

    public abstract void loadWeb();

    public boolean overrideUrlLoading(WebView webView, String str) {
        return this.webViewClient.loadUrlByBridgeWebViewClient(webView, str);
    }

    public abstract void registerHandler();

    public void registerProcessor(PLVWebMessageProcessor pLVWebMessageProcessor) {
        this.processors.add(pLVWebMessageProcessor);
    }

    public void removeProcessor(PLVWebMessageProcessor pLVWebMessageProcessor) {
        this.processors.remove(pLVWebMessageProcessor);
    }

    public void setPageLoadCallback(WebPageLoadCallback webPageLoadCallback) {
        this.pageLoadCallback = webPageLoadCallback;
    }

    public PLVWebview(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVWebview(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.pptPrepareParams = new LinkedHashMap();
        this.processors = new ArrayList();
        this.receiver = new BroadcastReceiver() { // from class: com.plv.foundationsdk.web.PLVWebview.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") && PLVNetworkUtils.isConnected(context2)) {
                    PLVWebview.this.loadWeb();
                }
            }
        };
        initialView(context);
        registerHandler();
        addCallback();
    }
}
