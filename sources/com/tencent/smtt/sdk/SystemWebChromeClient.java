package com.tencent.smtt.sdk;

import android.R;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebStorage;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebView.WebViewTransport;

/* loaded from: classes6.dex */
class SystemWebChromeClient extends android.webkit.WebChromeClient {

    /* renamed from: a, reason: collision with root package name */
    protected WebChromeClient f20861a;

    /* renamed from: b, reason: collision with root package name */
    private WebView f20862b;

    public static class a implements ConsoleMessage {

        /* renamed from: a, reason: collision with root package name */
        private ConsoleMessage.MessageLevel f20878a;

        /* renamed from: b, reason: collision with root package name */
        private String f20879b;

        /* renamed from: c, reason: collision with root package name */
        private String f20880c;

        /* renamed from: d, reason: collision with root package name */
        private int f20881d;

        public a(android.webkit.ConsoleMessage consoleMessage) {
            this.f20878a = ConsoleMessage.MessageLevel.valueOf(consoleMessage.messageLevel().name());
            this.f20879b = consoleMessage.message();
            this.f20880c = consoleMessage.sourceId();
            this.f20881d = consoleMessage.lineNumber();
        }

        public a(String str, String str2, int i2) {
            this.f20878a = ConsoleMessage.MessageLevel.LOG;
            this.f20879b = str;
            this.f20880c = str2;
            this.f20881d = i2;
        }

        @Override // com.tencent.smtt.export.external.interfaces.ConsoleMessage
        public int lineNumber() {
            return this.f20881d;
        }

        @Override // com.tencent.smtt.export.external.interfaces.ConsoleMessage
        public String message() {
            return this.f20879b;
        }

        @Override // com.tencent.smtt.export.external.interfaces.ConsoleMessage
        public ConsoleMessage.MessageLevel messageLevel() {
            return this.f20878a;
        }

        @Override // com.tencent.smtt.export.external.interfaces.ConsoleMessage
        public String sourceId() {
            return this.f20880c;
        }
    }

    public class b implements IX5WebChromeClient.CustomViewCallback {

        /* renamed from: a, reason: collision with root package name */
        WebChromeClient.CustomViewCallback f20882a;

        public b(WebChromeClient.CustomViewCallback customViewCallback) {
            this.f20882a = customViewCallback;
        }

        @Override // com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback
        public void onCustomViewHidden() {
            this.f20882a.onCustomViewHidden();
        }
    }

    public class c implements GeolocationPermissionsCallback {

        /* renamed from: a, reason: collision with root package name */
        GeolocationPermissions.Callback f20884a;

        public c(GeolocationPermissions.Callback callback) {
            this.f20884a = callback;
        }

        @Override // com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback
        public void invoke(String str, boolean z2, boolean z3) {
            this.f20884a.invoke(str, z2, z3);
        }
    }

    public class d implements JsPromptResult {

        /* renamed from: a, reason: collision with root package name */
        android.webkit.JsPromptResult f20886a;

        public d(android.webkit.JsPromptResult jsPromptResult) {
            this.f20886a = jsPromptResult;
        }

        @Override // com.tencent.smtt.export.external.interfaces.JsResult
        public void cancel() {
            this.f20886a.cancel();
        }

        @Override // com.tencent.smtt.export.external.interfaces.JsResult
        public void confirm() {
            this.f20886a.confirm();
        }

        @Override // com.tencent.smtt.export.external.interfaces.JsPromptResult
        public void confirm(String str) {
            this.f20886a.confirm(str);
        }
    }

    public class e implements JsResult {

        /* renamed from: a, reason: collision with root package name */
        android.webkit.JsResult f20888a;

        public e(android.webkit.JsResult jsResult) {
            this.f20888a = jsResult;
        }

        @Override // com.tencent.smtt.export.external.interfaces.JsResult
        public void cancel() {
            this.f20888a.cancel();
        }

        @Override // com.tencent.smtt.export.external.interfaces.JsResult
        public void confirm() {
            this.f20888a.confirm();
        }
    }

    public class f implements WebStorage.QuotaUpdater {

        /* renamed from: a, reason: collision with root package name */
        WebStorage.QuotaUpdater f20890a;

        public f(WebStorage.QuotaUpdater quotaUpdater) {
            this.f20890a = quotaUpdater;
        }

        @Override // com.tencent.smtt.sdk.WebStorage.QuotaUpdater
        public void updateQuota(long j2) {
            this.f20890a.updateQuota(j2);
        }
    }

