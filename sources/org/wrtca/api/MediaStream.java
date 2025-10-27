package org.wrtca.api;

import cn.hutool.core.text.StrPool;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;
import org.wrtca.jni.JniCommon;
import org.wrtca.log.Logging;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class MediaStream {
    private static final String TAG = "MediaStream";
    public final long nativeStream;
    public final List<AudioTrack> audioTracks = new ArrayList();
    public final List<VideoTrack> videoTracks = new ArrayList();
    public final List<VideoTrack> preservedVideoTracks = new ArrayList();

    @CalledByNative
    public MediaStream(long j2) {
        this.nativeStream = j2;
    }

    private static native boolean nativeAddAudioTrackToNativeStream(long j2, long j3);

    private static native boolean nativeAddVideoTrackToNativeStream(long j2, long j3);

    private static native String nativeGetLabel(long j2);

    private static native boolean nativeRemoveAudioTrack(long j2, long j3);

    private static native boolean nativeRemoveVideoTrack(long j2, long j3);

    private static void removeMediaStreamTrack(List<? extends MediaStreamTrack> list, long j2) {
        Iterator<? extends MediaStreamTrack> it = list.iterator();
        while (it.hasNext()) {
            MediaStreamTrack next = it.next();
            if (next.nativeTrack == j2) {
                next.dispose();
                it.remove();
                return;
            }
        }
        Logging.e(TAG, "Couldn't not find track");
    }

    @CalledByNative
    public void addNativeAudioTrack(long j2) {
        this.audioTracks.add(new AudioTrack(j2));
    }

    @CalledByNative
    public void addNativeVideoTrack(long j2) {
        this.videoTracks.add(new VideoTrack(j2));
    }

    public boolean addPreservedTrack(VideoTrack videoTrack) {
        if (!nativeAddVideoTrackToNativeStream(this.nativeStream, videoTrack.nativeTrack)) {
            return false;
        }
        this.preservedVideoTracks.add(videoTrack);
        return true;
    }

    public boolean addTrack(AudioTrack audioTrack) {
        if (!nativeAddAudioTrackToNativeStream(this.nativeStream, audioTrack.nativeTrack)) {
            return false;
        }
        this.audioTracks.add(audioTrack);
        return true;
    }

    @CalledByNative
    public void dispose() {
        while (!this.audioTracks.isEmpty()) {
            AudioTrack audioTrack = this.audioTracks.get(0);
            removeTrack(audioTrack);
            audioTrack.dispose();
        }
        while (!this.videoTracks.isEmpty()) {
            VideoTrack videoTrack = this.videoTracks.get(0);
            removeTrack(videoTrack);
            videoTrack.dispose();
        }
        while (!this.preservedVideoTracks.isEmpty()) {
            removeTrack(this.preservedVideoTracks.get(0));
        }
        JniCommon.nativeReleaseRef(this.nativeStream);
    }

    public String label() {
        return nativeGetLabel(this.nativeStream);
    }

    @CalledByNative
    public void removeAudioTrack(long j2) {
        removeMediaStreamTrack(this.audioTracks, j2);
    }

    public boolean removeTrack(AudioTrack audioTrack) {
        this.audioTracks.remove(audioTrack);
        return nativeRemoveAudioTrack(this.nativeStream, audioTrack.nativeTrack);
    }

    @CalledByNative
    public void removeVideoTrack(long j2) {
        removeMediaStreamTrack(this.videoTracks, j2);
    }

    public String toString() {
        return StrPool.BRACKET_START + label() + ":A=" + this.audioTracks.size() + ":V=" + this.videoTracks.size() + StrPool.BRACKET_END;
    }

    public boolean addTrack(VideoTrack videoTrack) {
        if (!nativeAddVideoTrackToNativeStream(this.nativeStream, videoTrack.nativeTrack)) {
            return false;
        }
        this.videoTracks.add(videoTrack);
        return true;
    }

    public boolean removeTrack(VideoTrack videoTrack) {
        this.videoTracks.remove(videoTrack);
        this.preservedVideoTracks.remove(videoTrack);
        return nativeRemoveVideoTrack(this.nativeStream, videoTrack.nativeTrack);
    }
}
