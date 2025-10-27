package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(21)
/* loaded from: classes.dex */
public class LensFacingConverter {
    private LensFacingConverter() {
    }

    @NonNull
    public static String nameOf(int i2) {
        if (i2 == 0) {
            return "FRONT";
        }
        if (i2 == 1) {
            return "BACK";
        }
        throw new IllegalArgumentException("Unknown lens facing " + i2);
    }

    public static int valueOf(@Nullable String str) {
        if (str == null) {
            throw new NullPointerException("name cannot be null");
        }
        if (str.equals("BACK")) {
            return 1;
        }
        if (str.equals("FRONT")) {
            return 0;
        }
        throw new IllegalArgumentException("Unknown len facing name " + str);
    }

    @NonNull
    public static Integer[] values() {
        return new Integer[]{0, 1};
    }
}
