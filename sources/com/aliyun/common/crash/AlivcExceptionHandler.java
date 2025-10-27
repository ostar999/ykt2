package com.aliyun.common.crash;

import android.os.Process;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;

@Keep
/* loaded from: classes2.dex */
public class AlivcExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

    private String dumpCrashStack(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        do {
            th.printStackTrace(printWriter);
            th = th.getCause();
        } while (th != null);
        printWriter.close();
        return stringWriter.toString();
    }

    private static native void nativeOnCrashCallback(int i2, long j2, String str, String str2);

    public void register() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void unRegister() {
        Thread.setDefaultUncaughtExceptionHandler(this.mDefaultHandler);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable th) {
        try {
            nativeOnCrashCallback(Process.myPid(), thread.getId(), thread.getName(), dumpCrashStack(th));
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.mDefaultHandler;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        } else {
            Process.killProcess(Process.myPid());
        }
    }
}
