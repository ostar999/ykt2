package com.xiaomi.channel.commonutils.logger;

import android.util.Log;

/* loaded from: classes6.dex */
public class a implements LoggerInterface {

    /* renamed from: a, reason: collision with root package name */
    private String f24490a = "xiaomi";

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void log(String str) {
        Log.v(this.f24490a, str);
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void log(String str, Throwable th) {
        Log.v(this.f24490a, str, th);
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void setTag(String str) {
        this.f24490a = str;
    }
}
