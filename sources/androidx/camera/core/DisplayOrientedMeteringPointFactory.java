package androidx.camera.core;

import android.graphics.PointF;
import android.view.Display;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.CameraInfoInternal;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class DisplayOrientedMeteringPointFactory extends MeteringPointFactory {

    @NonNull
    private final CameraInfo mCameraInfo;

    @NonNull
    private final Display mDisplay;
    private final float mHeight;
    private final float mWidth;

    public DisplayOrientedMeteringPointFactory(@NonNull Display display, @NonNull CameraInfo cameraInfo, float f2, float f3) {
        this.mWidth = f2;
        this.mHeight = f3;
        this.mDisplay = display;
        this.mCameraInfo = cameraInfo;
    }

    @Nullable
    private Integer getLensFacing() {
        CameraInfo cameraInfo = this.mCameraInfo;
        if (cameraInfo instanceof CameraInfoInternal) {
            return ((CameraInfoInternal) cameraInfo).getLensFacing();
        }
        return null;
    }

    private int getRelativeCameraOrientation(boolean z2) {
        try {
            int sensorRotationDegrees = this.mCameraInfo.getSensorRotationDegrees(this.mDisplay.getRotation());
            return z2 ? (360 - sensorRotationDegrees) % 360 : sensorRotationDegrees;
        } catch (Exception unused) {
            return 0;
        }
    }

    @Override // androidx.camera.core.MeteringPointFactory
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PointF convertPoint(float f2, float f3) {
        float f4 = this.mWidth;
        float f5 = this.mHeight;
        Integer lensFacing = getLensFacing();
        boolean z2 = lensFacing != null && lensFacing.intValue() == 0;
        int relativeCameraOrientation = getRelativeCameraOrientation(z2);
        if (relativeCameraOrientation != 90 && relativeCameraOrientation != 270) {
            f3 = f2;
            f2 = f3;
            f5 = f4;
            f4 = f5;
        }
        if (relativeCameraOrientation == 90) {
            f2 = f4 - f2;
        } else if (relativeCameraOrientation == 180) {
            f3 = f5 - f3;
            f2 = f4 - f2;
        } else if (relativeCameraOrientation == 270) {
            f3 = f5 - f3;
        }
        if (z2) {
            f3 = f5 - f3;
        }
        return new PointF(f3 / f5, f2 / f4);
    }
}
