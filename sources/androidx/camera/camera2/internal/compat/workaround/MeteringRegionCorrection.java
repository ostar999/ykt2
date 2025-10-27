package androidx.camera.camera2.internal.compat.workaround;

import android.graphics.PointF;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.internal.compat.quirk.AfRegionFlipHorizontallyQuirk;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.impl.Quirks;

@RequiresApi(21)
/* loaded from: classes.dex */
public class MeteringRegionCorrection {
    private final Quirks mCameraQuirks;

    public MeteringRegionCorrection(@NonNull Quirks quirks) {
        this.mCameraQuirks = quirks;
    }

    @NonNull
    public PointF getCorrectedPoint(@NonNull MeteringPoint meteringPoint, int i2) {
        return (i2 == 1 && this.mCameraQuirks.contains(AfRegionFlipHorizontallyQuirk.class)) ? new PointF(1.0f - meteringPoint.getX(), meteringPoint.getY()) : new PointF(meteringPoint.getX(), meteringPoint.getY());
    }
}
