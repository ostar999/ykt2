package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import com.tencent.open.log.SLog;

/* loaded from: classes6.dex */
public abstract class b extends Dialog {

    /* renamed from: a, reason: collision with root package name */
    protected a f20518a;

    /* renamed from: b, reason: collision with root package name */
    @SuppressLint({"NewApi"})
    protected final WebChromeClient f20519b;

    public b(Context context, int i2) {
        super(context, i2);
        this.f20519b = new WebChromeClient() { // from class: com.tencent.open.b.1
            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (consoleMessage == null) {
                    return false;
                }
                SLog.i("openSDK_LOG.JsDialog", "WebChromeClient onConsoleMessage" + consoleMessage.message() + " -- From  111 line " + consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
                b.this.a(consoleMessage.message());
                return true;
            }

            @Override // android.webkit.WebChromeClient
            public void onConsoleMessage(String str, int i3, String str2) {
                SLog.i("openSDK_LOG.JsDialog", "WebChromeClient onConsoleMessage" + str + " -- From 222 line " + i3 + " of " + str2);
            }
        };
    }

    public abstract void a(String str);

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f20518a = new a();
    }
}
