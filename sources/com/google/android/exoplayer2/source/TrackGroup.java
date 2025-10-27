package com.google.android.exoplayer2.source;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.BundleableUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.yikaobang.yixue.R2;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class TrackGroup implements Bundleable {
    public static final Bundleable.Creator<TrackGroup> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.source.i0
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return TrackGroup.lambda$static$0(bundle);
        }
    };
    private static final int FIELD_FORMATS = 0;
    private static final String TAG = "TrackGroup";
    private final Format[] formats;
    private int hashCode;
    public final int length;

    public TrackGroup(Format... formatArr) {
        Assertions.checkArgument(formatArr.length > 0);
        this.formats = formatArr;
        this.length = formatArr.length;
        verifyCorrectness();
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ TrackGroup lambda$static$0(Bundle bundle) {
        return new TrackGroup((Format[]) BundleableUtil.fromBundleNullableList(Format.CREATOR, bundle.getParcelableArrayList(keyForField(0)), ImmutableList.of()).toArray(new Format[0]));
    }

    private static void logErrorMessage(String str, @Nullable String str2, @Nullable String str3, int i2) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 78 + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append("Different ");
        sb.append(str);
        sb.append(" combined in one TrackGroup: '");
        sb.append(str2);
        sb.append("' (track 0) and '");
        sb.append(str3);
        sb.append("' (track ");
        sb.append(i2);
        sb.append(")");
        Log.e(TAG, "", new IllegalStateException(sb.toString()));
    }

    private static String normalizeLanguage(@Nullable String str) {
        return (str == null || str.equals(C.LANGUAGE_UNDETERMINED)) ? "" : str;
    }

    private static int normalizeRoleFlags(int i2) {
        return i2 | 16384;
    }

    private void verifyCorrectness() {
        String strNormalizeLanguage = normalizeLanguage(this.formats[0].language);
        int iNormalizeRoleFlags = normalizeRoleFlags(this.formats[0].roleFlags);
        int i2 = 1;
        while (true) {
            Format[] formatArr = this.formats;
            if (i2 >= formatArr.length) {
                return;
            }
            if (!strNormalizeLanguage.equals(normalizeLanguage(formatArr[i2].language))) {
                Format[] formatArr2 = this.formats;
                logErrorMessage("languages", formatArr2[0].language, formatArr2[i2].language, i2);
                return;
            } else {
                if (iNormalizeRoleFlags != normalizeRoleFlags(this.formats[i2].roleFlags)) {
                    logErrorMessage("role flags", Integer.toBinaryString(this.formats[0].roleFlags), Integer.toBinaryString(this.formats[i2].roleFlags), i2);
                    return;
                }
                i2++;
            }
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TrackGroup.class != obj.getClass()) {
            return false;
        }
        TrackGroup trackGroup = (TrackGroup) obj;
        return this.length == trackGroup.length && Arrays.equals(this.formats, trackGroup.formats);
    }

    public Format getFormat(int i2) {
        return this.formats[i2];
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = R2.attr.bl_checkable_gradient_endColor + Arrays.hashCode(this.formats);
        }
        return this.hashCode;
    }

    public int indexOf(Format format) {
        int i2 = 0;
        while (true) {
            Format[] formatArr = this.formats;
            if (i2 >= formatArr.length) {
                return -1;
            }
            if (format == formatArr[i2]) {
                return i2;
            }
            i2++;
        }
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(keyForField(0), BundleableUtil.toBundleArrayList(Lists.newArrayList(this.formats)));
        return bundle;
    }
}
