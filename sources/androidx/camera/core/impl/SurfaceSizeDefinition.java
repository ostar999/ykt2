package androidx.camera.core.impl;

import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.auto.value.AutoValue;

@AutoValue
@RequiresApi(21)
/* loaded from: classes.dex */
public abstract class SurfaceSizeDefinition {
    @NonNull
    public static SurfaceSizeDefinition create(@NonNull Size size, @NonNull Size size2, @NonNull Size size3) {
        return new AutoValue_SurfaceSizeDefinition(size, size2, size3);
    }

    @NonNull
    public abstract Size getAnalysisSize();

    @NonNull
    public abstract Size getPreviewSize();

    @NonNull
    public abstract Size getRecordSize();
}
