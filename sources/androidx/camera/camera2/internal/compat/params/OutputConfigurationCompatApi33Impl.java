package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.List;

@RequiresApi(33)
/* loaded from: classes.dex */
public class OutputConfigurationCompatApi33Impl extends OutputConfigurationCompatApi28Impl {
    public OutputConfigurationCompatApi33Impl(@NonNull Surface surface) {
        super(new OutputConfiguration(surface));
    }

    @RequiresApi(33)
    public static OutputConfigurationCompatApi33Impl wrap(@NonNull OutputConfiguration outputConfiguration) {
        return new OutputConfigurationCompatApi33Impl(outputConfiguration);
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi26Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    public /* bridge */ /* synthetic */ void addSurface(@NonNull Surface surface) {
        super.addSurface(surface);
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi26Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi24Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    public /* bridge */ /* synthetic */ void enableSurfaceSharing() {
        super.enableSurfaceSharing();
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi28Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi26Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    public /* bridge */ /* synthetic */ int getMaxSharedSurfaceCount() {
        return super.getMaxSharedSurfaceCount();
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi28Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi26Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi24Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    @NonNull
    public /* bridge */ /* synthetic */ Object getOutputConfiguration() {
        return super.getOutputConfiguration();
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi28Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi26Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi24Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    @Nullable
    public /* bridge */ /* synthetic */ String getPhysicalCameraId() {
        return super.getPhysicalCameraId();
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    public long getStreamUseCase() {
        return ((OutputConfiguration) getOutputConfiguration()).getStreamUseCase();
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi24Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    @Nullable
    public /* bridge */ /* synthetic */ Surface getSurface() {
        return super.getSurface();
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi24Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    public /* bridge */ /* synthetic */ int getSurfaceGroupId() {
        return super.getSurfaceGroupId();
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi26Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi24Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    @NonNull
    public /* bridge */ /* synthetic */ List getSurfaces() {
        return super.getSurfaces();
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi28Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi26Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    public /* bridge */ /* synthetic */ void removeSurface(@NonNull Surface surface) {
        super.removeSurface(surface);
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi28Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi26Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatApi24Impl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    public /* bridge */ /* synthetic */ void setPhysicalCameraId(@Nullable String str) {
        super.setPhysicalCameraId(str);
    }

    @Override // androidx.camera.camera2.internal.compat.params.OutputConfigurationCompatBaseImpl, androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat.OutputConfigurationCompatImpl
    public void setStreamUseCase(long j2) {
        if (j2 == -1) {
            return;
        }
        ((OutputConfiguration) getOutputConfiguration()).setStreamUseCase(j2);
    }

    public OutputConfigurationCompatApi33Impl(int i2, @NonNull Surface surface) {
        this(new OutputConfiguration(i2, surface));
    }

    public OutputConfigurationCompatApi33Impl(@NonNull Object obj) {
        super(obj);
    }
}
