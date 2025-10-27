package com.google.android.exoplayer2.drm;

import android.media.DeniedByServerException;
import android.media.MediaDrm;
import android.media.MediaDrmResetException;
import android.media.NotProvisionedException;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class DrmUtil {
    public static final int ERROR_SOURCE_EXO_MEDIA_DRM = 1;
    public static final int ERROR_SOURCE_LICENSE_ACQUISITION = 2;
    public static final int ERROR_SOURCE_PROVISIONING = 3;

    @RequiresApi(18)
    public static final class Api18 {
        private Api18() {
        }

        @DoNotInline
        public static boolean isDeniedByServerException(@Nullable Throwable th) {
            return th instanceof DeniedByServerException;
        }

        @DoNotInline
        public static boolean isNotProvisionedException(@Nullable Throwable th) {
            return th instanceof NotProvisionedException;
        }
    }

    @RequiresApi(21)
    public static final class Api21 {
        private Api21() {
        }

        @DoNotInline
        public static boolean isMediaDrmStateException(@Nullable Throwable th) {
            return th instanceof MediaDrm.MediaDrmStateException;
        }

        @DoNotInline
        public static int mediaDrmStateExceptionToErrorCode(Throwable th) {
            return Util.getErrorCodeForMediaDrmErrorCode(Util.getErrorCodeFromPlatformDiagnosticsInfo(((MediaDrm.MediaDrmStateException) th).getDiagnosticInfo()));
        }
    }

    @RequiresApi(23)
    public static final class Api23 {
        private Api23() {
        }

        @DoNotInline
        public static boolean isMediaDrmResetException(@Nullable Throwable th) {
            return th instanceof MediaDrmResetException;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorSource {
    }

    private DrmUtil() {
    }

    public static int getErrorCodeForMediaDrmException(Exception exc, int i2) {
        int i3 = Util.SDK_INT;
        if (i3 >= 21 && Api21.isMediaDrmStateException(exc)) {
            return Api21.mediaDrmStateExceptionToErrorCode(exc);
        }
        if (i3 >= 23 && Api23.isMediaDrmResetException(exc)) {
            return 6006;
        }
        if (i3 >= 18 && Api18.isNotProvisionedException(exc)) {
            return 6002;
        }
        if (i3 >= 18 && Api18.isDeniedByServerException(exc)) {
            return 6007;
        }
        if (exc instanceof UnsupportedDrmException) {
            return 6001;
        }
        if (exc instanceof DefaultDrmSessionManager.MissingSchemeDataException) {
            return 6003;
        }
        if (exc instanceof KeysExpiredException) {
            return 6008;
        }
        if (i2 == 1) {
            return 6006;
        }
        if (i2 == 2) {
            return 6004;
        }
        if (i2 == 3) {
            return 6002;
        }
        throw new IllegalArgumentException();
    }
}
