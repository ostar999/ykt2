package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.CheckResult;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.yikaobang.yixue.R2;

/* loaded from: classes3.dex */
public final class PlaybackParameters implements Bundleable {
    private static final int FIELD_PITCH = 1;
    private static final int FIELD_SPEED = 0;
    public final float pitch;
    private final int scaledUsPerMs;
    public final float speed;
    public static final PlaybackParameters DEFAULT = new PlaybackParameters(1.0f);
    public static final Bundleable.Creator<PlaybackParameters> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.u1
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return PlaybackParameters.lambda$static$0(bundle);
        }
    };

    public PlaybackParameters(float f2) {
        this(f2, 1.0f);
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ PlaybackParameters lambda$static$0(Bundle bundle) {
        return new PlaybackParameters(bundle.getFloat(keyForField(0), 1.0f), bundle.getFloat(keyForField(1), 1.0f));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || PlaybackParameters.class != obj.getClass()) {
            return false;
        }
        PlaybackParameters playbackParameters = (PlaybackParameters) obj;
        return this.speed == playbackParameters.speed && this.pitch == playbackParameters.pitch;
    }

    public long getMediaTimeUsForPlayoutTimeMs(long j2) {
        return j2 * this.scaledUsPerMs;
    }

    public int hashCode() {
        return ((R2.attr.bl_checkable_gradient_endColor + Float.floatToRawIntBits(this.speed)) * 31) + Float.floatToRawIntBits(this.pitch);
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putFloat(keyForField(0), this.speed);
        bundle.putFloat(keyForField(1), this.pitch);
        return bundle;
    }

    public String toString() {
        return Util.formatInvariant("PlaybackParameters(speed=%.2f, pitch=%.2f)", Float.valueOf(this.speed), Float.valueOf(this.pitch));
    }

    @CheckResult
    public PlaybackParameters withSpeed(@FloatRange(from = 0.0d, fromInclusive = false) float f2) {
        return new PlaybackParameters(f2, this.pitch);
    }

    public PlaybackParameters(@FloatRange(from = 0.0d, fromInclusive = false) float f2, @FloatRange(from = 0.0d, fromInclusive = false) float f3) {
        Assertions.checkArgument(f2 > 0.0f);
        Assertions.checkArgument(f3 > 0.0f);
        this.speed = f2;
        this.pitch = f3;
        this.scaledUsPerMs = Math.round(f2 * 1000.0f);
    }
}
