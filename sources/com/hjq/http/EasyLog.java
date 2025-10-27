package com.hjq.http;

/* loaded from: classes4.dex */
public final class EasyLog {
    public static void json(String str) {
        if (EasyConfig.getInstance().isLogEnabled()) {
            EasyConfig.getInstance().getLogStrategy().json(str);
        }
    }

    public static void print() {
        print("----------------------------------------");
    }

    public static void print(String str) {
        if (EasyConfig.getInstance().isLogEnabled()) {
            EasyConfig.getInstance().getLogStrategy().print(str);
        }
    }

    public static void print(String str, String str2) {
        if (EasyConfig.getInstance().isLogEnabled()) {
            EasyConfig.getInstance().getLogStrategy().print(str, str2);
        }
    }

    public static void print(Throwable th) {
        if (EasyConfig.getInstance().isLogEnabled()) {
            EasyConfig.getInstance().getLogStrategy().print(th);
        }
    }

    public static void print(StackTraceElement[] stackTraceElementArr) {
        if (EasyConfig.getInstance().isLogEnabled()) {
            EasyConfig.getInstance().getLogStrategy().print(stackTraceElementArr);
        }
    }
}
