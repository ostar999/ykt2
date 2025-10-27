package androidx.camera.core.processing;

import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
/* loaded from: classes.dex */
public abstract class SurfaceEdge {
    @NonNull
    public static SurfaceEdge create(@NonNull List<SettableSurface> list) {
        return new AutoValue_SurfaceEdge(list);
    }

    @NonNull
    public abstract List<SettableSurface> getSurfaces();
}
