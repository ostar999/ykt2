package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.UriUtil;
import com.yikaobang.yixue.R2;

/* loaded from: classes3.dex */
public final class RangedUri {
    private int hashCode;
    public final long length;
    private final String referenceUri;
    public final long start;

    public RangedUri(@Nullable String str, long j2, long j3) {
        this.referenceUri = str == null ? "" : str;
        this.start = j2;
        this.length = j3;
    }

    @Nullable
    public RangedUri attemptMerge(@Nullable RangedUri rangedUri, String str) {
        String strResolveUriString = resolveUriString(str);
        if (rangedUri != null && strResolveUriString.equals(rangedUri.resolveUriString(str))) {
            long j2 = this.length;
            if (j2 != -1) {
                long j3 = this.start;
                if (j3 + j2 == rangedUri.start) {
                    long j4 = rangedUri.length;
                    return new RangedUri(strResolveUriString, j3, j4 != -1 ? j2 + j4 : -1L);
                }
            }
            long j5 = rangedUri.length;
            if (j5 != -1) {
                long j6 = rangedUri.start;
                if (j6 + j5 == this.start) {
                    return new RangedUri(strResolveUriString, j6, j2 != -1 ? j5 + j2 : -1L);
                }
            }
        }
        return null;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || RangedUri.class != obj.getClass()) {
            return false;
        }
        RangedUri rangedUri = (RangedUri) obj;
        return this.start == rangedUri.start && this.length == rangedUri.length && this.referenceUri.equals(rangedUri.referenceUri);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = ((((R2.attr.bl_checkable_gradient_endColor + ((int) this.start)) * 31) + ((int) this.length)) * 31) + this.referenceUri.hashCode();
        }
        return this.hashCode;
    }

    public Uri resolveUri(String str) {
        return UriUtil.resolveToUri(str, this.referenceUri);
    }

    public String resolveUriString(String str) {
        return UriUtil.resolve(str, this.referenceUri);
    }

    public String toString() {
        String str = this.referenceUri;
        long j2 = this.start;
        long j3 = this.length;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 81);
        sb.append("RangedUri(referenceUri=");
        sb.append(str);
        sb.append(", start=");
        sb.append(j2);
        sb.append(", length=");
        sb.append(j3);
        sb.append(")");
        return sb.toString();
    }
}
