package com.tencent.smtt.sdk;

import android.database.sqlite.SQLiteException;
import java.lang.Thread;

/* loaded from: classes6.dex */
public class h implements Thread.UncaughtExceptionHandler {
    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        if (!(th instanceof SQLiteException)) {
            throw new RuntimeException(th);
        }
    }
}
