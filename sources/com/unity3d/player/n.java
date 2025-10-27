package com.unity3d.player;

import android.os.Build;
import java.lang.Thread;

/* loaded from: classes6.dex */
final class n implements Thread.UncaughtExceptionHandler {

    /* renamed from: a, reason: collision with root package name */
    private volatile Thread.UncaughtExceptionHandler f24168a;

    public final synchronized boolean a() {
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler == this) {
            return false;
        }
        this.f24168a = defaultUncaughtExceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(this);
        return true;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        try {
            Error error = new Error(String.format("FATAL EXCEPTION [%s]\n", thread.getName()) + String.format("Unity version     : %s\n", "2021.3.22f1c1") + String.format("Device model      : %s %s\n", Build.MANUFACTURER, Build.MODEL) + String.format("Device fingerprint: %s\n", Build.FINGERPRINT) + String.format("Build Type        : %s\n", "Release") + String.format("Scripting Backend : %s\n", "IL2CPP") + String.format("ABI               : %s\n", Build.CPU_ABI) + String.format("Strip Engine Code : %s\n", Boolean.TRUE));
            error.setStackTrace(new StackTraceElement[0]);
            error.initCause(th);
            this.f24168a.uncaughtException(thread, error);
        } catch (Throwable unused) {
            this.f24168a.uncaughtException(thread, th);
        }
    }
}
