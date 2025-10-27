package androidx.camera.camera2.internal.compat.workaround;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.internal.compat.quirk.CrashWhenTakingPhotoWithAutoFlashAEModeQuirk;
import androidx.camera.camera2.internal.compat.quirk.DeviceQuirks;
import androidx.camera.camera2.internal.compat.quirk.ImageCaptureFailWithAutoFlashQuirk;
import androidx.camera.core.impl.Quirks;

@RequiresApi(21)
/* loaded from: classes.dex */
public class AutoFlashAEModeDisabler {
    private final boolean mIsCrashWhenTakingPhotoWithAutoFlashAEModeQuirkEnabled;
    private final boolean mIsImageCaptureFailWithAutoFlashQuirkEnabled;

    public AutoFlashAEModeDisabler(@NonNull Quirks quirks) {
        this.mIsImageCaptureFailWithAutoFlashQuirkEnabled = quirks.contains(ImageCaptureFailWithAutoFlashQuirk.class);
        this.mIsCrashWhenTakingPhotoWithAutoFlashAEModeQuirkEnabled = DeviceQuirks.get(CrashWhenTakingPhotoWithAutoFlashAEModeQuirk.class) != null;
    }

    public int getCorrectedAeMode(int i2) {
        if ((this.mIsImageCaptureFailWithAutoFlashQuirkEnabled || this.mIsCrashWhenTakingPhotoWithAutoFlashAEModeQuirkEnabled) && i2 == 2) {
            return 1;
        }
        return i2;
    }
}
