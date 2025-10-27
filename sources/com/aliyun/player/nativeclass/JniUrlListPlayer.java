package com.aliyun.player.nativeclass;

import android.content.Context;
import com.aliyun.utils.f;

/* loaded from: classes2.dex */
public class JniUrlListPlayer extends JniListPlayerBase {
    private static final String TAG = "JniUrlListPlayer";

    static {
        f.b();
    }

    public JniUrlListPlayer(Context context, long j2, long j3) {
        super(context, j2, j3);
    }

    public static void loadClass() {
    }

    public void addUrl(String str, String str2) {
        nAddUrl(str, str2);
    }

    public void addUrl(String str, String str2, PreloadConfig preloadConfig) {
        nAddUrl(str, str2, preloadConfig);
    }

    public long getCurrentPlayerIndex() {
        return nGetCurrentPlayerIndex();
    }

    public long getPreRenderPlayerIndex() {
        return nGetPreRenderPlayerIndex();
    }

    public boolean moveTo(String str) {
        return nMoveTo(str);
    }

    public boolean moveToNext(boolean z2) {
        return nMoveToNext(z2);
    }

    public boolean moveToPrev() {
        return nMoveToPrev();
    }

    public native void nAddUrl(String str, String str2);

    public native void nAddUrl(String str, String str2, Object obj);

    public native long nGetCurrentPlayerIndex();

    public native long nGetPreRenderPlayerIndex();

    public native boolean nMoveTo(String str);

    public native boolean nMoveToNext(boolean z2);

    public native boolean nMoveToPrev();
}
