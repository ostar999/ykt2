package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RequiresApi(21)
/* loaded from: classes.dex */
public class CameraAccessExceptionCompat extends Exception {
    public static final int CAMERA_CHARACTERISTICS_CREATION_ERROR = 10002;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int CAMERA_DEPRECATED_HAL = 1000;
    public static final int CAMERA_DISABLED = 1;
    public static final int CAMERA_DISCONNECTED = 2;
    public static final int CAMERA_ERROR = 3;
    public static final int CAMERA_IN_USE = 4;
    public static final int CAMERA_UNAVAILABLE_DO_NOT_DISTURB = 10001;
    public static final int MAX_CAMERAS_IN_USE = 5;
    private final CameraAccessException mCameraAccessException;
    private final int mReason;

    @VisibleForTesting
    static final Set<Integer> PLATFORM_ERRORS = Collections.unmodifiableSet(new HashSet(Arrays.asList(4, 5, 1, 2, 3)));

    @VisibleForTesting
    static final Set<Integer> COMPAT_ERRORS = Collections.unmodifiableSet(new HashSet(Arrays.asList(10001, 10002)));

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface AccessError {
    }

    public CameraAccessExceptionCompat(int i2) {
        super(getDefaultMessage(i2));
        this.mReason = i2;
        this.mCameraAccessException = PLATFORM_ERRORS.contains(Integer.valueOf(i2)) ? new CameraAccessException(i2) : null;
    }

    private static String getCombinedMessage(int i2, String str) {
        return String.format("%s (%d): %s", getProblemString(i2), Integer.valueOf(i2), str);
    }

    @Nullable
    private static String getDefaultMessage(int i2) {
        if (i2 == 1) {
            return "The camera is disabled due to a device policy, and cannot be opened.";
        }
        if (i2 == 2) {
            return "The camera device is removable and has been disconnected from the Android device, or the camera service has shut down the connection due to a higher-priority access request for the camera device.";
        }
        if (i2 == 3) {
            return "The camera device is currently in the error state; no further calls to it will succeed.";
        }
        if (i2 == 4) {
            return "The camera device is in use already";
        }
        if (i2 == 5) {
            return "The system-wide limit for number of open cameras has been reached, and more camera devices cannot be opened until previous instances are closed.";
        }
        if (i2 == 10001) {
            return "Some API 28 devices cannot access the camera when the device is in \"Do Not Disturb\" mode. The camera will not be accessible until \"Do Not Disturb\" mode is disabled.";
        }
        if (i2 != 10002) {
            return null;
        }
        return "Failed to create CameraCharacteristics.";
    }

    @NonNull
    private static String getProblemString(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? i2 != 1000 ? i2 != 10001 ? i2 != 10002 ? "<UNKNOWN ERROR>" : "CAMERA_CHARACTERISTICS_CREATION_ERROR" : "CAMERA_UNAVAILABLE_DO_NOT_DISTURB" : "CAMERA_DEPRECATED_HAL" : "MAX_CAMERAS_IN_USE" : "CAMERA_IN_USE" : "CAMERA_ERROR" : "CAMERA_DISCONNECTED" : "CAMERA_DISABLED";
    }

    @NonNull
    public static CameraAccessExceptionCompat toCameraAccessExceptionCompat(@NonNull CameraAccessException cameraAccessException) {
        if (cameraAccessException != null) {
            return new CameraAccessExceptionCompat(cameraAccessException);
        }
        throw new NullPointerException("cameraAccessException should not be null");
    }

    public final int getReason() {
        return this.mReason;
    }

    @Nullable
    public CameraAccessException toCameraAccessException() {
        return this.mCameraAccessException;
    }

    public CameraAccessExceptionCompat(int i2, @Nullable String str) {
        super(getCombinedMessage(i2, str));
        this.mReason = i2;
        this.mCameraAccessException = PLATFORM_ERRORS.contains(Integer.valueOf(i2)) ? new CameraAccessException(i2, str) : null;
    }

    public CameraAccessExceptionCompat(int i2, @Nullable String str, @Nullable Throwable th) {
        super(getCombinedMessage(i2, str), th);
        this.mReason = i2;
        this.mCameraAccessException = PLATFORM_ERRORS.contains(Integer.valueOf(i2)) ? new CameraAccessException(i2, str, th) : null;
    }

    public CameraAccessExceptionCompat(int i2, @Nullable Throwable th) {
        super(getDefaultMessage(i2), th);
        this.mReason = i2;
        this.mCameraAccessException = PLATFORM_ERRORS.contains(Integer.valueOf(i2)) ? new CameraAccessException(i2, null, th) : null;
    }

    private CameraAccessExceptionCompat(@NonNull CameraAccessException cameraAccessException) {
        super(cameraAccessException.getMessage(), cameraAccessException.getCause());
        this.mReason = cameraAccessException.getReason();
        this.mCameraAccessException = cameraAccessException;
    }
}
