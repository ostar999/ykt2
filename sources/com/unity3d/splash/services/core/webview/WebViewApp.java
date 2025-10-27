package com.unity3d.splash.services.core.webview;

import android.os.ConditionVariable;
import android.os.Looper;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebViewClient;
import cn.hutool.core.text.StrPool;
import com.unity3d.splash.services.core.configuration.Configuration;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import com.unity3d.splash.services.core.webview.bridge.CallbackStatus;
import com.unity3d.splash.services.core.webview.bridge.Invocation;
import com.unity3d.splash.services.core.webview.bridge.NativeCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewBridge;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;

/* loaded from: classes6.dex */
public class WebViewApp extends WebViewClient {
    private static final int INVOKE_JS_CHARS_LENGTH = 22;
    private static ConditionVariable _conditionVariable;
    private static WebViewApp _currentApp;
    private Configuration _configuration;
    private boolean _initialized;
    private HashMap _nativeCallbacks;
    private boolean _webAppLoaded;
    private WebView _webView;

    public class WebAppChromeClient extends WebChromeClient {
        private WebAppChromeClient() {
        }

        @Override // android.webkit.WebChromeClient
        public void onConsoleMessage(String str, int i2, String str2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            File file;
            try {
                file = new File(str2);
            } catch (Exception e2) {
                DeviceLog.exception("Could not handle sourceId", e2);
                file = null;
            }
            if (file != null) {
                file.getName();
            }
        }
    }

