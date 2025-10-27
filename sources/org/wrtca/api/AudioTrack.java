package org.wrtca.api;

import java.util.IdentityHashMap;
import java.util.Iterator;
import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class AudioTrack extends MediaStreamTrack {
    private final IdentityHashMap<AudioSink, Long> sinks;

    public AudioTrack(long j2) {
        super(j2);
        this.sinks = new IdentityHashMap<>();
    }

    private static native void nativeAddSink(long j2, long j3);

    private static native void nativeFreeSink(long j2);

    private static native void nativeRemoveSink(long j2, long j3);

    private static native void nativeSetVolume(long j2, double d3);

    private static native long nativeWrapSink(AudioSink audioSink);

    public void addSink(AudioSink audioSink) {
        long jNativeWrapSink = nativeWrapSink(audioSink);
        this.sinks.put(audioSink, Long.valueOf(jNativeWrapSink));
        nativeAddSink(this.nativeTrack, jNativeWrapSink);
    }

    @Override // org.wrtca.api.MediaStreamTrack
    public void dispose() {
        Iterator<Long> it = this.sinks.values().iterator();
        while (it.hasNext()) {
            long jLongValue = it.next().longValue();
            nativeRemoveSink(this.nativeTrack, jLongValue);
            nativeFreeSink(jLongValue);
        }
        this.sinks.clear();
        super.dispose();
    }

    public void removeSink(AudioSink audioSink) {
        if (this.sinks.isEmpty()) {
            return;
        }
        long jLongValue = this.sinks.remove(audioSink).longValue();
        if (jLongValue != 0) {
            nativeRemoveSink(this.nativeTrack, jLongValue);
            nativeFreeSink(jLongValue);
        }
    }

    public void setVolume(double d3) {
        nativeSetVolume(this.nativeTrack, d3);
    }
}
