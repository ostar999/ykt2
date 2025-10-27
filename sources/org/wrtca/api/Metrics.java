package org.wrtca.api;

import java.util.HashMap;
import java.util.Map;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class Metrics {
    private static final String TAG = "Metrics";
    public final Map<String, HistogramInfo> map = new HashMap();

    public static class HistogramInfo {
        public final int bucketCount;
        public final int max;
        public final int min;
        public final Map<Integer, Integer> samples = new HashMap();

        @CalledByNative("HistogramInfo")
        public HistogramInfo(int i2, int i3, int i4) {
            this.min = i2;
            this.max = i3;
            this.bucketCount = i4;
        }

        @CalledByNative("HistogramInfo")
        public void addSample(int i2, int i3) {
            this.samples.put(Integer.valueOf(i2), Integer.valueOf(i3));
        }
    }

    @CalledByNative
    public Metrics() {
    }

    @CalledByNative
    private void add(String str, HistogramInfo histogramInfo) {
        this.map.put(str, histogramInfo);
    }

    public static void enable() {
        nativeEnable();
    }

    public static Metrics getAndReset() {
        return nativeGetAndReset();
    }

    private static native void nativeEnable();

    private static native Metrics nativeGetAndReset();
}
