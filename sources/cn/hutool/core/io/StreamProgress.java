package cn.hutool.core.io;

/* loaded from: classes.dex */
public interface StreamProgress {
    void finish();

    void progress(long j2, long j3);

    void start();
}
