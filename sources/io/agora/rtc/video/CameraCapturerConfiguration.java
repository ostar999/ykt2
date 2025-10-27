package io.agora.rtc.video;

/* loaded from: classes8.dex */
public class CameraCapturerConfiguration {
    public CAPTURER_OUTPUT_PREFERENCE preference;

    public enum CAPTURER_OUTPUT_PREFERENCE {
        CAPTURER_OUTPUT_PREFERENCE_AUTO(0),
        CAPTURER_OUTPUT_PREFERENCE_PERFORMANCE(1),
        CAPTURER_OUTPUT_PREFERENCE_PREVIEW(2);

        private int value;

        CAPTURER_OUTPUT_PREFERENCE(int v2) {
            this.value = v2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public CameraCapturerConfiguration(CAPTURER_OUTPUT_PREFERENCE preference) {
        this.preference = preference;
    }
}
