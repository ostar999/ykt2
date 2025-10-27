package org.wrtca.api;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class VideoTrack extends MediaStreamTrack {
    private static final String TAG = "VideoTrack";
    private final List<VideoRenderer> renderers;
    private final IdentityHashMap<VideoSink, Long> sinks;

    public VideoTrack(long j2) {
        super(j2);
        this.renderers = new ArrayList();
        this.sinks = new IdentityHashMap<>();
    }

    private static native void nativeAddSink(long j2, long j3, boolean z2);

    private static native void nativeFreeSink(long j2);

    private static native void nativeRemoveSink(long j2, long j3);

    private static native long nativeWrapSink(VideoSink videoSink);

    public void addRenderer(VideoRenderer videoRenderer) {
        this.renderers.add(videoRenderer);
        nativeAddSink(this.nativeTrack, videoRenderer.nativeVideoRenderer, false);
    }

    public void addSink(VideoSink videoSink) {
        c.h.a(TAG, "add sinks before size " + this.sinks.size());
        long jNativeWrapSink = nativeWrapSink(videoSink);
        this.sinks.put(videoSink, Long.valueOf(jNativeWrapSink));
        nativeAddSink(this.nativeTrack, jNativeWrapSink, true);
        c.h.a(TAG, "add sinks after size " + this.sinks.size());
        Iterator<VideoSink> it = this.sinks.keySet().iterator();
        while (it.hasNext()) {
            c.h.a(TAG, "add after sink key " + it.next());
        }
    }

    @Override // org.wrtca.api.MediaStreamTrack
    public void dispose() {
        for (VideoRenderer videoRenderer : this.renderers) {
            nativeRemoveSink(this.nativeTrack, videoRenderer.nativeVideoRenderer);
            videoRenderer.dispose();
        }
        this.renderers.clear();
        Iterator<Long> it = this.sinks.values().iterator();
        while (it.hasNext()) {
            long jLongValue = it.next().longValue();
            nativeRemoveSink(this.nativeTrack, jLongValue);
            nativeFreeSink(jLongValue);
        }
        this.sinks.clear();
        c.h.a(TAG, "sinks clear dispose " + this.sinks.size());
        super.dispose();
    }

    public void removeRenderer(VideoRenderer videoRenderer) {
        if (this.renderers.remove(videoRenderer)) {
            nativeRemoveSink(this.nativeTrack, videoRenderer.nativeVideoRenderer);
            c.h.a(TAG, "VideoTrack native remove render: " + videoRenderer.nativeVideoRenderer);
            videoRenderer.dispose();
            c.h.a("dispose", " render: " + videoRenderer);
        }
    }

    public void removeSink(VideoSink videoSink) {
        c.h.a(TAG, "remove before sinks size " + this.sinks.size());
        Long lRemove = this.sinks.remove(videoSink);
        if (lRemove != null) {
            long jLongValue = lRemove.longValue();
            if (jLongValue != 0) {
                nativeRemoveSink(this.nativeTrack, jLongValue);
                nativeFreeSink(jLongValue);
                c.h.a(TAG, "remove sink suc");
            }
            c.h.a(TAG, "remove after sinks size " + this.sinks.size());
        } else {
            c.h.a(TAG, "remove sink " + videoSink + " is not in list");
        }
        Iterator<VideoSink> it = this.sinks.keySet().iterator();
        while (it.hasNext()) {
            c.h.a(TAG, "remove after sink key " + it.next());
        }
    }
}