    public class WebAppClient extends WebViewClient {
        private WebAppClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(android.webkit.WebView webView, String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            super.onPageFinished(webView, str);
            DeviceLog.debug("onPageFinished url: " + str);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(android.webkit.WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
            if (webView != null) {
                DeviceLog.error("WEBVIEW_ERROR: " + webView.toString());
            }
            if (webResourceRequest != null) {
                DeviceLog.error("WEBVIEW_ERROR: " + webResourceRequest.toString());
            }
            if (webResourceError != null) {
                DeviceLog.error("WEBVIEW_ERROR: " + webResourceError.toString());
            }
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(android.webkit.WebView webView, String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            DeviceLog.debug("Trying to load url: " + str);
            return false;
        }
    }

    public WebViewApp() {
        this._webAppLoaded = false;
        this._initialized = false;
    }

    private WebViewApp(Configuration configuration) {
        this._webAppLoaded = false;
        this._initialized = false;
        setConfiguration(configuration);
        WebViewBridge.setClassTable(getConfiguration().getWebAppApiClassList());
        WebView webView = new WebView(ClientProperties.getApplicationContext());
        this._webView = webView;
        webView.setWebViewClient(new WebAppClient());
        this._webView.setWebChromeClient(new WebAppChromeClient());
    }

    public static boolean create(final Configuration configuration) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.entered();
        if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
            throw new IllegalThreadStateException("Cannot call create() from main thread!");
        }
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.core.webview.WebViewApp.1
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                try {
                    WebViewApp webViewApp = new WebViewApp(configuration);
                    String str = "?platform=android";
                    try {
                        if (configuration.getWebViewUrl() != null) {
                            str = "?platform=android&origin=" + URLEncoder.encode(configuration.getWebViewUrl(), "UTF-8");
                        }
                    } catch (UnsupportedEncodingException e2) {
                        DeviceLog.exception("Unsupported charset when encoding origin url", e2);
                    }
                    try {
                        if (configuration.getWebViewVersion() != null) {
                            str = str + "&version=" + URLEncoder.encode(configuration.getWebViewVersion(), "UTF-8");
                        }
                    } catch (UnsupportedEncodingException e3) {
                        DeviceLog.exception("Unsupported charset when encoding webview version", e3);
                    }
                    webViewApp.getWebView().loadDataWithBaseURL("file://" + SdkProperties.getLocalWebViewFile() + str, configuration.getWebViewData(), "text/html", "UTF-8", null);
                    WebViewApp.setCurrentApp(webViewApp);
                } catch (Exception unused) {
                    DeviceLog.error("Couldn't construct WebViewApp");
                    WebViewApp._conditionVariable.open();
                }
            }
        });
        ConditionVariable conditionVariable = new ConditionVariable();
        _conditionVariable = conditionVariable;
        return conditionVariable.block(60000L) && getCurrentApp() != null;
    }

    public static WebViewApp getCurrentApp() {
        return _currentApp;
    }

    private void invokeJavascriptMethod(String str, String str2, JSONArray jSONArray) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        String string = jSONArray.toString();
        StringBuilder sb = new StringBuilder(str.length() + 22 + str2.length() + string.length());
        sb.append("javascript:window.");
        sb.append(str);
        sb.append(StrPool.DOT);
        sb.append(str2);
        sb.append("(");
        sb.append(string);
        sb.append(");");
        String string2 = sb.toString();
        DeviceLog.debug("Invoking javascript: " + string2);
        getWebView().invokeJavascript(string2);
    }

    public static void setCurrentApp(WebViewApp webViewApp) {
        _currentApp = webViewApp;
    }

    public void addCallback(NativeCallback nativeCallback) {
        if (this._nativeCallbacks == null) {
            this._nativeCallbacks = new HashMap();
        }
        synchronized (this._nativeCallbacks) {
            this._nativeCallbacks.put(nativeCallback.getId(), nativeCallback);
        }
    }

    public NativeCallback getCallback(String str) {
        NativeCallback nativeCallback;
        synchronized (this._nativeCallbacks) {
            nativeCallback = (NativeCallback) this._nativeCallbacks.get(str);
        }
        return nativeCallback;
    }

    public Configuration getConfiguration() {
        return this._configuration;
    }

    public WebView getWebView() {
        return this._webView;
    }

    public boolean invokeCallback(Invocation invocation) {
        if (!isWebAppLoaded()) {
            DeviceLog.debug("invokeBatchCallback ignored because web app is not loaded");
            return false;
        }
        JSONArray jSONArray = new JSONArray();
        ArrayList responses = invocation.getResponses();
        if (responses != null && !responses.isEmpty()) {
            Iterator it = responses.iterator();
            while (it.hasNext()) {
                ArrayList arrayList = (ArrayList) it.next();
                CallbackStatus callbackStatus = (CallbackStatus) arrayList.get(0);
                Enum r5 = (Enum) arrayList.get(1);
                Object[] objArr = (Object[]) arrayList.get(2);
                String str = (String) objArr[0];
                Object[] objArrCopyOfRange = Arrays.copyOfRange(objArr, 1, objArr.length);
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(str);
                arrayList2.add(callbackStatus.toString());
                JSONArray jSONArray2 = new JSONArray();
                if (r5 != null) {
                    jSONArray2.put(r5.name());
                }
                for (Object obj : objArrCopyOfRange) {
                    jSONArray2.put(obj);
                }
                arrayList2.add(jSONArray2);
                JSONArray jSONArray3 = new JSONArray();
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    jSONArray3.put(it2.next());
                }
                jSONArray.put(jSONArray3);
            }
        }
        try {
            invokeJavascriptMethod("nativebridge", "handleCallback", jSONArray);
        } catch (Exception e2) {
            DeviceLog.exception("Error while invoking batch response for WebView", e2);
        }
        return true;
    }

    public boolean invokeMethod(String str, String str2, Method method, Object... objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        String id;
        if (!isWebAppLoaded()) {
            DeviceLog.debug("invokeMethod ignored because web app is not loaded");
            return false;
        }
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(str);
        jSONArray.put(str2);
        if (method != null) {
            NativeCallback nativeCallback = new NativeCallback(method);
            addCallback(nativeCallback);
            id = nativeCallback.getId();
        } else {
            id = null;
        }
        jSONArray.put(id);
        if (objArr != null) {
            for (Object obj : objArr) {
                jSONArray.put(obj);
            }
        }
        try {
            invokeJavascriptMethod("nativebridge", "handleInvocation", jSONArray);
            return true;
        } catch (Exception e2) {
            DeviceLog.exception("Error invoking javascript method", e2);
            return false;
        }
    }

    public boolean isWebAppInitialized() {
        return this._initialized;
    }

    public boolean isWebAppLoaded() {
        return this._webAppLoaded;
    }

    public void removeCallback(NativeCallback nativeCallback) {
        HashMap map = this._nativeCallbacks;
        if (map == null) {
            return;
        }
        synchronized (map) {
            this._nativeCallbacks.remove(nativeCallback.getId());
        }
    }

    public boolean sendEvent(Enum r4, Enum r5, Object... objArr) {
        if (!isWebAppLoaded()) {
            DeviceLog.debug("sendEvent ignored because web app is not loaded");
            return false;
        }
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(r4.name());
        jSONArray.put(r5.name());
        for (Object obj : objArr) {
            jSONArray.put(obj);
        }
        try {
            invokeJavascriptMethod("nativebridge", "handleEvent", jSONArray);
            return true;
        } catch (Exception e2) {
            DeviceLog.exception("Error while sending event to WebView", e2);
            return false;
        }
    }

    public void setConfiguration(Configuration configuration) {
        this._configuration = configuration;
    }

    public void setWebAppInitialized(boolean z2) {
        this._initialized = z2;
        _conditionVariable.open();
    }

    public void setWebAppLoaded(boolean z2) {
        this._webAppLoaded = z2;
    }

    public void setWebView(WebView webView) {
        this._webView = webView;
    }
}
