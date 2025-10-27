package com.plv.beauty.api.options;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public abstract class PLVFilterOption implements IPLVBeautyOption {
    protected static PLVFilterOption NO_EFFECT_OPTION;

    @Nullable
    protected String description;

    @Nullable
    @DrawableRes
    protected Integer iconDrawableId;

    @FloatRange(from = 0.0d, to = 1.0d)
    private float intensity = 0.0f;

    @Nullable
    protected String key;

    @Nullable
    protected String name;

    public static PLVFilterOption getNoEffectOption() {
        return NO_EFFECT_OPTION;
    }

    public boolean canAdjustIntensity() {
        return true;
    }

    public String getDescription() {
        return this.description;
    }

    @Nullable
    public Integer getIconDrawableId() {
        return this.iconDrawableId;
    }

    public float getIntensity() {
        return this.intensity;
    }

    @Nullable
    public String getKey() {
        return this.key;
    }

    @Nullable
    public String getName() {
        return this.name;
    }

    public PLVFilterOption intensity(float f2) {
        this.intensity = f2;
        return this;
    }
}
