package androidx.camera.camera2.internal.compat.workaround;

import android.hardware.camera2.CaptureRequest;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.internal.compat.quirk.DeviceQuirks;
import androidx.camera.camera2.internal.compat.quirk.StillCaptureFlashStopRepeatingQuirk;
import java.util.Iterator;
import java.util.List;

@RequiresApi(21)
/* loaded from: classes.dex */
public class StillCaptureFlow {
    private final boolean mShouldStopRepeatingBeforeStillCapture;

    public StillCaptureFlow() {
        this.mShouldStopRepeatingBeforeStillCapture = ((StillCaptureFlashStopRepeatingQuirk) DeviceQuirks.get(StillCaptureFlashStopRepeatingQuirk.class)) != null;
    }

    public boolean shouldStopRepeatingBeforeCapture(@NonNull List<CaptureRequest> list, boolean z2) {
        if (this.mShouldStopRepeatingBeforeStillCapture && z2) {
            Iterator<CaptureRequest> it = list.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Integer) it.next().get(CaptureRequest.CONTROL_AE_MODE)).intValue();
                if (iIntValue == 2 || iIntValue == 3) {
                    return true;
                }
            }
        }
        return false;
    }
}
