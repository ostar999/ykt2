package com.google.android.exoplayer2;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.source.MediaPeriodId;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.BundleableUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class ExoPlaybackException extends PlaybackException {
    public static final Bundleable.Creator<ExoPlaybackException> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.f
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return ExoPlaybackException.a(bundle);
        }
    };
    private static final int FIELD_IS_RECOVERABLE = 1006;
    private static final int FIELD_RENDERER_FORMAT = 1004;
    private static final int FIELD_RENDERER_FORMAT_SUPPORT = 1005;
    private static final int FIELD_RENDERER_INDEX = 1003;
    private static final int FIELD_RENDERER_NAME = 1002;
    private static final int FIELD_TYPE = 1001;
    public static final int TYPE_REMOTE = 3;
    public static final int TYPE_RENDERER = 1;
    public static final int TYPE_SOURCE = 0;
    public static final int TYPE_UNEXPECTED = 2;
    final boolean isRecoverable;

    @Nullable
    public final MediaPeriodId mediaPeriodId;

    @Nullable
    public final Format rendererFormat;
    public final int rendererFormatSupport;
    public final int rendererIndex;

    @Nullable
    public final String rendererName;
    public final int type;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private ExoPlaybackException(int i2, Throwable th, int i3) {
        this(i2, th, null, i3, null, -1, null, 4, false);
    }

    public static /* synthetic */ ExoPlaybackException a(Bundle bundle) {
        return new ExoPlaybackException(bundle);
    }

    public static ExoPlaybackException createForRemote(String str) {
        return new ExoPlaybackException(3, null, str, 1001, null, -1, null, 4, false);
    }

    public static ExoPlaybackException createForRenderer(Throwable th, String str, int i2, @Nullable Format format, int i3, boolean z2, int i4) {
        return new ExoPlaybackException(1, th, null, i4, str, i2, format, format == null ? 4 : i3, z2);
    }

    public static ExoPlaybackException createForSource(IOException iOException, int i2) {
        return new ExoPlaybackException(0, iOException, i2);
    }

    @Deprecated
    public static ExoPlaybackException createForUnexpected(RuntimeException runtimeException) {
        return createForUnexpected(runtimeException, 1000);
    }

    private static String deriveMessage(int i2, @Nullable String str, @Nullable String str2, int i3, @Nullable Format format, int i4) {
        String string;
        if (i2 == 0) {
            string = "Source error";
        } else if (i2 != 1) {
            string = i2 != 3 ? "Unexpected runtime error" : "Remote error";
        } else {
            String strValueOf = String.valueOf(format);
            String formatSupportString = Util.getFormatSupportString(i4);
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 53 + strValueOf.length() + String.valueOf(formatSupportString).length());
            sb.append(str2);
            sb.append(" error, index=");
            sb.append(i3);
            sb.append(", format=");
            sb.append(strValueOf);
            sb.append(", format_supported=");
            sb.append(formatSupportString);
            string = sb.toString();
        }
        if (TextUtils.isEmpty(str)) {
            return string;
        }
        String strValueOf2 = String.valueOf(string);
        StringBuilder sb2 = new StringBuilder(strValueOf2.length() + 2 + String.valueOf(str).length());
        sb2.append(strValueOf2);
        sb2.append(": ");
        sb2.append(str);
        return sb2.toString();
    }

    @CheckResult
    public ExoPlaybackException copyWithMediaPeriodId(@Nullable MediaPeriodId mediaPeriodId) {
        return new ExoPlaybackException((String) Util.castNonNull(getMessage()), getCause(), this.errorCode, this.type, this.rendererName, this.rendererIndex, this.rendererFormat, this.rendererFormatSupport, mediaPeriodId, this.timestampMs, this.isRecoverable);
    }

    @Override // com.google.android.exoplayer2.PlaybackException
    public boolean errorInfoEquals(@Nullable PlaybackException playbackException) {
        if (!super.errorInfoEquals(playbackException)) {
            return false;
        }
        ExoPlaybackException exoPlaybackException = (ExoPlaybackException) Util.castNonNull(playbackException);
        return this.type == exoPlaybackException.type && Util.areEqual(this.rendererName, exoPlaybackException.rendererName) && this.rendererIndex == exoPlaybackException.rendererIndex && Util.areEqual(this.rendererFormat, exoPlaybackException.rendererFormat) && this.rendererFormatSupport == exoPlaybackException.rendererFormatSupport && Util.areEqual(this.mediaPeriodId, exoPlaybackException.mediaPeriodId) && this.isRecoverable == exoPlaybackException.isRecoverable;
    }

    public Exception getRendererException() {
        Assertions.checkState(this.type == 1);
        return (Exception) Assertions.checkNotNull(getCause());
    }

    public IOException getSourceException() {
        Assertions.checkState(this.type == 0);
        return (IOException) Assertions.checkNotNull(getCause());
    }

    public RuntimeException getUnexpectedException() {
        Assertions.checkState(this.type == 2);
        return (RuntimeException) Assertions.checkNotNull(getCause());
    }

    @Override // com.google.android.exoplayer2.PlaybackException, com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = super.toBundle();
        bundle.putInt(PlaybackException.keyForField(1001), this.type);
        bundle.putString(PlaybackException.keyForField(1002), this.rendererName);
        bundle.putInt(PlaybackException.keyForField(1003), this.rendererIndex);
        bundle.putBundle(PlaybackException.keyForField(1004), BundleableUtil.toNullableBundle(this.rendererFormat));
        bundle.putInt(PlaybackException.keyForField(1005), this.rendererFormatSupport);
        bundle.putBoolean(PlaybackException.keyForField(1006), this.isRecoverable);
        return bundle;
    }

    private ExoPlaybackException(int i2, @Nullable Throwable th, @Nullable String str, int i3, @Nullable String str2, int i4, @Nullable Format format, int i5, boolean z2) {
        this(deriveMessage(i2, str, str2, i4, format, i5), th, i3, i2, str2, i4, format, i5, null, SystemClock.elapsedRealtime(), z2);
    }

    public static ExoPlaybackException createForUnexpected(RuntimeException runtimeException, int i2) {
        return new ExoPlaybackException(2, runtimeException, i2);
    }

    private ExoPlaybackException(Bundle bundle) {
        super(bundle);
        this.type = bundle.getInt(PlaybackException.keyForField(1001), 2);
        this.rendererName = bundle.getString(PlaybackException.keyForField(1002));
        this.rendererIndex = bundle.getInt(PlaybackException.keyForField(1003), -1);
        this.rendererFormat = (Format) BundleableUtil.fromNullableBundle(Format.CREATOR, bundle.getBundle(PlaybackException.keyForField(1004)));
        this.rendererFormatSupport = bundle.getInt(PlaybackException.keyForField(1005), 4);
        this.isRecoverable = bundle.getBoolean(PlaybackException.keyForField(1006), false);
        this.mediaPeriodId = null;
    }

    private ExoPlaybackException(String str, @Nullable Throwable th, int i2, int i3, @Nullable String str2, int i4, @Nullable Format format, int i5, @Nullable MediaPeriodId mediaPeriodId, long j2, boolean z2) {
        super(str, th, i2, j2);
        Assertions.checkArgument(!z2 || i3 == 1);
        Assertions.checkArgument(th != null || i3 == 3);
        this.type = i3;
        this.rendererName = str2;
        this.rendererIndex = i4;
        this.rendererFormat = format;
        this.rendererFormatSupport = i5;
        this.mediaPeriodId = mediaPeriodId;
        this.isRecoverable = z2;
    }
}
