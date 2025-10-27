package com.tencent.smtt.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Build;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceError;
import com.tencent.smtt.export.external.interfaces.ClientCertRequest;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;
import java.io.InputStream;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Map;

@SuppressLint({"NewApi", "Override"})
/* loaded from: classes6.dex */
class SystemWebViewClient extends android.webkit.WebViewClient {

    /* renamed from: c, reason: collision with root package name */
    private static String f20892c;

    /* renamed from: a, reason: collision with root package name */
    private WebViewClient f20893a;

    /* renamed from: b, reason: collision with root package name */
    private WebView f20894b;

    public static class a extends ClientCertRequest {

        /* renamed from: a, reason: collision with root package name */
        private android.webkit.ClientCertRequest f20900a;

        public a(android.webkit.ClientCertRequest clientCertRequest) {
            this.f20900a = clientCertRequest;
        }

        @Override // com.tencent.smtt.export.external.interfaces.ClientCertRequest
        public void cancel() {
            this.f20900a.cancel();
        }

        @Override // com.tencent.smtt.export.external.interfaces.ClientCertRequest
        public String getHost() {
            return this.f20900a.getHost();
        }

        @Override // com.tencent.smtt.export.external.interfaces.ClientCertRequest
        public String[] getKeyTypes() {
            return this.f20900a.getKeyTypes();
        }

        @Override // com.tencent.smtt.export.external.interfaces.ClientCertRequest
        public int getPort() {
            return this.f20900a.getPort();
        }

        @Override // com.tencent.smtt.export.external.interfaces.ClientCertRequest
        public Principal[] getPrincipals() {
            return this.f20900a.getPrincipals();
        }

        @Override // com.tencent.smtt.export.external.interfaces.ClientCertRequest
        public void ignore() {
            this.f20900a.ignore();
        }

        @Override // com.tencent.smtt.export.external.interfaces.ClientCertRequest
        public void proceed(PrivateKey privateKey, X509Certificate[] x509CertificateArr) {
            this.f20900a.proceed(privateKey, x509CertificateArr);
        }
    }

    public static class b implements HttpAuthHandler {

        /* renamed from: a, reason: collision with root package name */
        private android.webkit.HttpAuthHandler f20901a;

        public b(android.webkit.HttpAuthHandler httpAuthHandler) {
            this.f20901a = httpAuthHandler;
        }

        @Override // com.tencent.smtt.export.external.interfaces.HttpAuthHandler
        public void cancel() {
            this.f20901a.cancel();
        }

        @Override // com.tencent.smtt.export.external.interfaces.HttpAuthHandler
        public void proceed(String str, String str2) {
            this.f20901a.proceed(str, str2);
        }

        @Override // com.tencent.smtt.export.external.interfaces.HttpAuthHandler
        public boolean useHttpAuthUsernamePassword() {
            return this.f20901a.useHttpAuthUsernamePassword();
        }
    }

    public static class c implements SslErrorHandler {

        /* renamed from: a, reason: collision with root package name */
        android.webkit.SslErrorHandler f20902a;

        public c(android.webkit.SslErrorHandler sslErrorHandler) {
            this.f20902a = sslErrorHandler;
        }

        @Override // com.tencent.smtt.export.external.interfaces.SslErrorHandler
        public void cancel() {
            this.f20902a.cancel();
        }

        @Override // com.tencent.smtt.export.external.interfaces.SslErrorHandler
        public void proceed() {
            this.f20902a.proceed();
        }
    }

    public static class d implements SslError {

        /* renamed from: a, reason: collision with root package name */
        android.net.http.SslError f20903a;

        public d(android.net.http.SslError sslError) {
            this.f20903a = sslError;
        }

        @Override // com.tencent.smtt.export.external.interfaces.SslError
        public boolean addError(int i2) {
            return this.f20903a.addError(i2);
        }

        @Override // com.tencent.smtt.export.external.interfaces.SslError
        public SslCertificate getCertificate() {
            return this.f20903a.getCertificate();
        }

        @Override // com.tencent.smtt.export.external.interfaces.SslError
        public int getPrimaryError() {
            return this.f20903a.getPrimaryError();
        }

