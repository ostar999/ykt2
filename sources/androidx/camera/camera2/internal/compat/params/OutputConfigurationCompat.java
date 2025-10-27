package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import android.os.Build;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.camera.camera2.internal.compat.ApiCompat;
import java.util.List;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class OutputConfigurationCompat {
    public static final int STREAM_USE_CASE_NONE = -1;
    public static final int SURFACE_GROUP_ID_NONE = -1;
    private final OutputConfigurationCompatImpl mImpl;

    public interface OutputConfigurationCompatImpl {
        void addSurface(@NonNull Surface surface);

        void enableSurfaceSharing();

        int getMaxSharedSurfaceCount();

        @Nullable
        Object getOutputConfiguration();

        @Nullable
        String getPhysicalCameraId();

        long getStreamUseCase();

        @Nullable
        Surface getSurface();

        int getSurfaceGroupId();

        List<Surface> getSurfaces();

        void removeSurface(@NonNull Surface surface);

        void setPhysicalCameraId(@Nullable String str);

        void setStreamUseCase(long j2);
    }

    public OutputConfigurationCompat(@NonNull Surface surface) {
        this(-1, surface);
    }

    @Nullable
    public static OutputConfigurationCompat wrap(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        int i2 = Build.VERSION.SDK_INT;
        OutputConfigurationCompatImpl outputConfigurationCompatImplWrap = i2 >= 33 ? OutputConfigurationCompatApi33Impl.wrap((OutputConfiguration) obj) : i2 >= 28 ? OutputConfigurationCompatApi28Impl.wrap((OutputConfiguration) obj) : i2 >= 26 ? OutputConfigurationCompatApi26Impl.wrap((OutputConfiguration) obj) : i2 >= 24 ? OutputConfigurationCompatApi24Impl.wrap((OutputConfiguration) obj) : null;
        if (outputConfigurationCompatImplWrap == null) {
            return null;
        }
        return new OutputConfigurationCompat(outputConfigurationCompatImplWrap);
    }

    public void addSurface(@NonNull Surface surface) {
        this.mImpl.addSurface(surface);
    }

    public void enableSurfaceSharing() {
        this.mImpl.enableSurfaceSharing();
    }

    public boolean equals(Object obj) {
        if (obj instanceof OutputConfigurationCompat) {
            return this.mImpl.equals(((OutputConfigurationCompat) obj).mImpl);
        }
        return false;
    }

    public int getMaxSharedSurfaceCount() {
        return this.mImpl.getMaxSharedSurfaceCount();
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String getPhysicalCameraId() {
        return this.mImpl.getPhysicalCameraId();
    }

    public long getStreamUseCase() {
        return this.mImpl.getStreamUseCase();
    }

    @Nullable
    public Surface getSurface() {
        return this.mImpl.getSurface();
    }

    public int getSurfaceGroupId() {
        return this.mImpl.getSurfaceGroupId();
    }

    @NonNull
    public List<Surface> getSurfaces() {
        return this.mImpl.getSurfaces();
    }

    public int hashCode() {
        return this.mImpl.hashCode();
    }

    public void removeSurface(@NonNull Surface surface) {
        this.mImpl.removeSurface(surface);
    }

    public void setPhysicalCameraId(@Nullable String str) {
        this.mImpl.setPhysicalCameraId(str);
    }

    public void setStreamUseCase(long j2) {
        this.mImpl.setStreamUseCase(j2);
    }

    @Nullable
    public Object unwrap() {
        return this.mImpl.getOutputConfiguration();
    }

    public OutputConfigurationCompat(int i2, @NonNull Surface surface) {
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 33) {
            this.mImpl = new OutputConfigurationCompatApi33Impl(i2, surface);
            return;
        }
        if (i3 >= 28) {
            this.mImpl = new OutputConfigurationCompatApi28Impl(i2, surface);
            return;
        }
        if (i3 >= 26) {
            this.mImpl = new OutputConfigurationCompatApi26Impl(i2, surface);
        } else if (i3 >= 24) {
            this.mImpl = new OutputConfigurationCompatApi24Impl(i2, surface);
        } else {
            this.mImpl = new OutputConfigurationCompatBaseImpl(surface);
        }
    }

    @RequiresApi(26)
    public <T> OutputConfigurationCompat(@NonNull Size size, @NonNull Class<T> cls) {
        OutputConfiguration outputConfigurationNewOutputConfiguration = ApiCompat.Api26Impl.newOutputConfiguration(size, cls);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 33) {
            this.mImpl = OutputConfigurationCompatApi33Impl.wrap(outputConfigurationNewOutputConfiguration);
        } else if (i2 >= 28) {
            this.mImpl = OutputConfigurationCompatApi28Impl.wrap(outputConfigurationNewOutputConfiguration);
        } else {
            this.mImpl = OutputConfigurationCompatApi26Impl.wrap(outputConfigurationNewOutputConfiguration);
        }
    }

    private OutputConfigurationCompat(@NonNull OutputConfigurationCompatImpl outputConfigurationCompatImpl) {
        this.mImpl = outputConfigurationCompatImpl;
    }
}
