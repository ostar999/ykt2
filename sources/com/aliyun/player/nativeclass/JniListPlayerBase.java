package com.aliyun.player.nativeclass;

import android.content.Context;
import com.aliyun.utils.f;

/* loaded from: classes2.dex */
public class JniListPlayerBase {
    private long mNativeContext = 0;

    static {
        f.b();
    }

    public JniListPlayerBase(Context context, long j2, long j3) {
        nConstruct(j2, j3);
    }

    public static void loadClass() {
    }

    public void clear() {
        nClear();
    }

    public void enablePreloadStrategy(int i2, boolean z2) {
        nEnablePreloadStrategy(i2, z2);
    }

    public String getCurrentUid() {
        return nGetCurrentUid();
    }

    public int getMaxPreloadMemorySizeMB() {
        return nGetMaxPreloadMemorySizeMB();
    }

    public long getNativeListContext() {
        return this.mNativeContext;
    }

    public native void nClear();

    public native void nConstruct(long j2, long j3);

    public native void nEnablePreloadStrategy(int i2, boolean z2);

    public native String nGetCurrentUid();

    public native int nGetMaxPreloadMemorySizeMB();

    public native void nRelease();

    public native void nRemoveSource(String str);

    public native void nSetMaxPreloadMemorySizeMB(int i2);

    public native void nSetPreloadCount(int i2);

    public native void nSetPreloadCount(int i2, int i3);

    public native void nSetPreloadScene(int i2);

    public native void nSetPreloadStrategyParam(int i2, String str);

    public native void nStop();

    public native void nUpdatePreloadConfig(Object obj);

    public native void nUpdatePreloadConfig(String str, Object obj);

    public void release() {
        nRelease();
    }

    public void removeSource(String str) {
        nRemoveSource(str);
    }

    public void setMaxPreloadMemorySizeMB(int i2) {
        nSetMaxPreloadMemorySizeMB(i2);
    }

    public void setNativeListContext(long j2) {
        this.mNativeContext = j2;
    }

    public void setPreloadCount(int i2) {
        nSetPreloadCount(i2);
    }

    public void setPreloadCount(int i2, int i3) {
        nSetPreloadCount(i2, i3);
    }

    public void setPreloadScene(int i2) {
        nSetPreloadScene(i2);
    }

    public void setPreloadStrategyParam(int i2, String str) {
        nSetPreloadStrategyParam(i2, str);
    }

    public void stop() {
        nStop();
    }

    public void updatePreloadConfig(PreloadConfig preloadConfig) {
        nUpdatePreloadConfig(preloadConfig);
    }

    public void updatePreloadConfig(String str, PreloadConfig preloadConfig) {
        nUpdatePreloadConfig(str, preloadConfig);
    }
}