        @Override // com.tencent.smtt.export.external.interfaces.SslError
        public String getUrl() {
            return this.f20903a.getUrl();
        }

        @Override // com.tencent.smtt.export.external.interfaces.SslError
        public boolean hasError(int i2) {
            return this.f20903a.hasError(i2);
        }
    }

    public static class e implements WebResourceRequest {

        /* renamed from: a, reason: collision with root package name */
        private String f20904a;

        /* renamed from: b, reason: collision with root package name */
        private boolean f20905b;

        /* renamed from: c, reason: collision with root package name */
        private boolean f20906c;

        /* renamed from: d, reason: collision with root package name */
        private boolean f20907d;

        /* renamed from: e, reason: collision with root package name */
        private String f20908e;

        /* renamed from: f, reason: collision with root package name */
        private Map<String, String> f20909f;

        public e(String str, boolean z2, boolean z3, boolean z4, String str2, Map<String, String> map) {
            this.f20904a = str;
            this.f20905b = z2;
            this.f20906c = z3;
            this.f20907d = z4;
            this.f20908e = str2;
            this.f20909f = map;
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public String getMethod() {
            return this.f20908e;
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public Map<String, String> getRequestHeaders() {
            return this.f20909f;
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public Uri getUrl() {
            return Uri.parse(this.f20904a);
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public boolean hasGesture() {
            return this.f20907d;
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public boolean isForMainFrame() {
            return this.f20905b;
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public boolean isRedirect() {
            return this.f20906c;
        }
    }

    public static class f implements WebResourceRequest {

        /* renamed from: a, reason: collision with root package name */
        android.webkit.WebResourceRequest f20910a;

        public f(android.webkit.WebResourceRequest webResourceRequest) {
            this.f20910a = webResourceRequest;
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public String getMethod() {
            return this.f20910a.getMethod();
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public Map<String, String> getRequestHeaders() {
            return this.f20910a.getRequestHeaders();
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public Uri getUrl() {
            return this.f20910a.getUrl();
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public boolean hasGesture() {
            return this.f20910a.hasGesture();
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public boolean isForMainFrame() {
            return this.f20910a.isForMainFrame();
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceRequest
        public boolean isRedirect() {
            if (Build.VERSION.SDK_INT >= 24) {
                Object objA = com.tencent.smtt.utils.j.a(this.f20910a, "isRedirect");
                if (objA instanceof Boolean) {
                    return ((Boolean) objA).booleanValue();
                }
            }
            return false;
        }
    }

    public static class g extends WebResourceResponse {

        /* renamed from: a, reason: collision with root package name */
        android.webkit.WebResourceResponse f20911a;

        public g(android.webkit.WebResourceResponse webResourceResponse) {
            this.f20911a = webResourceResponse;
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceResponse
        public InputStream getData() {
            return this.f20911a.getData();
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceResponse
        public String getEncoding() {
            return this.f20911a.getEncoding();
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceResponse
        public String getMimeType() {
            return this.f20911a.getMimeType();
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceResponse
        public String getReasonPhrase() {
            return this.f20911a.getReasonPhrase();
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceResponse
        public Map<String, String> getResponseHeaders() {
            return this.f20911a.getResponseHeaders();
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceResponse
        public int getStatusCode() {
            return this.f20911a.getStatusCode();
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceResponse
        public void setData(InputStream inputStream) {
            this.f20911a.setData(inputStream);
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceResponse
        public void setEncoding(String str) {
            this.f20911a.setEncoding(str);
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceResponse
        public void setMimeType(String str) {
            this.f20911a.setMimeType(str);
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceResponse
        public void setResponseHeaders(Map<String, String> map) {
            this.f20911a.setResponseHeaders(map);
        }

        @Override // com.tencent.smtt.export.external.interfaces.WebResourceResponse
        public void setStatusCodeAndReasonPhrase(int i2, String str) {
            this.f20911a.setStatusCodeAndReasonPhrase(i2, str);
        }
    }

    public SystemWebViewClient(WebView webView, WebViewClient webViewClient) {
        this.f20894b = webView;
        this.f20893a = webViewClient;
    }

    @Override // android.webkit.WebViewClient
    public void doUpdateVisitedHistory(android.webkit.WebView webView, String str, boolean z2) {
        this.f20894b.a(webView);
        this.f20893a.doUpdateVisitedHistory(this.f20894b, str, z2);
    }

    @Override // android.webkit.WebViewClient
    public void onFormResubmission(android.webkit.WebView webView, Message message, Message message2) {
        this.f20894b.a(webView);
        this.f20893a.onFormResubmission(this.f20894b, message, message2);
    }

    @Override // android.webkit.WebViewClient
    public void onLoadResource(android.webkit.WebView webView, String str) {
        this.f20894b.a(webView);
        this.f20893a.onLoadResource(this.f20894b, str);
    }

    @Override // android.webkit.WebViewClient
    public void onPageCommitVisible(android.webkit.WebView webView, String str) {
        this.f20894b.a(webView);
        this.f20893a.onPageCommitVisible(this.f20894b, str);
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(android.webkit.WebView webView, String str) throws Throwable {
        com.tencent.smtt.utils.p pVarA;
        TbsPrivacyAccess.rmPrivacyItemIfNeeded(webView.getContext().getApplicationContext());
        if (f20892c == null && (pVarA = com.tencent.smtt.utils.p.a()) != null) {
            pVarA.a(true);
            f20892c = Boolean.toString(true);
        }
        this.f20894b.a(webView);
        this.f20894b.f21067a++;
        this.f20893a.onPageFinished(this.f20894b, str);
        if ("com.qzone".equals(webView.getContext().getApplicationInfo().packageName)) {
            this.f20894b.a(webView.getContext());
        }
        TbsLog.app_extra("SystemWebViewClient", webView.getContext());
        WebView.c();
        if (!TbsShareManager.mHasQueryed && this.f20894b.getContext() != null && TbsShareManager.isThirdPartyApp(this.f20894b.getContext())) {
            TbsShareManager.mHasQueryed = true;
            new Thread(new Runnable() { // from class: com.tencent.smtt.sdk.SystemWebViewClient.1
                @Override // java.lang.Runnable
                public void run() {
                    TbsDownloader.needDownload(SystemWebViewClient.this.f20894b.getContext(), false);
                }
            }).start();
        }
        if (this.f20894b.getContext() == null || TbsLogReport.getInstance(this.f20894b.getContext()).getShouldUploadEventReport()) {
            return;
        }
        TbsLogReport.getInstance(this.f20894b.getContext()).setShouldUploadEventReport(true);
        TbsLogReport.getInstance(this.f20894b.getContext()).dailyReport();
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(android.webkit.WebView webView, String str, Bitmap bitmap) {
        this.f20894b.a(webView);
        this.f20893a.onPageStarted(this.f20894b, str, bitmap);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedClientCertRequest(android.webkit.WebView webView, android.webkit.ClientCertRequest clientCertRequest) {
        this.f20894b.a(webView);
        this.f20893a.onReceivedClientCertRequest(this.f20894b, new a(clientCertRequest));
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(android.webkit.WebView webView, int i2, String str, String str2) {
        this.f20894b.a(webView);
        this.f20893a.onReceivedError(this.f20894b, i2, str, str2);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(android.webkit.WebView webView, android.webkit.WebResourceRequest webResourceRequest, final WebResourceError webResourceError) {
        this.f20894b.a(webView);
        this.f20893a.onReceivedError(this.f20894b, webResourceRequest != null ? new f(webResourceRequest) : null, webResourceError != null ? new com.tencent.smtt.export.external.interfaces.WebResourceError() { // from class: com.tencent.smtt.sdk.SystemWebViewClient.2
            @Override // com.tencent.smtt.export.external.interfaces.WebResourceError
            public CharSequence getDescription() {
                return webResourceError.getDescription();
            }

            @Override // com.tencent.smtt.export.external.interfaces.WebResourceError
            public int getErrorCode() {
                return webResourceError.getErrorCode();
            }
        } : null);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedHttpAuthRequest(android.webkit.WebView webView, android.webkit.HttpAuthHandler httpAuthHandler, String str, String str2) {
        this.f20894b.a(webView);
        this.f20893a.onReceivedHttpAuthRequest(this.f20894b, new b(httpAuthHandler), str, str2);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedHttpError(android.webkit.WebView webView, android.webkit.WebResourceRequest webResourceRequest, android.webkit.WebResourceResponse webResourceResponse) {
        this.f20894b.a(webView);
        this.f20893a.onReceivedHttpError(this.f20894b, new f(webResourceRequest), new g(webResourceResponse));
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(12)
    public void onReceivedLoginRequest(android.webkit.WebView webView, String str, String str2, String str3) {
        this.f20894b.a(webView);
        this.f20893a.onReceivedLoginRequest(this.f20894b, str, str2, str3);
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(8)
    public void onReceivedSslError(android.webkit.WebView webView, android.webkit.SslErrorHandler sslErrorHandler, android.net.http.SslError sslError) {
        this.f20894b.a(webView);
        this.f20893a.onReceivedSslError(this.f20894b, new c(sslErrorHandler), new d(sslError));
    }

    @Override // android.webkit.WebViewClient
    public boolean onRenderProcessGone(android.webkit.WebView webView, final RenderProcessGoneDetail renderProcessGoneDetail) {
        this.f20894b.a(webView);
        return this.f20893a.onRenderProcessGone(this.f20894b, new WebViewClient.RenderProcessGoneDetail() { // from class: com.tencent.smtt.sdk.SystemWebViewClient.3
            @Override // com.tencent.smtt.sdk.WebViewClient.RenderProcessGoneDetail
            public boolean didCrash() {
                return renderProcessGoneDetail.didCrash();
            }
        });
    }

    @Override // android.webkit.WebViewClient
    public void onScaleChanged(android.webkit.WebView webView, float f2, float f3) {
        this.f20894b.a(webView);
        this.f20893a.onScaleChanged(this.f20894b, f2, f3);
    }

    @Override // android.webkit.WebViewClient
    public void onTooManyRedirects(android.webkit.WebView webView, Message message, Message message2) {
        this.f20894b.a(webView);
        this.f20893a.onTooManyRedirects(this.f20894b, message, message2);
    }

    @Override // android.webkit.WebViewClient
    public void onUnhandledKeyEvent(android.webkit.WebView webView, KeyEvent keyEvent) {
        this.f20894b.a(webView);
        this.f20893a.onUnhandledKeyEvent(this.f20894b, keyEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001b  */
    @Override // android.webkit.WebViewClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.webkit.WebResourceResponse shouldInterceptRequest(android.webkit.WebView r9, android.webkit.WebResourceRequest r10) {
        /*
            r8 = this;
            int r9 = android.os.Build.VERSION.SDK_INT
            r0 = 0
            if (r10 != 0) goto L6
            return r0
        L6:
            r1 = 24
            if (r9 < r1) goto L1b
            java.lang.String r9 = "isRedirect"
            java.lang.Object r9 = com.tencent.smtt.utils.j.a(r10, r9)
            boolean r1 = r9 instanceof java.lang.Boolean
            if (r1 == 0) goto L1b
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            goto L1c
        L1b:
            r9 = 0
        L1c:
            r4 = r9
            com.tencent.smtt.sdk.SystemWebViewClient$e r9 = new com.tencent.smtt.sdk.SystemWebViewClient$e
            android.net.Uri r1 = r10.getUrl()
            java.lang.String r2 = r1.toString()
            boolean r3 = r10.isForMainFrame()
            boolean r5 = r10.hasGesture()
            java.lang.String r6 = r10.getMethod()
            java.util.Map r7 = r10.getRequestHeaders()
            r1 = r9
            r1.<init>(r2, r3, r4, r5, r6, r7)
            com.tencent.smtt.sdk.WebViewClient r10 = r8.f20893a
            com.tencent.smtt.sdk.WebView r1 = r8.f20894b
            com.tencent.smtt.export.external.interfaces.WebResourceResponse r9 = r10.shouldInterceptRequest(r1, r9)
            if (r9 != 0) goto L46
            return r0
        L46:
            android.webkit.WebResourceResponse r10 = new android.webkit.WebResourceResponse
            java.lang.String r0 = r9.getMimeType()
            java.lang.String r1 = r9.getEncoding()
            java.io.InputStream r2 = r9.getData()
            r10.<init>(r0, r1, r2)
            java.util.Map r0 = r9.getResponseHeaders()
            r10.setResponseHeaders(r0)
            int r0 = r9.getStatusCode()
            java.lang.String r9 = r9.getReasonPhrase()
            int r1 = r10.getStatusCode()
            if (r0 != r1) goto L78
            if (r9 == 0) goto L7b
            java.lang.String r1 = r10.getReasonPhrase()
            boolean r1 = r9.equals(r1)
            if (r1 != 0) goto L7b
        L78:
            r10.setStatusCodeAndReasonPhrase(r0, r9)
        L7b:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.SystemWebViewClient.shouldInterceptRequest(android.webkit.WebView, android.webkit.WebResourceRequest):android.webkit.WebResourceResponse");
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(11)
    public android.webkit.WebResourceResponse shouldInterceptRequest(android.webkit.WebView webView, String str) {
        WebResourceResponse webResourceResponseShouldInterceptRequest = this.f20893a.shouldInterceptRequest(this.f20894b, str);
        if (webResourceResponseShouldInterceptRequest == null) {
            return null;
        }
        return new android.webkit.WebResourceResponse(webResourceResponseShouldInterceptRequest.getMimeType(), webResourceResponseShouldInterceptRequest.getEncoding(), webResourceResponseShouldInterceptRequest.getData());
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideKeyEvent(android.webkit.WebView webView, KeyEvent keyEvent) {
        this.f20894b.a(webView);
        return this.f20893a.shouldOverrideKeyEvent(this.f20894b, keyEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0039  */
    @Override // android.webkit.WebViewClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean shouldOverrideUrlLoading(android.webkit.WebView r8, android.webkit.WebResourceRequest r9) {
        /*
            r7 = this;
            if (r9 == 0) goto L11
            android.net.Uri r0 = r9.getUrl()
            if (r0 == 0) goto L11
            android.net.Uri r0 = r9.getUrl()
            java.lang.String r0 = r0.toString()
            goto L12
        L11:
            r0 = 0
        L12:
            if (r0 == 0) goto L62
            com.tencent.smtt.sdk.WebView r1 = r7.f20894b
            boolean r0 = r1.showDebugView(r0)
            if (r0 == 0) goto L1d
            goto L62
        L1d:
            com.tencent.smtt.sdk.WebView r0 = r7.f20894b
            r0.a(r8)
            int r8 = android.os.Build.VERSION.SDK_INT
            r0 = 24
            if (r8 < r0) goto L39
            java.lang.String r8 = "isRedirect"
            java.lang.Object r8 = com.tencent.smtt.utils.j.a(r9, r8)
            boolean r0 = r8 instanceof java.lang.Boolean
            if (r0 == 0) goto L39
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            goto L3a
        L39:
            r8 = 0
        L3a:
            r3 = r8
            com.tencent.smtt.sdk.SystemWebViewClient$e r8 = new com.tencent.smtt.sdk.SystemWebViewClient$e
            android.net.Uri r0 = r9.getUrl()
            java.lang.String r1 = r0.toString()
            boolean r2 = r9.isForMainFrame()
            boolean r4 = r9.hasGesture()
            java.lang.String r5 = r9.getMethod()
            java.util.Map r6 = r9.getRequestHeaders()
            r0 = r8
            r0.<init>(r1, r2, r3, r4, r5, r6)
            com.tencent.smtt.sdk.WebViewClient r9 = r7.f20893a
            com.tencent.smtt.sdk.WebView r0 = r7.f20894b
            boolean r8 = r9.shouldOverrideUrlLoading(r0, r8)
            return r8
        L62:
            r8 = 1
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.SystemWebViewClient.shouldOverrideUrlLoading(android.webkit.WebView, android.webkit.WebResourceRequest):boolean");
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(android.webkit.WebView webView, String str) {
        if (str == null || this.f20894b.showDebugView(str)) {
            return true;
        }
        this.f20894b.a(webView);
        return this.f20893a.shouldOverrideUrlLoading(this.f20894b, str);
    }
}
