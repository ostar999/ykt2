package org.wrtca.jni;

/* loaded from: classes9.dex */
class WebRtcClassLoader {
    @CalledByNative
    public static Object getClassLoader() {
        return WebRtcClassLoader.class.getClassLoader();
    }
}
