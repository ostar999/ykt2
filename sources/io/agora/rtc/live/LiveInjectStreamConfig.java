package io.agora.rtc.live;

/* loaded from: classes8.dex */
public class LiveInjectStreamConfig {
    public int width = 0;
    public int height = 0;
    public int videoGop = 30;
    public int videoFramerate = 15;
    public int videoBitrate = 400;
    public AudioSampleRateType audioSampleRate = AudioSampleRateType.TYPE_44100;
    public int audioBitrate = 48;
    public int audioChannels = 1;

    public enum AudioSampleRateType {
        TYPE_32000(32000),
        TYPE_44100(44100),
        TYPE_48000(48000);

        private int value;

        AudioSampleRateType(int v2) {
            this.value = v2;
        }

        public static int getValue(AudioSampleRateType type) {
            return type.value;
        }
    }
}
