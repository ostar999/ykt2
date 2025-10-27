package io.agora.rtc;

/* loaded from: classes8.dex */
public interface IAudioFrameObserver {
    boolean onMixedFrame(byte[] samples, int numOfSamples, int bytesPerSample, int channels, int samplesPerSec);

    boolean onPlaybackFrame(byte[] samples, int numOfSamples, int bytesPerSample, int channels, int samplesPerSec);

    boolean onPlaybackFrameBeforeMixing(byte[] samples, int numOfSamples, int bytesPerSample, int channels, int samplesPerSec, int uid);

    boolean onRecordFrame(byte[] samples, int numOfSamples, int bytesPerSample, int channels, int samplesPerSec);
}
