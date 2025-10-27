package com.catchpig.mvvm.widget.dsbridge;

import android.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.annotation.Keep;
import androidx.appcompat.app.AlertDialog;
import com.github.lzyzsd.jsbridge.BridgeUtil;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.PermissionRequest;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebStorage;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class DWebView extends WebView {
    private static final String BRIDGE_NAME = "_dsbridge";
    private static final String LOG_TAG = "dsBridge";
    private static boolean isDebug = false;
    private String APP_CACHE_DIRNAME;
    private volatile boolean alertBoxBlock;
    private int callID;
    private ArrayList<CallInfo> callInfoList;
    private OnDWebViewListener dWebViewListerner;
    Map<Integer, OnReturnValue> handlerMap;
    private InnerJavascriptInterface innerJavascriptInterface;
    private Map<String, Object> javaScriptNamespaceInterfaces;
    private JavascriptCloseWindowListener javascriptCloseWindowListener;
    private WebChromeClient mWebChromeClient;
    private WebViewClient mWebViewClient;
    private Handler mainHandler;
    private WebChromeClient webChromeClient;
    private WebViewClient webViewClient;

    public static class CallInfo {
        private int callbackId;
        private String data;
        private String method;

        public CallInfo(String str, int i2, Object[] objArr) {
            this.data = new JSONArray((Collection) Arrays.asList(objArr == null ? new Object[0] : objArr)).toString();
            this.callbackId = i2;
            this.method = str;
        }

        public String toString() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("method", this.method);
                jSONObject.put("callbackId", this.callbackId);
                jSONObject.put("data", this.data);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            return jSONObject.toString();
        }
    }

    @Deprecated
    public interface FileChooser {
        @TargetApi(11)
        void openFileChooser(ValueCallback valueCallback, String str);

        @TargetApi(16)
        void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2);
    }

    public class InnerJavascriptInterface {
        private InnerJavascriptInterface() {
        }

        private void PrintDebugInfo(String str) {
            Log.d(DWebView.LOG_TAG, str);
            if (DWebView.isDebug) {
                DWebView.this.evaluateJavascript(String.format("alert('%s')", "DEBUG ERR MSG:\\n" + str.replaceAll("\\'", "\\\\'")));
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x0078  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0096  */
        @android.webkit.JavascriptInterface
        @androidx.annotation.Keep
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.String call(java.lang.String r14, java.lang.String r15) throws org.json.JSONException, java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
            /*
                Method dump skipped, instructions count: 277
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.widget.dsbridge.DWebView.InnerJavascriptInterface.call(java.lang.String, java.lang.String):java.lang.String");
        }
    }

    public interface JavascriptCloseWindowListener {
        boolean onClose();
    }

    public interface OnDWebViewListener {
        void onPageFinished(DWebView dWebView, String str);

        void onPageStarted(DWebView dWebView, String str, Bitmap bitmap);

        void onProgressChanged(DWebView dWebView, int i2);

        void onReceivedTitle(DWebView dWebView, String str);
    }

    public DWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.javaScriptNamespaceInterfaces = new HashMap();
        this.callID = 0;
        this.alertBoxBlock = true;
        this.javascriptCloseWindowListener = null;
        this.innerJavascriptInterface = new InnerJavascriptInterface();
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.handlerMap = new HashMap();
        this.mWebChromeClient = new WebChromeClient() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.6
            @Override // com.tencent.smtt.sdk.WebChromeClient
            public Bitmap getDefaultVideoPoster() {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.getDefaultVideoPoster() : super.getDefaultVideoPoster();
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public View getVideoLoadingProgressView() {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.getVideoLoadingProgressView() : super.getVideoLoadingProgressView();
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.getVisitedHistory(valueCallback);
                } else {
                    super.getVisitedHistory(valueCallback);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onCloseWindow(WebView webView) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onCloseWindow(webView);
                } else {
                    super.onCloseWindow(webView);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.onConsoleMessage(consoleMessage) : super.onConsoleMessage(consoleMessage);
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onCreateWindow(WebView webView, boolean z2, boolean z3, Message message) {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.onCreateWindow(webView, z2, z3, message) : super.onCreateWindow(webView, z2, z3, message);
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onExceededDatabaseQuota(String str, String str2, long j2, long j3, long j4, WebStorage.QuotaUpdater quotaUpdater) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onExceededDatabaseQuota(str, str2, j2, j3, j4, quotaUpdater);
                } else {
                    super.onExceededDatabaseQuota(str, str2, j2, j3, j4, quotaUpdater);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onGeolocationPermissionsHidePrompt() {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onGeolocationPermissionsHidePrompt();
                } else {
                    super.onGeolocationPermissionsHidePrompt();
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissionsCallback geolocationPermissionsCallback) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onGeolocationPermissionsShowPrompt(str, geolocationPermissionsCallback);
                } else {
                    super.onGeolocationPermissionsShowPrompt(str, geolocationPermissionsCallback);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onHideCustomView() {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onHideCustomView();
                } else {
                    super.onHideCustomView();
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onJsAlert(WebView webView, String str, String str2, final JsResult jsResult) {
                if (!DWebView.this.alertBoxBlock) {
                    jsResult.confirm();
                }
                if (DWebView.this.webChromeClient != null && DWebView.this.webChromeClient.onJsAlert(webView, str, str2, jsResult)) {
                    return true;
                }
                new AlertDialog.Builder(DWebView.this.getContext()).setMessage(str2).setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.6.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        dialogInterface.dismiss();
                        if (DWebView.this.alertBoxBlock) {
                            jsResult.confirm();
                        }
                    }
                }).create().show();
                return true;
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.onJsBeforeUnload(webView, str, str2, jsResult) : super.onJsBeforeUnload(webView, str, str2, jsResult);
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onJsConfirm(WebView webView, String str, String str2, final JsResult jsResult) {
                if (!DWebView.this.alertBoxBlock) {
                    jsResult.confirm();
                }
                if (DWebView.this.webChromeClient != null && DWebView.this.webChromeClient.onJsConfirm(webView, str, str2, jsResult)) {
                    return true;
                }
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.6.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        if (DWebView.this.alertBoxBlock) {
                            if (i2 == -1) {
                                jsResult.confirm();
                            } else {
                                jsResult.cancel();
                            }
                        }
                    }
                };
                new AlertDialog.Builder(DWebView.this.getContext()).setMessage(str2).setCancelable(false).setPositiveButton(R.string.ok, onClickListener).setNegativeButton(R.string.cancel, onClickListener).show();
                return true;
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onJsPrompt(WebView webView, String str, String str2, String str3, final JsPromptResult jsPromptResult) {
                if (!DWebView.this.alertBoxBlock) {
                    jsPromptResult.confirm();
                }
                if (DWebView.this.webChromeClient != null && DWebView.this.webChromeClient.onJsPrompt(webView, str, str2, str3, jsPromptResult)) {
                    return true;
                }
                final EditText editText = new EditText(DWebView.this.getContext());
                editText.setText(str3);
                if (str3 != null) {
                    editText.setSelection(str3.length());
                }
                float f2 = DWebView.this.getContext().getResources().getDisplayMetrics().density;
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.6.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        if (DWebView.this.alertBoxBlock) {
                            if (i2 == -1) {
                                jsPromptResult.confirm(editText.getText().toString());
                            } else {
                                jsPromptResult.cancel();
                            }
                        }
                    }
                };
                new AlertDialog.Builder(DWebView.this.getContext()).setTitle(str2).setView(editText).setCancelable(false).setPositiveButton(R.string.ok, onClickListener).setNegativeButton(R.string.cancel, onClickListener).show();
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                int i2 = (int) (16.0f * f2);
                layoutParams.setMargins(i2, 0, i2, 0);
                layoutParams.gravity = 1;
                editText.setLayoutParams(layoutParams);
                int i3 = (int) (15.0f * f2);
                editText.setPadding(i3 - ((int) (f2 * 5.0f)), i3, i3, i3);
                return true;
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onJsTimeout() {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.onJsTimeout() : super.onJsTimeout();
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            @TargetApi(21)
            public void onPermissionRequest(PermissionRequest permissionRequest) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onPermissionRequest(permissionRequest);
                } else {
                    super.onPermissionRequest(permissionRequest);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            @TargetApi(21)
            public void onPermissionRequestCanceled(PermissionRequest permissionRequest) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onPermissionRequestCanceled(permissionRequest);
                } else {
                    super.onPermissionRequestCanceled(permissionRequest);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onProgressChanged(WebView webView, int i2) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onProgressChanged(webView, i2);
                } else {
                    super.onProgressChanged(webView, i2);
                }
                if (DWebView.this.dWebViewListerner != null) {
                    DWebView.this.dWebViewListerner.onProgressChanged(DWebView.this, i2);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onReachedMaxAppCacheSize(long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onReachedMaxAppCacheSize(j2, j3, quotaUpdater);
                }
                super.onReachedMaxAppCacheSize(j2, j3, quotaUpdater);
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onReceivedIcon(WebView webView, Bitmap bitmap) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onReceivedIcon(webView, bitmap);
                } else {
                    super.onReceivedIcon(webView, bitmap);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onReceivedTitle(WebView webView, String str) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onReceivedTitle(webView, str);
                } else {
                    super.onReceivedTitle(webView, str);
                }
                if (DWebView.this.dWebViewListerner != null) {
                    DWebView.this.dWebViewListerner.onReceivedTitle(DWebView.this, str);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onReceivedTouchIconUrl(WebView webView, String str, boolean z2) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onReceivedTouchIconUrl(webView, str, z2);
                } else {
                    super.onReceivedTouchIconUrl(webView, str, z2);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onRequestFocus(WebView webView) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onRequestFocus(webView);
                } else {
                    super.onRequestFocus(webView);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onShowCustomView(view, customViewCallback);
                } else {
                    super.onShowCustomView(view, customViewCallback);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            @TargetApi(21)
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.onShowFileChooser(webView, valueCallback, fileChooserParams) : super.onShowFileChooser(webView, valueCallback, fileChooserParams);
            }

            @Keep
            @TargetApi(11)
            public void openFileChooser(ValueCallback valueCallback, String str) {
                if (DWebView.this.webChromeClient instanceof FileChooser) {
                    ((FileChooser) DWebView.this.webChromeClient).openFileChooser(valueCallback, str);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            @Keep
            @TargetApi(16)
            public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
                if (DWebView.this.webChromeClient instanceof FileChooser) {
                    ((FileChooser) DWebView.this.webChromeClient).openFileChooser(valueCallback, str, str2);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            @TargetApi(14)
            public void onShowCustomView(View view, int i2, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onShowCustomView(view, i2, customViewCallback);
                } else {
                    super.onShowCustomView(view, i2, customViewCallback);
                }
            }
        };
        this.mWebViewClient = new WebViewClient() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.7
            @Override // com.tencent.smtt.sdk.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                if (DWebView.this.webViewClient != null) {
                    DWebView.this.webViewClient.onPageFinished(webView, str);
                } else {
                    super.onPageFinished(webView, str);
                }
                if (DWebView.this.dWebViewListerner != null) {
                    DWebView.this.dWebViewListerner.onPageFinished(DWebView.this, str);
                }
            }

            @Override // com.tencent.smtt.sdk.WebViewClient
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                if (DWebView.this.webViewClient != null) {
                    DWebView.this.webViewClient.onPageStarted(webView, str, bitmap);
                } else {
                    super.onPageStarted(webView, str, bitmap);
                }
                if (DWebView.this.dWebViewListerner != null) {
                    DWebView.this.dWebViewListerner.onPageStarted(DWebView.this, str, bitmap);
                }
            }

            @Override // com.tencent.smtt.sdk.WebViewClient
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
            }

            @Override // com.tencent.smtt.sdk.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        };
        init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _evaluateJavascript(String str) {
        super.evaluateJavascript(str, null);
    }

    @Keep
    private void addInternalJavascriptObject() {
        addJavascriptObject(new Object() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.1
            @JavascriptInterface
            @Keep
            public String closePage(Object obj) throws JSONException {
                DWebView.this.runOnMainThread(new Runnable() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DWebView.this.javascriptCloseWindowListener == null || DWebView.this.javascriptCloseWindowListener.onClose()) {
                            Context context = DWebView.this.getContext();
                            if (context instanceof Activity) {
                                ((Activity) context).onBackPressed();
                            }
                        }
                    }
                });
                return null;
            }

            @JavascriptInterface
            @Keep
            public void disableJavascriptDialogBlock(Object obj) throws JSONException {
                DWebView.this.alertBoxBlock = !((JSONObject) obj).getBoolean("disable");
            }

            @JavascriptInterface
            @Keep
            public void dsinit(Object obj) {
                DWebView.this.dispatchStartupQueue();
            }

            /* JADX WARN: Removed duplicated region for block: B:12:0x0052  */
            @android.webkit.JavascriptInterface
            @androidx.annotation.Keep
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean hasNativeMethod(java.lang.Object r9) throws org.json.JSONException, java.lang.NoSuchMethodException, java.lang.SecurityException {
                /*
                    r8 = this;
                    java.lang.Class<java.lang.Object> r0 = java.lang.Object.class
                    org.json.JSONObject r9 = (org.json.JSONObject) r9
                    java.lang.String r1 = "name"
                    java.lang.String r1 = r9.getString(r1)
                    java.lang.String r1 = r1.trim()
                    java.lang.String r2 = "type"
                    java.lang.String r9 = r9.getString(r2)
                    java.lang.String r9 = r9.trim()
                    com.catchpig.mvvm.widget.dsbridge.DWebView r2 = com.catchpig.mvvm.widget.dsbridge.DWebView.this
                    java.lang.String[] r1 = com.catchpig.mvvm.widget.dsbridge.DWebView.v(r2, r1)
                    com.catchpig.mvvm.widget.dsbridge.DWebView r2 = com.catchpig.mvvm.widget.dsbridge.DWebView.this
                    java.util.Map r2 = com.catchpig.mvvm.widget.dsbridge.DWebView.n(r2)
                    r3 = 0
                    r4 = r1[r3]
                    java.lang.Object r2 = r2.get(r4)
                    if (r2 == 0) goto L7a
                    java.lang.Class r2 = r2.getClass()
                    r4 = 1
                    r5 = r1[r4]     // Catch: java.lang.Exception -> L43
                    r6 = 2
                    java.lang.Class[] r6 = new java.lang.Class[r6]     // Catch: java.lang.Exception -> L43
                    r6[r3] = r0     // Catch: java.lang.Exception -> L43
                    java.lang.Class<com.catchpig.mvvm.widget.dsbridge.CompletionHandler> r7 = com.catchpig.mvvm.widget.dsbridge.CompletionHandler.class
                    r6[r4] = r7     // Catch: java.lang.Exception -> L43
                    java.lang.reflect.Method r0 = r2.getMethod(r5, r6)     // Catch: java.lang.Exception -> L43
                    r1 = r4
                    goto L50
                L43:
                    r1 = r1[r4]     // Catch: java.lang.Exception -> L4e
                    java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch: java.lang.Exception -> L4e
                    r5[r3] = r0     // Catch: java.lang.Exception -> L4e
                    java.lang.reflect.Method r0 = r2.getMethod(r1, r5)     // Catch: java.lang.Exception -> L4e
                    goto L4f
                L4e:
                    r0 = 0
                L4f:
                    r1 = r3
                L50:
                    if (r0 == 0) goto L7a
                    java.lang.Class<android.webkit.JavascriptInterface> r2 = android.webkit.JavascriptInterface.class
                    java.lang.annotation.Annotation r0 = r0.getAnnotation(r2)
                    android.webkit.JavascriptInterface r0 = (android.webkit.JavascriptInterface) r0
                    if (r0 != 0) goto L5d
                    return r3
                L5d:
                    java.lang.String r0 = "all"
                    boolean r0 = r0.equals(r9)
                    if (r0 != 0) goto L79
                    if (r1 == 0) goto L6f
                    java.lang.String r0 = "asyn"
                    boolean r0 = r0.equals(r9)
                    if (r0 != 0) goto L79
                L6f:
                    if (r1 != 0) goto L7a
                    java.lang.String r0 = "syn"
                    boolean r9 = r0.equals(r9)
                    if (r9 == 0) goto L7a
                L79:
                    return r4
                L7a:
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.widget.dsbridge.DWebView.AnonymousClass1.hasNativeMethod(java.lang.Object):boolean");
            }

            @JavascriptInterface
            @Keep
            public void returnValue(final Object obj) {
                DWebView.this.runOnMainThread(new Runnable() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.1.2
                    @Override // java.lang.Runnable
                    public void run() throws JSONException {
                        JSONObject jSONObject = (JSONObject) obj;
                        try {
                            int i2 = jSONObject.getInt("id");
                            boolean z2 = jSONObject.getBoolean("complete");
                            OnReturnValue onReturnValue = DWebView.this.handlerMap.get(Integer.valueOf(i2));
                            Object obj2 = jSONObject.has("data") ? jSONObject.get("data") : null;
                            if (onReturnValue != null) {
                                onReturnValue.onValue(obj2);
                                if (z2) {
                                    DWebView.this.handlerMap.remove(Integer.valueOf(i2));
                                }
                            }
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                });
            }
        }, "_dsb");
    }

    private void dispatchJavascriptCall(CallInfo callInfo) {
        evaluateJavascript(String.format("window._handleMessageFromNative(%s)", callInfo.toString()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void dispatchStartupQueue() {
        ArrayList<CallInfo> arrayList = this.callInfoList;
        if (arrayList != null) {
            Iterator<CallInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                dispatchJavascriptCall(it.next());
            }
            this.callInfoList = null;
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void init() {
        this.APP_CACHE_DIRNAME = getContext().getFilesDir().getAbsolutePath() + "/webcache";
        WebSettings settings = getSettings();
        settings.setDomStorageEnabled(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
        settings.setMixedContentMode(0);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(-1);
        settings.setJavaScriptEnabled(true);
        settings.setAppCachePath(this.APP_CACHE_DIRNAME);
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setTextZoom(100);
        super.setWebViewClient(this.mWebViewClient);
        super.setWebChromeClient(this.mWebChromeClient);
        addInternalJavascriptObject();
        super.addJavascriptInterface(this.innerJavascriptInterface, BRIDGE_NAME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String[] parseNamespace(String str) {
        String strSubstring;
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf != -1) {
            strSubstring = str.substring(0, iLastIndexOf);
            str = str.substring(iLastIndexOf + 1);
        } else {
            strSubstring = "";
        }
        return new String[]{strSubstring, str};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runOnMainThread(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            this.mainHandler.post(runnable);
        }
    }

    public static void setWebContentsDebuggingEnabled(boolean z2) {
        WebView.setWebContentsDebuggingEnabled(z2);
        isDebug = z2;
    }

    public void addJavascriptObject(Object obj, String str) {
        if (str == null) {
            str = "";
        }
        if (obj != null) {
            this.javaScriptNamespaceInterfaces.put(str, obj);
        }
    }

    public synchronized <T> void callHandler(String str, Object[] objArr, OnReturnValue<T> onReturnValue) {
        int i2 = this.callID + 1;
        this.callID = i2;
        CallInfo callInfo = new CallInfo(str, i2, objArr);
        if (onReturnValue != null) {
            this.handlerMap.put(Integer.valueOf(callInfo.callbackId), onReturnValue);
        }
        ArrayList<CallInfo> arrayList = this.callInfoList;
        if (arrayList != null) {
            arrayList.add(callInfo);
        } else {
            dispatchJavascriptCall(callInfo);
        }
    }

    @Override // com.tencent.smtt.sdk.WebView
    public void clearCache(boolean z2) {
        super.clearCache(z2);
        CookieManager.getInstance().removeAllCookie();
        Context context = getContext();
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        File file = new File(this.APP_CACHE_DIRNAME);
        File file2 = new File(context.getCacheDir().getAbsolutePath() + "/webviewCache");
        if (file2.exists()) {
            deleteFile(file2);
        }
        if (file.exists()) {
            deleteFile(file);
        }
    }

    public void deleteFile(File file) {
        if (!file.exists()) {
            Log.e("Webview", "delete file no exists " + file.getAbsolutePath());
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                deleteFile(file2);
            }
        }
        file.delete();
    }

    public void disableJavascriptDialogBlock(boolean z2) {
        this.alertBoxBlock = !z2;
    }

    public void evaluateJavascript(final String str) {
        runOnMainThread(new Runnable() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.2
            @Override // java.lang.Runnable
            public void run() {
                DWebView.this._evaluateJavascript(str);
            }
        });
    }

    public void hasJavascriptMethod(String str, OnReturnValue<Boolean> onReturnValue) {
        callHandler("_hasJavascriptMethod", new Object[]{str}, onReturnValue);
    }

    @Override // com.tencent.smtt.sdk.WebView
    public void loadUrl(final String str) {
        runOnMainThread(new Runnable() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.3
            @Override // java.lang.Runnable
            public void run() {
                String str2 = str;
                if (str2 != null && str2.startsWith(BridgeUtil.JAVASCRIPT_STR)) {
                    DWebView.super.loadUrl(str);
                    return;
                }
                DWebView.this.callInfoList = new ArrayList();
                DWebView.super.loadUrl(str);
            }
        });
    }

    @Override // com.tencent.smtt.sdk.WebView
    public void reload() {
        runOnMainThread(new Runnable() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.5
            @Override // java.lang.Runnable
            public void run() {
                DWebView.this.callInfoList = new ArrayList();
                DWebView.super.reload();
            }
        });
    }

    public void removeJavascriptObject(String str) {
        if (str == null) {
            str = "";
        }
        this.javaScriptNamespaceInterfaces.remove(str);
    }

    public void setJavascriptCloseWindowListener(JavascriptCloseWindowListener javascriptCloseWindowListener) {
        this.javascriptCloseWindowListener = javascriptCloseWindowListener;
    }

    public DWebView setOnDWebViewListener(OnDWebViewListener onDWebViewListener) {
        this.dWebViewListerner = onDWebViewListener;
        return this;
    }

    @Override // com.tencent.smtt.sdk.WebView
    public void setWebChromeClient(WebChromeClient webChromeClient) {
        this.webChromeClient = webChromeClient;
    }

    @Override // com.tencent.smtt.sdk.WebView
    public void setWebViewClient(WebViewClient webViewClient) {
        this.webViewClient = webViewClient;
    }

    @Override // com.tencent.smtt.sdk.WebView
    public void loadUrl(final String str, final Map<String, String> map) {
        runOnMainThread(new Runnable() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.4
            @Override // java.lang.Runnable
            public void run() {
                String str2 = str;
                if (str2 != null && str2.startsWith(BridgeUtil.JAVASCRIPT_STR)) {
                    DWebView.super.loadUrl(str, map);
                    return;
                }
                DWebView.this.callInfoList = new ArrayList();
                DWebView.super.loadUrl(str, map);
            }
        });
    }

    public void callHandler(String str, Object[] objArr) {
        callHandler(str, objArr, null);
    }

    public <T> void callHandler(String str, OnReturnValue<T> onReturnValue) {
        callHandler(str, null, onReturnValue);
    }

    public DWebView(Context context) {
        super(context);
        this.javaScriptNamespaceInterfaces = new HashMap();
        this.callID = 0;
        this.alertBoxBlock = true;
        this.javascriptCloseWindowListener = null;
        this.innerJavascriptInterface = new InnerJavascriptInterface();
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.handlerMap = new HashMap();
        this.mWebChromeClient = new WebChromeClient() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.6
            @Override // com.tencent.smtt.sdk.WebChromeClient
            public Bitmap getDefaultVideoPoster() {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.getDefaultVideoPoster() : super.getDefaultVideoPoster();
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public View getVideoLoadingProgressView() {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.getVideoLoadingProgressView() : super.getVideoLoadingProgressView();
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.getVisitedHistory(valueCallback);
                } else {
                    super.getVisitedHistory(valueCallback);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onCloseWindow(WebView webView) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onCloseWindow(webView);
                } else {
                    super.onCloseWindow(webView);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.onConsoleMessage(consoleMessage) : super.onConsoleMessage(consoleMessage);
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onCreateWindow(WebView webView, boolean z2, boolean z3, Message message) {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.onCreateWindow(webView, z2, z3, message) : super.onCreateWindow(webView, z2, z3, message);
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onExceededDatabaseQuota(String str, String str2, long j2, long j3, long j4, WebStorage.QuotaUpdater quotaUpdater) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onExceededDatabaseQuota(str, str2, j2, j3, j4, quotaUpdater);
                } else {
                    super.onExceededDatabaseQuota(str, str2, j2, j3, j4, quotaUpdater);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onGeolocationPermissionsHidePrompt() {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onGeolocationPermissionsHidePrompt();
                } else {
                    super.onGeolocationPermissionsHidePrompt();
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissionsCallback geolocationPermissionsCallback) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onGeolocationPermissionsShowPrompt(str, geolocationPermissionsCallback);
                } else {
                    super.onGeolocationPermissionsShowPrompt(str, geolocationPermissionsCallback);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onHideCustomView() {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onHideCustomView();
                } else {
                    super.onHideCustomView();
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onJsAlert(WebView webView, String str, String str2, final JsResult jsResult) {
                if (!DWebView.this.alertBoxBlock) {
                    jsResult.confirm();
                }
                if (DWebView.this.webChromeClient != null && DWebView.this.webChromeClient.onJsAlert(webView, str, str2, jsResult)) {
                    return true;
                }
                new AlertDialog.Builder(DWebView.this.getContext()).setMessage(str2).setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.6.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        dialogInterface.dismiss();
                        if (DWebView.this.alertBoxBlock) {
                            jsResult.confirm();
                        }
                    }
                }).create().show();
                return true;
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.onJsBeforeUnload(webView, str, str2, jsResult) : super.onJsBeforeUnload(webView, str, str2, jsResult);
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onJsConfirm(WebView webView, String str, String str2, final JsResult jsResult) {
                if (!DWebView.this.alertBoxBlock) {
                    jsResult.confirm();
                }
                if (DWebView.this.webChromeClient != null && DWebView.this.webChromeClient.onJsConfirm(webView, str, str2, jsResult)) {
                    return true;
                }
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.6.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        if (DWebView.this.alertBoxBlock) {
                            if (i2 == -1) {
                                jsResult.confirm();
                            } else {
                                jsResult.cancel();
                            }
                        }
                    }
                };
                new AlertDialog.Builder(DWebView.this.getContext()).setMessage(str2).setCancelable(false).setPositiveButton(R.string.ok, onClickListener).setNegativeButton(R.string.cancel, onClickListener).show();
                return true;
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onJsPrompt(WebView webView, String str, String str2, String str3, final JsPromptResult jsPromptResult) {
                if (!DWebView.this.alertBoxBlock) {
                    jsPromptResult.confirm();
                }
                if (DWebView.this.webChromeClient != null && DWebView.this.webChromeClient.onJsPrompt(webView, str, str2, str3, jsPromptResult)) {
                    return true;
                }
                final EditText editText = new EditText(DWebView.this.getContext());
                editText.setText(str3);
                if (str3 != null) {
                    editText.setSelection(str3.length());
                }
                float f2 = DWebView.this.getContext().getResources().getDisplayMetrics().density;
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.6.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        if (DWebView.this.alertBoxBlock) {
                            if (i2 == -1) {
                                jsPromptResult.confirm(editText.getText().toString());
                            } else {
                                jsPromptResult.cancel();
                            }
                        }
                    }
                };
                new AlertDialog.Builder(DWebView.this.getContext()).setTitle(str2).setView(editText).setCancelable(false).setPositiveButton(R.string.ok, onClickListener).setNegativeButton(R.string.cancel, onClickListener).show();
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
                int i2 = (int) (16.0f * f2);
                layoutParams.setMargins(i2, 0, i2, 0);
                layoutParams.gravity = 1;
                editText.setLayoutParams(layoutParams);
                int i3 = (int) (15.0f * f2);
                editText.setPadding(i3 - ((int) (f2 * 5.0f)), i3, i3, i3);
                return true;
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public boolean onJsTimeout() {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.onJsTimeout() : super.onJsTimeout();
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            @TargetApi(21)
            public void onPermissionRequest(PermissionRequest permissionRequest) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onPermissionRequest(permissionRequest);
                } else {
                    super.onPermissionRequest(permissionRequest);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            @TargetApi(21)
            public void onPermissionRequestCanceled(PermissionRequest permissionRequest) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onPermissionRequestCanceled(permissionRequest);
                } else {
                    super.onPermissionRequestCanceled(permissionRequest);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onProgressChanged(WebView webView, int i2) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onProgressChanged(webView, i2);
                } else {
                    super.onProgressChanged(webView, i2);
                }
                if (DWebView.this.dWebViewListerner != null) {
                    DWebView.this.dWebViewListerner.onProgressChanged(DWebView.this, i2);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onReachedMaxAppCacheSize(long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onReachedMaxAppCacheSize(j2, j3, quotaUpdater);
                }
                super.onReachedMaxAppCacheSize(j2, j3, quotaUpdater);
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onReceivedIcon(WebView webView, Bitmap bitmap) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onReceivedIcon(webView, bitmap);
                } else {
                    super.onReceivedIcon(webView, bitmap);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onReceivedTitle(WebView webView, String str) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onReceivedTitle(webView, str);
                } else {
                    super.onReceivedTitle(webView, str);
                }
                if (DWebView.this.dWebViewListerner != null) {
                    DWebView.this.dWebViewListerner.onReceivedTitle(DWebView.this, str);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onReceivedTouchIconUrl(WebView webView, String str, boolean z2) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onReceivedTouchIconUrl(webView, str, z2);
                } else {
                    super.onReceivedTouchIconUrl(webView, str, z2);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onRequestFocus(WebView webView) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onRequestFocus(webView);
                } else {
                    super.onRequestFocus(webView);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onShowCustomView(view, customViewCallback);
                } else {
                    super.onShowCustomView(view, customViewCallback);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            @TargetApi(21)
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                return DWebView.this.webChromeClient != null ? DWebView.this.webChromeClient.onShowFileChooser(webView, valueCallback, fileChooserParams) : super.onShowFileChooser(webView, valueCallback, fileChooserParams);
            }

            @Keep
            @TargetApi(11)
            public void openFileChooser(ValueCallback valueCallback, String str) {
                if (DWebView.this.webChromeClient instanceof FileChooser) {
                    ((FileChooser) DWebView.this.webChromeClient).openFileChooser(valueCallback, str);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            @Keep
            @TargetApi(16)
            public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
                if (DWebView.this.webChromeClient instanceof FileChooser) {
                    ((FileChooser) DWebView.this.webChromeClient).openFileChooser(valueCallback, str, str2);
                }
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient
            @TargetApi(14)
            public void onShowCustomView(View view, int i2, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                if (DWebView.this.webChromeClient != null) {
                    DWebView.this.webChromeClient.onShowCustomView(view, i2, customViewCallback);
                } else {
                    super.onShowCustomView(view, i2, customViewCallback);
                }
            }
        };
        this.mWebViewClient = new WebViewClient() { // from class: com.catchpig.mvvm.widget.dsbridge.DWebView.7
            @Override // com.tencent.smtt.sdk.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                if (DWebView.this.webViewClient != null) {
                    DWebView.this.webViewClient.onPageFinished(webView, str);
                } else {
                    super.onPageFinished(webView, str);
                }
                if (DWebView.this.dWebViewListerner != null) {
                    DWebView.this.dWebViewListerner.onPageFinished(DWebView.this, str);
                }
            }

            @Override // com.tencent.smtt.sdk.WebViewClient
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                if (DWebView.this.webViewClient != null) {
                    DWebView.this.webViewClient.onPageStarted(webView, str, bitmap);
                } else {
                    super.onPageStarted(webView, str, bitmap);
                }
                if (DWebView.this.dWebViewListerner != null) {
                    DWebView.this.dWebViewListerner.onPageStarted(DWebView.this, str, bitmap);
                }
            }

            @Override // com.tencent.smtt.sdk.WebViewClient
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
            }

            @Override // com.tencent.smtt.sdk.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        };
        init();
    }
}
