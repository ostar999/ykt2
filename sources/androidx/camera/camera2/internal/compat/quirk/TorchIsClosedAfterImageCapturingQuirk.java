package androidx.camera.camera2.internal.compat.quirk;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.camera.core.impl.Quirk;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RequiresApi(21)
/* loaded from: classes.dex */
public class TorchIsClosedAfterImageCapturingQuirk implements Quirk {
    public static final List<String> BUILD_MODELS = Arrays.asList("mi a1", "mi a2", "mi a2 lite", "redmi 4x", "redmi 5a", "redmi 6 pro");

    public static boolean load() {
        return BUILD_MODELS.contains(Build.MODEL.toLowerCase(Locale.US));
    }
}
