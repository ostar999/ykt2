package io.agora.rtc.video;

/* loaded from: classes8.dex */
public class BeautyOptions {
    public static final int LIGHTENING_CONTRAST_HIGH = 2;
    public static final int LIGHTENING_CONTRAST_LOW = 0;
    public static final int LIGHTENING_CONTRAST_NORMAL = 1;
    public int lighteningContrastLevel;
    public float lighteningLevel;
    public float rednessLevel;
    public float smoothnessLevel;

    public BeautyOptions(int contrastLevel, float lightening, float smoothness, float redness) {
        this.lighteningContrastLevel = contrastLevel;
        this.lighteningLevel = lightening;
        this.smoothnessLevel = smoothness;
        this.rednessLevel = redness;
    }

    public BeautyOptions() {
        this.lighteningContrastLevel = 1;
        this.lighteningLevel = 0.7f;
        this.smoothnessLevel = 0.5f;
        this.rednessLevel = 0.1f;
    }
}
