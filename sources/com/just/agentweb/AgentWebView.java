package com.just.agentweb;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.webkit.JsPromptResult;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.widget.Toast;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class AgentWebView extends LollipopFixedWebView {
    private static final String TAG = "AgentWebView";
    private FixedOnReceivedTitle mFixedOnReceivedTitle;
    private Map<String, String> mInjectJavaScripts;
    private Boolean mIsAccessibilityEnabledOriginal;
    private boolean mIsInited;
    private Map<String, JsCallJava> mJsCallJavas;

    public static class AgentWebChrome extends MiddlewareWebChromeBase {
        private AgentWebView mAgentWebView;

        private AgentWebChrome(AgentWebView agentWebView) {
            this.mAgentWebView = agentWebView;
        }

        @Override // com.just.agentweb.WebChromeClientDelegate, android.webkit.WebChromeClient
        public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
            JsCallJava jsCallJava;
            Log.i(AgentWebView.TAG, "onJsPrompt:" + str + "  message:" + str2 + "  d:" + str3 + "  ");
            if (this.mAgentWebView.mJsCallJavas == null || !JsCallJava.isSafeWebViewCallMsg(str2)) {
                return super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
            }
            JSONObject msgJSONObject = JsCallJava.getMsgJSONObject(str2);
            String interfacedName = JsCallJava.getInterfacedName(msgJSONObject);
            if (interfacedName == null || (jsCallJava = (JsCallJava) this.mAgentWebView.mJsCallJavas.get(interfacedName)) == null) {
                return true;
            }
            jsPromptResult.confirm(jsCallJava.call(webView, msgJSONObject));
            return true;
        }

        @Override // com.just.agentweb.WebChromeClientDelegate, android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i2) {
            if (this.mAgentWebView.mJsCallJavas != null) {
                this.mAgentWebView.injectJavaScript();
                if (LogUtils.isDebug()) {
                    Log.d(AgentWebView.TAG, "injectJavaScript, onProgressChanged.newProgress = " + i2 + ", url = " + webView.getUrl());
                }
            }
            if (this.mAgentWebView.mInjectJavaScripts != null) {
                this.mAgentWebView.injectExtraJavaScript();
            }
            super.onProgressChanged(webView, i2);
        }

        @Override // com.just.agentweb.WebChromeClientDelegate, android.webkit.WebChromeClient
        public void onReceivedTitle(WebView webView, String str) {
            this.mAgentWebView.mFixedOnReceivedTitle.onReceivedTitle();
            super.onReceivedTitle(webView, str);
        }
    }

    public static class AgentWebClient extends MiddlewareWebClientBase {
        private AgentWebView mAgentWebView;

        private AgentWebClient(AgentWebView agentWebView) {
            this.mAgentWebView = agentWebView;
        }

        @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            this.mAgentWebView.mFixedOnReceivedTitle.onPageFinished(webView);
            if (LogUtils.isDebug()) {
                Log.d(AgentWebView.TAG, "onPageFinished.url = " + webView.getUrl());
            }
        }

        @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            if (this.mAgentWebView.mJsCallJavas != null) {
                this.mAgentWebView.injectJavaScript();
                if (LogUtils.isDebug()) {
                    Log.d(AgentWebView.TAG, "injectJavaScript, onPageStarted.url = " + webView.getUrl());
                }
            }
            if (this.mAgentWebView.mInjectJavaScripts != null) {
                this.mAgentWebView.injectExtraJavaScript();
            }
            this.mAgentWebView.mFixedOnReceivedTitle.onPageStarted();
            this.mAgentWebView.fixedAccessibilityInjectorExceptionForOnPageFinished(str);
        }
    }

    public static class FixedOnReceivedTitle {
        private boolean mIsOnReceivedTitle;
        private android.webkit.WebChromeClient mWebChromeClient;

        private FixedOnReceivedTitle() {
        }

        public void onPageFinished(WebView webView) {
            WebBackForwardList webBackForwardListCopyBackForwardList;
            if (this.mIsOnReceivedTitle || this.mWebChromeClient == null) {
                return;
            }
            try {
                webBackForwardListCopyBackForwardList = webView.copyBackForwardList();
            } catch (NullPointerException e2) {
                if (LogUtils.isDebug()) {
                    e2.printStackTrace();
                }
                webBackForwardListCopyBackForwardList = null;
            }
            if (webBackForwardListCopyBackForwardList == null || webBackForwardListCopyBackForwardList.getSize() <= 0 || webBackForwardListCopyBackForwardList.getCurrentIndex() < 0 || webBackForwardListCopyBackForwardList.getItemAtIndex(webBackForwardListCopyBackForwardList.getCurrentIndex()) == null) {
                return;
            }
            this.mWebChromeClient.onReceivedTitle(webView, webBackForwardListCopyBackForwardList.getItemAtIndex(webBackForwardListCopyBackForwardList.getCurrentIndex()).getTitle());
        }

        public void onPageStarted() {
            this.mIsOnReceivedTitle = false;
        }

        public void onReceivedTitle() {
            this.mIsOnReceivedTitle = true;
        }

        public void setWebChromeClient(android.webkit.WebChromeClient webChromeClient) {
            this.mWebChromeClient = webChromeClient;
        }
    }

    public AgentWebView(Context context) {
        this(context, null);
    }

    public AgentWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        removeSearchBoxJavaBridge();
        this.mIsInited = true;
        this.mFixedOnReceivedTitle = new FixedOnReceivedTitle();
    }

    private void fixedStillAttached() {
        if (getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).removeAllViewsInLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void injectExtraJavaScript() {
        for (Map.Entry<String, String> entry : this.mInjectJavaScripts.entrySet()) {
            loadUrl(buildNotRepeatInjectJS(entry.getKey(), entry.getValue()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void injectJavaScript() {
        for (Map.Entry<String, JsCallJava> entry : this.mJsCallJavas.entrySet()) {
            loadUrl(buildNotRepeatInjectJS(entry.getKey(), entry.getValue().getPreloadInterfaceJs()));
        }
    }

    private boolean isAccessibilityEnabled() {
        return ((AccessibilityManager) getContext().getSystemService("accessibility")).isEnabled();
    }

    public static Pair<Boolean, String> isWebViewPackageException(Throwable th) {
        String string = th.getCause() == null ? th.toString() : th.getCause().toString();
        String stackTraceString = Log.getStackTraceString(th);
        if (!stackTraceString.contains("android.content.pm.PackageManager$NameNotFoundException") && !stackTraceString.contains("java.lang.RuntimeException: Cannot load WebView") && !stackTraceString.contains("android.webkit.WebViewFactory$MissingWebViewPackageException: Failed to load WebView provider: No WebView installed")) {
            return new Pair<>(Boolean.FALSE, string);
        }
        LogUtils.safeCheckCrash(TAG, "isWebViewPackageException", th);
        return new Pair<>(Boolean.TRUE, "WebView load failed, " + string);
    }

    private void releaseConfigCallback() {
    }

    private void resetAccessibilityEnabled() {
        Boolean bool = this.mIsAccessibilityEnabledOriginal;
        if (bool != null) {
            setAccessibilityEnabled(bool.booleanValue());
        }
    }

    private void setAccessibilityEnabled(boolean z2) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        try {
            Method declaredMethod = accessibilityManager.getClass().getDeclaredMethod("setAccessibilityState", Boolean.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(accessibilityManager, Boolean.valueOf(z2));
            declaredMethod.setAccessible(false);
        } catch (Throwable th) {
            if (LogUtils.isDebug()) {
                LogUtils.e(TAG, "setAccessibilityEnabled", th);
            }
        }
    }

    public void addInjectJavaScript(String str) {
        if (this.mInjectJavaScripts == null) {
            this.mInjectJavaScripts = new HashMap();
        }
        this.mInjectJavaScripts.put(String.valueOf(str.hashCode()), str);
        injectExtraJavaScript();
    }

    @Override // android.webkit.WebView
    @Deprecated
    public final void addJavascriptInterface(Object obj, String str) {
        super.addJavascriptInterface(obj, str);
        Log.i(TAG, "注入");
    }

    public void addJavascriptInterfaceSupport(Object obj, String str) {
    }

    public String buildNotRepeatInjectJS(String str, String str2) {
        String str3 = String.format("__injectFlag_%1$s__", str);
        return "javascript:try{(function(){if(window." + str3 + "){console.log('" + str3 + " has been injected');return;}window." + str3 + "=true;" + str2 + "}())}catch(e){console.warn(e)}";
    }

    public String buildTryCatchInjectJS(String str) {
        return "javascript:try{" + str + "}catch(e){console.warn(e)}";
    }

    @Override // android.webkit.WebView
    public void clearHistory() {
        if (this.mIsInited) {
            super.clearHistory();
        }
    }

    @Override // android.webkit.WebView
    public void destroy() {
        setVisibility(8);
        Map<String, JsCallJava> map = this.mJsCallJavas;
        if (map != null) {
            map.clear();
        }
        Map<String, String> map2 = this.mInjectJavaScripts;
        if (map2 != null) {
            map2.clear();
        }
        removeAllViewsInLayout();
        fixedStillAttached();
        releaseConfigCallback();
        if (this.mIsInited) {
            resetAccessibilityEnabled();
            LogUtils.i(TAG, "destroy web");
            super.destroy();
        }
    }

    public void fixedAccessibilityInjectorException() {
    }

    public void fixedAccessibilityInjectorExceptionForOnPageFinished(String str) {
    }

    @Override // android.webkit.WebView
    public boolean isPrivateBrowsingEnabled() {
        return super.isPrivateBrowsingEnabled();
    }

    @TargetApi(11)
    public boolean removeSearchBoxJavaBridge() {
        return false;
    }

    @Override // android.webkit.WebView, android.view.View
    public void setOverScrollMode(int i2) {
        try {
            super.setOverScrollMode(i2);
        } catch (Throwable th) {
            Pair<Boolean, String> pairIsWebViewPackageException = isWebViewPackageException(th);
            if (!((Boolean) pairIsWebViewPackageException.first).booleanValue()) {
                throw th;
            }
            Toast.makeText(getContext(), (CharSequence) pairIsWebViewPackageException.second, 0).show();
            destroy();
        }
    }

    @Override // android.webkit.WebView
    public final void setWebChromeClient(android.webkit.WebChromeClient webChromeClient) {
        AgentWebChrome agentWebChrome = new AgentWebChrome();
        agentWebChrome.setDelegate(webChromeClient);
        this.mFixedOnReceivedTitle.setWebChromeClient(webChromeClient);
        super.setWebChromeClient(agentWebChrome);
        setWebChromeClientSupport(agentWebChrome);
    }

    public final void setWebChromeClientSupport(android.webkit.WebChromeClient webChromeClient) {
    }

    @Override // android.webkit.WebView
    public final void setWebViewClient(android.webkit.WebViewClient webViewClient) {
        AgentWebClient agentWebClient = new AgentWebClient();
        agentWebClient.setDelegate(webViewClient);
        super.setWebViewClient(agentWebClient);
        setWebViewClientSupport(agentWebClient);
    }

    public final void setWebViewClientSupport(android.webkit.WebViewClient webViewClient) {
    }

    @TargetApi(19)
    public void trySetWebDebuggEnabled() {
        if (LogUtils.isDebug()) {
            try {
                WebView.class.getMethod("setWebContentsDebuggingEnabled", Boolean.TYPE).invoke(null, Boolean.TRUE);
            } catch (Throwable th) {
                if (LogUtils.isDebug()) {
                    th.printStackTrace();
                }
            }
        }
    }
}
