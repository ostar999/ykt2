package io.agora.rtc.audio;

/* loaded from: classes8.dex */
public class AudioRecordingConfiguration {
    public String filePath;
    public int recordingPosition;
    public int recordingQuality;

    public AudioRecordingConfiguration() {
        this.recordingQuality = 1;
        this.recordingPosition = 0;
    }

    public AudioRecordingConfiguration(String filePath, int quality, int position) {
        this.filePath = filePath;
        this.recordingQuality = quality;
        this.recordingPosition = position;
    }
}
