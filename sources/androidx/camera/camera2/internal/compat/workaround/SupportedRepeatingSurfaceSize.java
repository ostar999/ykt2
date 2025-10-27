package androidx.camera.camera2.internal.compat.workaround;

import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.internal.compat.quirk.DeviceQuirks;
import androidx.camera.camera2.internal.compat.quirk.RepeatingStreamConstraintForVideoRecordingQuirk;
import androidx.camera.core.impl.utils.CompareSizesByArea;
import java.util.ArrayList;
import java.util.Comparator;

@RequiresApi(21)
/* loaded from: classes.dex */
public class SupportedRepeatingSurfaceSize {
    private static final Size MINI_PREVIEW_SIZE_HUAWEI_MATE_9 = new Size(320, 240);
    private static final Comparator<Size> SIZE_COMPARATOR = new CompareSizesByArea();

    @Nullable
    private final RepeatingStreamConstraintForVideoRecordingQuirk mQuirk = (RepeatingStreamConstraintForVideoRecordingQuirk) DeviceQuirks.get(RepeatingStreamConstraintForVideoRecordingQuirk.class);

    @NonNull
    public Size[] getSupportedSizes(@NonNull Size[] sizeArr) {
        if (this.mQuirk == null || !RepeatingStreamConstraintForVideoRecordingQuirk.isHuaweiMate9()) {
            return sizeArr;
        }
        ArrayList arrayList = new ArrayList();
        for (Size size : sizeArr) {
            if (SIZE_COMPARATOR.compare(size, MINI_PREVIEW_SIZE_HUAWEI_MATE_9) >= 0) {
                arrayList.add(size);
            }
        }
        return (Size[]) arrayList.toArray(new Size[0]);
    }
}
