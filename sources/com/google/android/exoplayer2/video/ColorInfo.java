package com.google.android.exoplayer2.video;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.yikaobang.yixue.R2;
import java.util.Arrays;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class ColorInfo implements Bundleable {
    public static final Bundleable.Creator<ColorInfo> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.video.a
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return ColorInfo.lambda$static$0(bundle);
        }
    };
    private static final int FIELD_COLOR_RANGE = 1;
    private static final int FIELD_COLOR_SPACE = 0;
    private static final int FIELD_COLOR_TRANSFER = 2;
    private static final int FIELD_HDR_STATIC_INFO = 3;
    public final int colorRange;
    public final int colorSpace;
    public final int colorTransfer;
    private int hashCode;

    @Nullable
    public final byte[] hdrStaticInfo;

    public ColorInfo(int i2, int i3, int i4, @Nullable byte[] bArr) {
        this.colorSpace = i2;
        this.colorRange = i3;
        this.colorTransfer = i4;
        this.hdrStaticInfo = bArr;
    }

    @Pure
    public static int isoColorPrimariesToColorSpace(int i2) {
        if (i2 == 1) {
            return 1;
        }
        if (i2 != 9) {
            return (i2 == 4 || i2 == 5 || i2 == 6 || i2 == 7) ? 2 : -1;
        }
        return 6;
    }

    @Pure
    public static int isoTransferCharacteristicsToColorTransfer(int i2) {
        if (i2 == 1) {
            return 3;
        }
        if (i2 == 16) {
            return 6;
        }
        if (i2 != 18) {
            return (i2 == 6 || i2 == 7) ? 3 : -1;
        }
        return 7;
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ColorInfo lambda$static$0(Bundle bundle) {
        return new ColorInfo(bundle.getInt(keyForField(0), -1), bundle.getInt(keyForField(1), -1), bundle.getInt(keyForField(2), -1), bundle.getByteArray(keyForField(3)));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ColorInfo.class != obj.getClass()) {
            return false;
        }
        ColorInfo colorInfo = (ColorInfo) obj;
        return this.colorSpace == colorInfo.colorSpace && this.colorRange == colorInfo.colorRange && this.colorTransfer == colorInfo.colorTransfer && Arrays.equals(this.hdrStaticInfo, colorInfo.hdrStaticInfo);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = ((((((R2.attr.bl_checkable_gradient_endColor + this.colorSpace) * 31) + this.colorRange) * 31) + this.colorTransfer) * 31) + Arrays.hashCode(this.hdrStaticInfo);
        }
        return this.hashCode;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(keyForField(0), this.colorSpace);
        bundle.putInt(keyForField(1), this.colorRange);
        bundle.putInt(keyForField(2), this.colorTransfer);
        bundle.putByteArray(keyForField(3), this.hdrStaticInfo);
        return bundle;
    }

    public String toString() {
        int i2 = this.colorSpace;
        int i3 = this.colorRange;
        int i4 = this.colorTransfer;
        boolean z2 = this.hdrStaticInfo != null;
        StringBuilder sb = new StringBuilder(55);
        sb.append("ColorInfo(");
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        sb.append(", ");
        sb.append(i4);
        sb.append(", ");
        sb.append(z2);
        sb.append(")");
        return sb.toString();
    }
}
