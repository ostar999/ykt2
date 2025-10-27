package com.umeng.socialize.net.dplus.cache;

/* loaded from: classes6.dex */
public abstract class IReader<T> {

    /* renamed from: a, reason: collision with root package name */
    private String f23797a;
    public T result;

    public IReader(String str) {
        this.f23797a = str;
    }

    public static double formatSize(long j2) {
        return (j2 / 1024.0d) / 1024.0d;
    }

    public abstract void create(String str);

    public String getLogFileName() {
        return this.f23797a;
    }
}