    public SystemWebChromeClient(WebView webView, WebChromeClient webChromeClient) {
        this.f20862b = webView;
        this.f20861a = webChromeClient;
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(7)
    public Bitmap getDefaultVideoPoster() {
        Bitmap defaultVideoPoster = this.f20861a.getDefaultVideoPoster();
        if (defaultVideoPoster != null) {
            return defaultVideoPoster;
        }
        try {
            return Build.VERSION.SDK_INT >= 24 ? BitmapFactory.decodeResource(this.f20862b.getResources(), R.drawable.ic_media_play) : defaultVideoPoster;
        } catch (Exception unused) {
            return defaultVideoPoster;
        }
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(7)
    public View getVideoLoadingProgressView() {
        return this.f20861a.getVideoLoadingProgressView();
    }

    @Override // android.webkit.WebChromeClient
    public void getVisitedHistory(final android.webkit.ValueCallback<String[]> valueCallback) {
        this.f20861a.getVisitedHistory(new ValueCallback<String[]>() { // from class: com.tencent.smtt.sdk.SystemWebChromeClient.1
            @Override // com.tencent.smtt.sdk.ValueCallback, android.webkit.ValueCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onReceiveValue(String[] strArr) {
                valueCallback.onReceiveValue(strArr);
            }
        });
    }

    @Override // android.webkit.WebChromeClient
    public void onCloseWindow(android.webkit.WebView webView) {
        this.f20862b.a(webView);
        this.f20861a.onCloseWindow(this.f20862b);
    }

    @Override // android.webkit.WebChromeClient
    public void onConsoleMessage(String str, int i2, String str2) {
        this.f20861a.onConsoleMessage(new a(str, str2, i2));
    }

    @Override // android.webkit.WebChromeClient
    public boolean onConsoleMessage(android.webkit.ConsoleMessage consoleMessage) {
        return this.f20861a.onConsoleMessage(new a(consoleMessage));
    }

    @Override // android.webkit.WebChromeClient
    public boolean onCreateWindow(android.webkit.WebView webView, boolean z2, boolean z3, final Message message) {
        WebView webView2 = this.f20862b;
        webView2.getClass();
        final WebView.WebViewTransport webViewTransport = webView2.new WebViewTransport();
        Message messageObtain = Message.obtain(message.getTarget(), new Runnable() { // from class: com.tencent.smtt.sdk.SystemWebChromeClient.2
            @Override // java.lang.Runnable
            public void run() {
                WebView webView3 = webViewTransport.getWebView();
                if (webView3 != null) {
                    ((WebView.WebViewTransport) message.obj).setWebView(webView3.a());
                }
                message.sendToTarget();
            }
        });
        messageObtain.obj = webViewTransport;
        return this.f20861a.onCreateWindow(this.f20862b, z2, z3, messageObtain);
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(5)
    @Deprecated
    public void onExceededDatabaseQuota(String str, String str2, long j2, long j3, long j4, WebStorage.QuotaUpdater quotaUpdater) {
        this.f20861a.onExceededDatabaseQuota(str, str2, j2, j3, j4, new f(quotaUpdater));
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(5)
    public void onGeolocationPermissionsHidePrompt() {
        this.f20861a.onGeolocationPermissionsHidePrompt();
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(5)
    public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
        this.f20861a.onGeolocationPermissionsShowPrompt(str, new c(callback));
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsAlert(android.webkit.WebView webView, String str, String str2, android.webkit.JsResult jsResult) {
        this.f20862b.a(webView);
        return this.f20861a.onJsAlert(this.f20862b, str, str2, new e(jsResult));
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsBeforeUnload(android.webkit.WebView webView, String str, String str2, android.webkit.JsResult jsResult) {
        this.f20862b.a(webView);
        return this.f20861a.onJsBeforeUnload(this.f20862b, str, str2, new e(jsResult));
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsConfirm(android.webkit.WebView webView, String str, String str2, android.webkit.JsResult jsResult) {
        this.f20862b.a(webView);
        return this.f20861a.onJsConfirm(this.f20862b, str, str2, new e(jsResult));
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsPrompt(android.webkit.WebView webView, String str, String str2, String str3, android.webkit.JsPromptResult jsPromptResult) {
        this.f20862b.a(webView);
        return this.f20861a.onJsPrompt(this.f20862b, str, str2, str3, new d(jsPromptResult));
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(7)
    public boolean onJsTimeout() {
        return this.f20861a.onJsTimeout();
    }

    @Override // android.webkit.WebChromeClient
    public void onPermissionRequest(final PermissionRequest permissionRequest) {
        this.f20861a.onPermissionRequest(new com.tencent.smtt.export.external.interfaces.PermissionRequest() { // from class: com.tencent.smtt.sdk.SystemWebChromeClient.6
            @Override // com.tencent.smtt.export.external.interfaces.PermissionRequest
            public void deny() {
                permissionRequest.deny();
            }

            @Override // com.tencent.smtt.export.external.interfaces.PermissionRequest
            public Uri getOrigin() {
                return permissionRequest.getOrigin();
            }

            @Override // com.tencent.smtt.export.external.interfaces.PermissionRequest
            public String[] getResources() {
                return permissionRequest.getResources();
            }

            @Override // com.tencent.smtt.export.external.interfaces.PermissionRequest
            public void grant(String[] strArr) {
                permissionRequest.grant(strArr);
            }
        });
    }

    @Override // android.webkit.WebChromeClient
    public void onPermissionRequestCanceled(final PermissionRequest permissionRequest) {
        this.f20861a.onPermissionRequestCanceled(new com.tencent.smtt.export.external.interfaces.PermissionRequest() { // from class: com.tencent.smtt.sdk.SystemWebChromeClient.7
            @Override // com.tencent.smtt.export.external.interfaces.PermissionRequest
            public void deny() {
                permissionRequest.deny();
            }

            @Override // com.tencent.smtt.export.external.interfaces.PermissionRequest
            public Uri getOrigin() {
                return permissionRequest.getOrigin();
            }

            @Override // com.tencent.smtt.export.external.interfaces.PermissionRequest
            public String[] getResources() {
                return permissionRequest.getResources();
            }

            @Override // com.tencent.smtt.export.external.interfaces.PermissionRequest
            public void grant(String[] strArr) {
                permissionRequest.grant(strArr);
            }
        });
    }

    @Override // android.webkit.WebChromeClient
    public void onProgressChanged(android.webkit.WebView webView, int i2) {
        this.f20862b.a(webView);
        this.f20861a.onProgressChanged(this.f20862b, i2);
    }

    @TargetApi(7)
    @Deprecated
    public void onReachedMaxAppCacheSize(long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
        this.f20861a.onReachedMaxAppCacheSize(j2, j3, new f(quotaUpdater));
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedIcon(android.webkit.WebView webView, Bitmap bitmap) {
        this.f20862b.a(webView);
        this.f20861a.onReceivedIcon(this.f20862b, bitmap);
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTitle(android.webkit.WebView webView, String str) {
        this.f20862b.a(webView);
        this.f20861a.onReceivedTitle(this.f20862b, str);
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(7)
    public void onReceivedTouchIconUrl(android.webkit.WebView webView, String str, boolean z2) {
        this.f20862b.a(webView);
        this.f20861a.onReceivedTouchIconUrl(this.f20862b, str, z2);
    }

    @Override // android.webkit.WebChromeClient
    public void onRequestFocus(android.webkit.WebView webView) {
        this.f20862b.a(webView);
        this.f20861a.onRequestFocus(this.f20862b);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onShowFileChooser(android.webkit.WebView webView, final android.webkit.ValueCallback<Uri[]> valueCallback, final WebChromeClient.FileChooserParams fileChooserParams) {
        ValueCallback<Uri[]> valueCallback2 = new ValueCallback<Uri[]>() { // from class: com.tencent.smtt.sdk.SystemWebChromeClient.4
            @Override // com.tencent.smtt.sdk.ValueCallback, android.webkit.ValueCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onReceiveValue(Uri[] uriArr) {
                valueCallback.onReceiveValue(uriArr);
            }
        };
        WebChromeClient.FileChooserParams fileChooserParams2 = new WebChromeClient.FileChooserParams() { // from class: com.tencent.smtt.sdk.SystemWebChromeClient.5
            @Override // com.tencent.smtt.sdk.WebChromeClient.FileChooserParams
            public Intent createIntent() {
                return fileChooserParams.createIntent();
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient.FileChooserParams
            public String[] getAcceptTypes() {
                return fileChooserParams.getAcceptTypes();
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient.FileChooserParams
            public String getFilenameHint() {
                return fileChooserParams.getFilenameHint();
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient.FileChooserParams
            public int getMode() {
                return fileChooserParams.getMode();
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient.FileChooserParams
            public CharSequence getTitle() {
                return fileChooserParams.getTitle();
            }

            @Override // com.tencent.smtt.sdk.WebChromeClient.FileChooserParams
            public boolean isCaptureEnabled() {
                return fileChooserParams.isCaptureEnabled();
            }
        };
        this.f20862b.a(webView);
        return this.f20861a.onShowFileChooser(this.f20862b, valueCallback2, fileChooserParams2);
    }

    public void openFileChooser(android.webkit.ValueCallback<Uri> valueCallback) {
        openFileChooser(valueCallback, null, null);
    }

    public void openFileChooser(android.webkit.ValueCallback<Uri> valueCallback, String str) {
        openFileChooser(valueCallback, str, null);
    }

    public void openFileChooser(final android.webkit.ValueCallback<Uri> valueCallback, String str, String str2) {
        this.f20861a.openFileChooser(new ValueCallback<Uri>() { // from class: com.tencent.smtt.sdk.SystemWebChromeClient.3
            @Override // com.tencent.smtt.sdk.ValueCallback, android.webkit.ValueCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onReceiveValue(Uri uri) {
                valueCallback.onReceiveValue(uri);
            }
        }, str, str2);
    }

    public void setupAutoFill(Message message) {
    }
}
