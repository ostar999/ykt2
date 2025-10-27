package androidx.camera.core.impl.utils;

import androidx.annotation.RequiresApi;
import androidx.camera.core.Logger;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class CameraOrientationUtil {
    private static final String TAG = "CameraOrientationUtil";

    private CameraOrientationUtil() {
    }

    public static int degreesToSurfaceRotation(int i2) {
        if (i2 == 0) {
            return 0;
        }
        if (i2 == 90) {
            return 1;
        }
        if (i2 == 180) {
            return 2;
        }
        if (i2 == 270) {
            return 3;
        }
        throw new IllegalStateException("Invalid sensor rotation: " + i2);
    }

    public static int getRelativeImageRotation(int i2, int i3, boolean z2) {
        int i4 = z2 ? ((i3 - i2) + 360) % 360 : (i3 + i2) % 360;
        if (Logger.isDebugEnabled(TAG)) {
            Logger.d(TAG, String.format("getRelativeImageRotation: destRotationDegrees=%s, sourceRotationDegrees=%s, isOppositeFacing=%s, result=%s", Integer.valueOf(i2), Integer.valueOf(i3), Boolean.valueOf(z2), Integer.valueOf(i4)));
        }
        return i4;
    }

    public static int surfaceRotationToDegrees(int i2) {
        if (i2 == 0) {
            return 0;
        }
        if (i2 == 1) {
            return 90;
        }
        if (i2 == 2) {
            return 180;
        }
        if (i2 == 3) {
            return 270;
        }
        throw new IllegalArgumentException("Unsupported surface rotation: " + i2);
    }
}
