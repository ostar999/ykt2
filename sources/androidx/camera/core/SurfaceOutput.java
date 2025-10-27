package androidx.camera.core;

import android.util.Size;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.util.Consumer;
import com.google.auto.value.AutoValue;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public interface SurfaceOutput {

    @AutoValue
    public static abstract class Event {
        public static final int EVENT_REQUEST_CLOSE = 0;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public @interface EventCode {
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static Event of(int i2, @NonNull SurfaceOutput surfaceOutput) {
            return new AutoValue_SurfaceOutput_Event(i2, surfaceOutput);
        }

        public abstract int getEventCode();

        @NonNull
        public abstract SurfaceOutput getSurfaceOutput();
    }

    public enum GlTransformOptions {
        USE_SURFACE_TEXTURE_TRANSFORM,
        APPLY_CROP_ROTATE_AND_MIRRORING
    }

    void close();

    int getFormat();

    int getRotationDegrees();

    @NonNull
    Size getSize();

    @NonNull
    Surface getSurface(@NonNull Executor executor, @NonNull Consumer<Event> consumer);

    int getTargets();

    void updateTransformMatrix(@NonNull float[] fArr, @NonNull float[] fArr2);
}
