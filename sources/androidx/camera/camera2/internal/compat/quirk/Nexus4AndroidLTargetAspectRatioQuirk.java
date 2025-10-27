package androidx.camera.camera2.internal.compat.quirk;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.camera.core.impl.Quirk;
import java.util.Arrays;
import java.util.List;

@RequiresApi(21)
/* loaded from: classes.dex */
public class Nexus4AndroidLTargetAspectRatioQuirk implements Quirk {
    private static final List<String> DEVICE_MODELS = Arrays.asList("NEXUS 4");

    public static boolean load() {
        "GOOGLE".equalsIgnoreCase(Build.BRAND);
        return false;
    }

    public int getCorrectedAspectRatio() {
        return 2;
    }
}
