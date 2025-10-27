package com.tencent.smtt.sdk;

import android.content.Intent;
import android.net.Uri;

/* loaded from: classes6.dex */
class b implements com.tencent.smtt.export.external.interfaces.DownloadListener {

    /* renamed from: a, reason: collision with root package name */
    private DownloadListener f21148a;

    /* renamed from: b, reason: collision with root package name */
    private WebView f21149b;

    public b(WebView webView, DownloadListener downloadListener, boolean z2) {
        this.f21148a = downloadListener;
        this.f21149b = webView;
    }

    @Override // com.tencent.smtt.export.external.interfaces.DownloadListener
    public void onDownloadStart(String str, String str2, String str3, String str4, long j2) {
        onDownloadStart(str, null, null, str2, str3, str4, j2, null, null);
    }

    @Override // com.tencent.smtt.export.external.interfaces.DownloadListener
    public void onDownloadStart(String str, String str2, byte[] bArr, String str3, String str4, String str5, long j2, String str6, String str7) {
        DownloadListener downloadListener = this.f21148a;
        if (downloadListener != null) {
            downloadListener.onDownloadStart(str, str3, str4, str5, j2);
            return;
        }
        if (QbSdk.canOpenMimeFileType(this.f21149b.getContext(), str5)) {
            Intent intent = new Intent("com.tencent.QQBrowser.action.sdk.document");
            intent.setFlags(268435456);
            intent.putExtra("key_reader_sdk_url", str);
            intent.putExtra("key_reader_sdk_type", 1);
            intent.setData(Uri.parse(str));
            this.f21149b.getContext().startActivity(intent);
        }
    }

    @Override // com.tencent.smtt.export.external.interfaces.DownloadListener
    public void onDownloadVideo(String str, long j2, int i2) {
    }
}
