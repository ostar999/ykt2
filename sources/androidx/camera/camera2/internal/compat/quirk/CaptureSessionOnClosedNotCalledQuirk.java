package androidx.camera.camera2.internal.compat.quirk;

import androidx.annotation.RequiresApi;
import androidx.camera.core.impl.Quirk;

@RequiresApi(21)
/* loaded from: classes.dex */
public class CaptureSessionOnClosedNotCalledQuirk implements Quirk {
    public static boolean load() {
        return false;
    }
}
