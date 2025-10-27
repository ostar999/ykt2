package com.google.android.exoplayer2.video;

import android.os.Bundle;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;

/* loaded from: classes3.dex */
public final class VideoSize implements Bundleable {
    private static final int DEFAULT_HEIGHT = 0;
    private static final float DEFAULT_PIXEL_WIDTH_HEIGHT_RATIO = 1.0f;
    private static final int DEFAULT_UNAPPLIED_ROTATION_DEGREES = 0;
    private static final int DEFAULT_WIDTH = 0;
    private static final int FIELD_HEIGHT = 1;
    private static final int FIELD_PIXEL_WIDTH_HEIGHT_RATIO = 3;
    private static final int FIELD_UNAPPLIED_ROTATION_DEGREES = 2;
    private static final int FIELD_WIDTH = 0;

    @IntRange(from = 0)
    public final int height;

    @FloatRange(from = 0.0d, fromInclusive = false)
    public final float pixelWidthHeightRatio;

    @IntRange(from = 0, to = 359)
    public final int unappliedRotationDegrees;

    @IntRange(from = 0)
    public final int width;
    public static final VideoSize UNKNOWN = new VideoSize(0, 0);
    public static final Bundleable.Creator<VideoSize> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.video.o
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return VideoSize.lambda$static$0(bundle);
        }
    };

    public VideoSize(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3) {
        this(i2, i3, 0, 1.0f);
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ VideoSize lambda$static$0(Bundle bundle) {
        return new VideoSize(bundle.getInt(keyForField(0), 0), bundle.getInt(keyForField(1), 0), bundle.getInt(keyForField(2), 0), bundle.getFloat(keyForField(3), 1.0f));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VideoSize)) {
            return false;
        }
        VideoSize videoSize = (VideoSize) obj;
        return this.width == videoSize.width && this.height == videoSize.height && this.unappliedRotationDegrees == videoSize.unappliedRotationDegrees && this.pixelWidthHeightRatio == videoSize.pixelWidthHeightRatio;
    }

    public int hashCode() {
        return ((((((217 + this.width) * 31) + this.height) * 31) + this.unappliedRotationDegrees) * 31) + Float.floatToRawIntBits(this.pixelWidthHeightRatio);
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(keyForField(0), this.width);
        bundle.putInt(keyForField(1), this.height);
        bundle.putInt(keyForField(2), this.unappliedRotationDegrees);
        bundle.putFloat(keyForField(3), this.pixelWidthHeightRatio);
        return bundle;
    }

    public VideoSize(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3, @IntRange(from = 0, to = 359) int i4, @FloatRange(from = 0.0d, fromInclusive = false) float f2) {
        this.width = i2;
        this.height = i3;
        this.unappliedRotationDegrees = i4;
        this.pixelWidthHeightRatio = f2;
    }
}
