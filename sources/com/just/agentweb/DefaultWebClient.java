package com.just.agentweb;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import androidx.annotation.RequiresApi;
import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public class DefaultWebClient extends MiddlewareWebClientBase {
    public static final String ALIPAYS_SCHEME = "alipays://";
    public static final int ASK_USER_OPEN_OTHER_PAGE = 250;
    private static final int CONSTANTS_ABNORMAL_BIG = 7;
    public static final int DERECT_OPEN_OTHER_PAGE = 1001;
    public static final int DISALLOW_OPEN_OTHER_APP = 62;
    private static final boolean HAS_ALIPAY_LIB;
    public static final String HTTPS_SCHEME = "https://";
    public static final String HTTP_SCHEME = "http://";
    public static final String INTENT_SCHEME = "intent://";
    public static final String SCHEME_SMS = "sms:";
    private static final String TAG = "DefaultWebClient";
    public static final String WEBCHAT_PAY_SCHEME = "weixin://wap/pay?";
    private WeakReference<AbsAgentWebUIController> mAgentWebUIController;
    private Handler.Callback mCallback;
    private Set<String> mErrorUrlsSet;
    private boolean mIsInterceptUnkownUrl;
    private Object mPayTask;
    private int mUrlHandleWays;
    private Set<String> mWaittingFinishSet;
    private WeakReference<Activity> mWeakReference;
    private WebView mWebView;
    private android.webkit.WebViewClient mWebViewClient;
    private Method onMainFrameErrorMethod;
    private boolean webClientHelper;

    public static class Builder {
        private Activity mActivity;
        private android.webkit.WebViewClient mClient;
        private boolean mIsInterceptUnkownScheme = true;
        private PermissionInterceptor mPermissionInterceptor;
        private int mUrlHandleWays;
        private boolean mWebClientHelper;
        private WebView mWebView;

        public DefaultWebClient build() {
            return new DefaultWebClient(this);
        }

        public Builder setActivity(Activity activity) {
            this.mActivity = activity;
            return this;
        }

        public Builder setClient(android.webkit.WebViewClient webViewClient) {
            this.mClient = webViewClient;
            return this;
        }

        public Builder setInterceptUnkownUrl(boolean z2) {
            this.mIsInterceptUnkownScheme = z2;
            return this;
        }

        public Builder setPermissionInterceptor(PermissionInterceptor permissionInterceptor) {
            this.mPermissionInterceptor = permissionInterceptor;
            return this;
        }

        public Builder setUrlHandleWays(int i2) {
            this.mUrlHandleWays = i2;
            return this;
        }

        public Builder setWebClientHelper(boolean z2) {
            this.mWebClientHelper = z2;
            return this;
        }

        public Builder setWebView(WebView webView) {
            this.mWebView = webView;
            return this;
        }
    }

    public enum OpenOtherPageWays {
        DERECT(1001),
        ASK(250),
        DISALLOW(62);

        int code;

        OpenOtherPageWays(int i2) {
            this.code = i2;
        }
    }

    static {
        boolean z2;
        try {
            Class.forName("com.alipay.sdk.app.PayTask");
            z2 = true;
        } catch (Throwable unused) {
            z2 = false;
        }
        HAS_ALIPAY_LIB = z2;
        LogUtils.i(TAG, "HAS_ALIPAY_LIB:" + z2);
    }

    public DefaultWebClient(Builder builder) {
        super(builder.mClient);
        this.mWeakReference = null;
        this.webClientHelper = true;
        this.mUrlHandleWays = 250;
        this.mIsInterceptUnkownUrl = true;
        this.mAgentWebUIController = null;
        this.mCallback = null;
        this.onMainFrameErrorMethod = null;
        this.mErrorUrlsSet = new HashSet();
        this.mWaittingFinishSet = new HashSet();
        this.mWebView = builder.mWebView;
        this.mWebViewClient = builder.mClient;
        this.mWeakReference = new WeakReference<>(builder.mActivity);
        this.webClientHelper = builder.mWebClientHelper;
        this.mAgentWebUIController = new WeakReference<>(AgentWebUtils.getAgentWebUIControllerByWebView(builder.mWebView));
        this.mIsInterceptUnkownUrl = builder.mIsInterceptUnkownScheme;
        if (builder.mUrlHandleWays <= 0) {
            this.mUrlHandleWays = 250;
        } else {
            this.mUrlHandleWays = builder.mUrlHandleWays;
        }
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    private boolean deepLink(String str) {
        ResolveInfo resolveInfoLookupResolveInfo;
        int i2 = this.mUrlHandleWays;
        if (i2 != 250) {
            if (i2 != 1001) {
                return false;
            }
            lookup(str);
            return true;
        }
        Activity activity = this.mWeakReference.get();
        if (activity == null || (resolveInfoLookupResolveInfo = lookupResolveInfo(str)) == null) {
            return false;
        }
        ActivityInfo activityInfo = resolveInfoLookupResolveInfo.activityInfo;
        LogUtils.e(TAG, "resolve package:" + resolveInfoLookupResolveInfo.activityInfo.packageName + " app package:" + activity.getPackageName());
        if (activityInfo != null && !TextUtils.isEmpty(activityInfo.packageName) && activityInfo.packageName.equals(activity.getPackageName())) {
            return lookup(str);
        }
        if (this.mAgentWebUIController.get() != null) {
            AbsAgentWebUIController absAgentWebUIController = this.mAgentWebUIController.get();
            WebView webView = this.mWebView;
            absAgentWebUIController.onOpenPagePrompt(webView, webView.getUrl(), getCallback(str));
        }
        return true;
    }

    private Handler.Callback getCallback(final String str) {
        Handler.Callback callback = this.mCallback;
        if (callback != null) {
            return callback;
        }
        Handler.Callback callback2 = new Handler.Callback() { // from class: com.just.agentweb.DefaultWebClient.2
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.what != 1) {
                    return true;
                }
                DefaultWebClient.this.lookup(str);
                return true;
            }
        };
        this.mCallback = callback2;
        return callback2;
    }

    private boolean handleCommonLink(String str) {
        if (!str.startsWith(com.tencent.smtt.sdk.WebView.SCHEME_TEL) && !str.startsWith(SCHEME_SMS) && !str.startsWith("mailto:") && !str.startsWith(com.tencent.smtt.sdk.WebView.SCHEME_GEO)) {
            return false;
        }
        try {
            Activity activity = this.mWeakReference.get();
            if (activity == null) {
                return false;
            }
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            activity.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e2) {
            if (!AgentWebConfig.DEBUG) {
                return true;
            }
            e2.printStackTrace();
            return true;
        }
    }

    private void handleIntentUrl(String str) {
        try {
            if (!TextUtils.isEmpty(str) && str.startsWith(INTENT_SCHEME)) {
                lookup(str);
            }
        } catch (Throwable th) {
            if (LogUtils.isDebug()) {
                th.printStackTrace();
            }
        }
    }

    private boolean isAlipay(final WebView webView, String str) {
        try {
            Activity activity = this.mWeakReference.get();
            if (activity == null) {
                return false;
            }
            if (this.mPayTask == null) {
                this.mPayTask = Class.forName("com.alipay.sdk.app.PayTask").getConstructor(Activity.class).newInstance(activity);
            }
            boolean zPayInterceptorWithUrl = ((PayTask) this.mPayTask).payInterceptorWithUrl(str, true, new H5PayCallback() { // from class: com.just.agentweb.DefaultWebClient.1
                @Override // com.alipay.sdk.app.H5PayCallback
                public void onPayResult(H5PayResultModel h5PayResultModel) {
                    final String returnUrl = h5PayResultModel.getReturnUrl();
                    if (TextUtils.isEmpty(returnUrl)) {
                        return;
                    }
                    AgentWebUtils.runInUiThread(new Runnable() { // from class: com.just.agentweb.DefaultWebClient.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            webView.loadUrl(returnUrl);
                        }
                    });
                }
            });
            if (zPayInterceptorWithUrl) {
                LogUtils.i(TAG, "alipay-isIntercepted:" + zPayInterceptorWithUrl + "  url:" + str);
            }
            return zPayInterceptorWithUrl;
        } catch (Throwable unused) {
            boolean z2 = AgentWebConfig.DEBUG;
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean lookup(String str) {
        try {
            Activity activity = this.mWeakReference.get();
            if (activity == null) {
                return true;
            }
            PackageManager packageManager = activity.getPackageManager();
            Intent uri = Intent.parseUri(str, 1);
            if (packageManager.resolveActivity(uri, 65536) == null) {
                return false;
            }
            activity.startActivity(uri);
            return true;
        } catch (Throwable th) {
            if (!LogUtils.isDebug()) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    private ResolveInfo lookupResolveInfo(String str) {
        try {
            Activity activity = this.mWeakReference.get();
            if (activity == null) {
                return null;
            }
            return activity.getPackageManager().resolveActivity(Intent.parseUri(str, 1), 65536);
        } catch (Throwable th) {
            if (LogUtils.isDebug()) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0033, code lost:
    
        if (r1 != null) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void onMainFrameError(android.webkit.WebView r10, int r11, java.lang.String r12, java.lang.String r13) {
        /*
            r9 = this;
            java.util.Set<java.lang.String> r0 = r9.mErrorUrlsSet
            r0.add(r13)
            android.webkit.WebViewClient r0 = r9.mWebViewClient
            if (r0 == 0) goto L5c
            boolean r1 = r9.webClientHelper
            if (r1 == 0) goto L5c
            java.lang.reflect.Method r1 = r9.onMainFrameErrorMethod
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            r7 = 5
            if (r1 != 0) goto L35
            java.lang.Class[] r1 = new java.lang.Class[r7]
            java.lang.Class<com.just.agentweb.AbsAgentWebUIController> r8 = com.just.agentweb.AbsAgentWebUIController.class
            r1[r6] = r8
            java.lang.Class<android.webkit.WebView> r8 = android.webkit.WebView.class
            r1[r5] = r8
            java.lang.Class r8 = java.lang.Integer.TYPE
            r1[r4] = r8
            java.lang.Class<java.lang.String> r8 = java.lang.String.class
            r1[r3] = r8
            r1[r2] = r8
            java.lang.String r8 = "onMainFrameError"
            java.lang.reflect.Method r1 = com.just.agentweb.AgentWebUtils.isExistMethod(r0, r8, r1)
            r9.onMainFrameErrorMethod = r1
            if (r1 == 0) goto L5c
        L35:
            android.webkit.WebViewClient r0 = r9.mWebViewClient     // Catch: java.lang.Throwable -> L51
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch: java.lang.Throwable -> L51
            java.lang.ref.WeakReference<com.just.agentweb.AbsAgentWebUIController> r8 = r9.mAgentWebUIController     // Catch: java.lang.Throwable -> L51
            java.lang.Object r8 = r8.get()     // Catch: java.lang.Throwable -> L51
            r7[r6] = r8     // Catch: java.lang.Throwable -> L51
            r7[r5] = r10     // Catch: java.lang.Throwable -> L51
            java.lang.Integer r10 = java.lang.Integer.valueOf(r11)     // Catch: java.lang.Throwable -> L51
            r7[r4] = r10     // Catch: java.lang.Throwable -> L51
            r7[r3] = r12     // Catch: java.lang.Throwable -> L51
            r7[r2] = r13     // Catch: java.lang.Throwable -> L51
            r1.invoke(r0, r7)     // Catch: java.lang.Throwable -> L51
            goto L5b
        L51:
            r10 = move-exception
            boolean r11 = com.just.agentweb.LogUtils.isDebug()
            if (r11 == 0) goto L5b
            r10.printStackTrace()
        L5b:
            return
        L5c:
            java.lang.ref.WeakReference<com.just.agentweb.AbsAgentWebUIController> r0 = r9.mAgentWebUIController
            java.lang.Object r0 = r0.get()
            if (r0 == 0) goto L6f
            java.lang.ref.WeakReference<com.just.agentweb.AbsAgentWebUIController> r0 = r9.mAgentWebUIController
            java.lang.Object r0 = r0.get()
            com.just.agentweb.AbsAgentWebUIController r0 = (com.just.agentweb.AbsAgentWebUIController) r0
            r0.onMainFrameError(r10, r11, r12, r13)
        L6f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.just.agentweb.DefaultWebClient.onMainFrameError(android.webkit.WebView, int, java.lang.String, java.lang.String):void");
    }

    private int queryActiviesNumber(String str) throws URISyntaxException {
        try {
            if (this.mWeakReference.get() == null) {
                return 0;
            }
            List<ResolveInfo> listQueryIntentActivities = this.mWeakReference.get().getPackageManager().queryIntentActivities(Intent.parseUri(str, 1), 65536);
            if (listQueryIntentActivities == null) {
                return 0;
            }
            return listQueryIntentActivities.size();
        } catch (URISyntaxException e2) {
            if (LogUtils.isDebug()) {
                e2.printStackTrace();
            }
            return 0;
        }
    }

    private void startActivity(String str) {
        try {
            if (this.mWeakReference.get() == null) {
                return;
            }
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            this.mWeakReference.get().startActivity(intent);
        } catch (Exception e2) {
            if (LogUtils.isDebug()) {
                e2.printStackTrace();
            }
        }
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public void doUpdateVisitedHistory(WebView webView, String str, boolean z2) {
        if (!this.mWaittingFinishSet.contains(str)) {
            this.mWaittingFinishSet.add(str);
        }
        super.doUpdateVisitedHistory(webView, str, z2);
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        if (this.mErrorUrlsSet.contains(str) || !this.mWaittingFinishSet.contains(str)) {
            webView.setVisibility(0);
        } else if (this.mAgentWebUIController.get() != null) {
            this.mAgentWebUIController.get().onShowMainFrame();
        }
        if (this.mWaittingFinishSet.contains(str)) {
            this.mWaittingFinishSet.remove(str);
        }
        if (!this.mErrorUrlsSet.isEmpty()) {
            this.mErrorUrlsSet.clear();
        }
        super.onPageFinished(webView, str);
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        if (!this.mWaittingFinishSet.contains(str)) {
            this.mWaittingFinishSet.add(str);
        }
        super.onPageStarted(webView, str, bitmap);
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public void onReceivedError(WebView webView, int i2, String str, String str2) {
        LogUtils.i(TAG, "onReceivedError：" + str + "  CODE:" + i2);
        if ((str2 != null || i2 == -12) && i2 != -1) {
            if (i2 == -2 || str2 == null || str2.equals(webView.getUrl()) || str2.equals(webView.getOriginalUrl())) {
                onMainFrameError(webView, i2, str, str2);
            }
        }
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    @RequiresApi(api = 21)
    @TargetApi(23)
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        String string = webResourceRequest.getUrl().toString();
        int errorCode = webResourceError.getErrorCode();
        if (webResourceRequest.isForMainFrame()) {
            if ((string != null || errorCode == -12) && errorCode != -1) {
                LogUtils.i(TAG, "onReceivedError:" + ((Object) webResourceError.getDescription()) + " code:" + webResourceError.getErrorCode() + " failingUrl:" + string + " getUrl:" + webView.getUrl() + " getOriginalUrl:" + webView.getOriginalUrl());
                if (errorCode == -2 || string == null || string.equals(webView.getUrl()) || string.equals(webView.getOriginalUrl())) {
                    onMainFrameError(webView, webResourceError.getErrorCode(), webResourceError.getDescription().toString(), webResourceRequest.getUrl().toString());
                }
            }
        }
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
        super.onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        if (this.mAgentWebUIController.get() != null) {
            this.mAgentWebUIController.get().onShowSslCertificateErrorDialog(webView, sslErrorHandler, sslError);
        }
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public void onScaleChanged(WebView webView, float f2, float f3) {
        LogUtils.i(TAG, "onScaleChanged:" + f2 + "   n:" + f3);
        if (f3 - f2 > 7.0f) {
            webView.setInitialScale((int) ((f2 / f3) * 100.0f));
        }
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        return super.shouldInterceptRequest(webView, webResourceRequest);
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        return super.shouldInterceptRequest(webView, str);
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        return super.shouldOverrideKeyEvent(webView, keyEvent);
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    @RequiresApi(api = 21)
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        String string = webResourceRequest.getUrl().toString();
        if (string.startsWith(HTTP_SCHEME) || string.startsWith(HTTPS_SCHEME)) {
            return this.webClientHelper && HAS_ALIPAY_LIB && isAlipay(webView, string);
        }
        if (!this.webClientHelper) {
            return super.shouldOverrideUrlLoading(webView, webResourceRequest);
        }
        if (handleCommonLink(string)) {
            return true;
        }
        if (string.startsWith(INTENT_SCHEME)) {
            handleIntentUrl(string);
            LogUtils.i(TAG, "intent url ");
            return true;
        }
        if (string.startsWith(WEBCHAT_PAY_SCHEME)) {
            LogUtils.i(TAG, "lookup wechat to pay ~~");
            startActivity(string);
            return true;
        }
        if (string.startsWith(ALIPAYS_SCHEME) && lookup(string)) {
            LogUtils.i(TAG, "alipays url lookup alipay ~~ ");
            return true;
        }
        if (queryActiviesNumber(string) > 0 && deepLink(string)) {
            LogUtils.i(TAG, "intercept url:" + string);
            return true;
        }
        if (!this.mIsInterceptUnkownUrl) {
            return super.shouldOverrideUrlLoading(webView, webResourceRequest);
        }
        LogUtils.i(TAG, "intercept UnkownUrl :" + webResourceRequest.getUrl());
        return true;
    }

    @Override // com.just.agentweb.WebViewClientDelegate, android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        String str2;
        String str3;
        if (str.startsWith(HTTP_SCHEME) || str.startsWith(HTTPS_SCHEME)) {
            return this.webClientHelper && HAS_ALIPAY_LIB && isAlipay(webView, str);
        }
        if (!this.webClientHelper) {
            return false;
        }
        if (handleCommonLink(str)) {
            return true;
        }
        if (str.startsWith(INTENT_SCHEME)) {
            handleIntentUrl(str);
            return true;
        }
        if (str.startsWith(WEBCHAT_PAY_SCHEME)) {
            startActivity(str);
            return true;
        }
        if (str.startsWith(ALIPAYS_SCHEME) && lookup(str)) {
            return true;
        }
        if (queryActiviesNumber(str) > 0 && deepLink(str)) {
            str2 = TAG;
            str3 = "intercept OtherAppScheme";
        } else {
            if (!this.mIsInterceptUnkownUrl) {
                return super.shouldOverrideUrlLoading(webView, str);
            }
            str2 = TAG;
            str3 = "intercept InterceptUnkownScheme : " + str;
        }
        LogUtils.i(str2, str3);
        return true;
    }
}
