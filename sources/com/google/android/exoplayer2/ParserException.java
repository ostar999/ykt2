package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import java.io.IOException;

/* loaded from: classes3.dex */
public class ParserException extends IOException {
    public final boolean contentIsMalformed;
    public final int dataType;

    public ParserException(@Nullable String str, @Nullable Throwable th, boolean z2, int i2) {
        super(str, th);
        this.contentIsMalformed = z2;
        this.dataType = i2;
    }

    public static ParserException createForMalformedContainer(@Nullable String str, @Nullable Throwable th) {
        return new ParserException(str, th, true, 1);
    }

    public static ParserException createForMalformedDataOfUnknownType(@Nullable String str, @Nullable Throwable th) {
        return new ParserException(str, th, true, 0);
    }

    public static ParserException createForMalformedManifest(@Nullable String str, @Nullable Throwable th) {
        return new ParserException(str, th, true, 4);
    }

    public static ParserException createForManifestWithUnsupportedFeature(@Nullable String str, @Nullable Throwable th) {
        return new ParserException(str, th, false, 4);
    }

    public static ParserException createForUnsupportedContainerFeature(@Nullable String str) {
        return new ParserException(str, null, false, 1);
    }
}
