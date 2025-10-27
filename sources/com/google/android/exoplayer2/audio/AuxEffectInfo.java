package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.yikaobang.yixue.R2;

/* loaded from: classes3.dex */
public final class AuxEffectInfo {
    public static final int NO_AUX_EFFECT_ID = 0;
    public final int effectId;
    public final float sendLevel;

    public AuxEffectInfo(int i2, float f2) {
        this.effectId = i2;
        this.sendLevel = f2;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AuxEffectInfo.class != obj.getClass()) {
            return false;
        }
        AuxEffectInfo auxEffectInfo = (AuxEffectInfo) obj;
        return this.effectId == auxEffectInfo.effectId && Float.compare(auxEffectInfo.sendLevel, this.sendLevel) == 0;
    }

    public int hashCode() {
        return ((R2.attr.bl_checkable_gradient_endColor + this.effectId) * 31) + Float.floatToIntBits(this.sendLevel);
    }
}
