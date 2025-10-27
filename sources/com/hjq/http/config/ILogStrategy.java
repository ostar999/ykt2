package com.hjq.http.config;

/* loaded from: classes4.dex */
public interface ILogStrategy {
    void json(String str);

    void print();

    void print(String str);

    void print(String str, String str2);

    void print(Throwable th);

    void print(StackTraceElement[] stackTraceElementArr);
}
