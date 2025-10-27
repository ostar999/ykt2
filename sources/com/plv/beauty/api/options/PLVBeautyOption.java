package com.plv.beauty.api.options;

import androidx.annotation.FloatRange;

/* loaded from: classes4.dex */
public enum PLVBeautyOption implements IPLVBeautyOption {
    BEAUTY_WHITEN,
    BEAUTY_SHARP,
    BEAUTY_SMOOTH,
    RESHAPE_DEFORM_OVERALL,
    RESHAPE_DEFORM_EYE,
    RESHAPE_DEFORM_NOSE,
    RESHAPE_DEFORM_ZOOM_MOUTH,
    RESHAPE_DEFORM_FOREHEAD,
    RESHAPE_DEFORM_ZOOM_JAWBONE,
    RESHAPE_BEAUTY_WHITEN_TEETH,
    RESHAPE_BEAUTY_BRIGHTEN_EYE;


    @FloatRange(from = 0.0d, to = 1.0d)
    private float intensity = 0.0f;

    PLVBeautyOption() {
    }

    public float getIntensity() {
        return this.intensity;
    }

    public PLVBeautyOption intensity(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.intensity = f2;
        return this;
    }
}
