package com.xiaomi.push;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;

/* renamed from: com.xiaomi.push.do, reason: invalid class name */
/* loaded from: classes6.dex */
public class Cdo implements LoggerInterface {

    /* renamed from: a, reason: collision with root package name */
    private LoggerInterface f24739a;

    /* renamed from: b, reason: collision with root package name */
    private LoggerInterface f24740b;

    public Cdo(LoggerInterface loggerInterface, LoggerInterface loggerInterface2) {
        this.f24739a = loggerInterface;
        this.f24740b = loggerInterface2;
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void log(String str) {
        LoggerInterface loggerInterface = this.f24739a;
        if (loggerInterface != null) {
            loggerInterface.log(str);
        }
        LoggerInterface loggerInterface2 = this.f24740b;
        if (loggerInterface2 != null) {
            loggerInterface2.log(str);
        }
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void log(String str, Throwable th) {
        LoggerInterface loggerInterface = this.f24739a;
        if (loggerInterface != null) {
            loggerInterface.log(str, th);
        }
        LoggerInterface loggerInterface2 = this.f24740b;
        if (loggerInterface2 != null) {
            loggerInterface2.log(str, th);
        }
    }

    @Override // com.xiaomi.channel.commonutils.logger.LoggerInterface
    public void setTag(String str) {
    }
}
