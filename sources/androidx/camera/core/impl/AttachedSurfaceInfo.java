package androidx.camera.core.impl;

import android.util.Range;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.auto.value.AutoValue;

@AutoValue
@RequiresApi(21)
/* loaded from: classes.dex */
public abstract class AttachedSurfaceInfo {
    @NonNull
    public static AttachedSurfaceInfo create(@NonNull SurfaceConfig surfaceConfig, int i2, @NonNull Size size, @Nullable Range<Integer> range) {
        return new AutoValue_AttachedSurfaceInfo(surfaceConfig, i2, size, range);
    }

    public abstract int getImageFormat();

    @NonNull
    public abstract Size getSize();

    @NonNull
    public abstract SurfaceConfig getSurfaceConfig();

    @Nullable
    public abstract Range<Integer> getTargetFrameRate();
}
