package com.easefun.polyv.mediasdk.jni;

import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;

/* loaded from: classes3.dex */
public class PLVPlayerNative {
    static {
        IjkMediaPlayer.loadLibrariesOnce(null);
    }

    public static String decryptPdx(byte[] bArr, int i2, String str) {
        try {
            return new String(native_decryptPdx(bArr, i2, str));
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static native byte[] native_decryptPdx(byte[] bArr, int i2, String str) throws Throwable;
}
