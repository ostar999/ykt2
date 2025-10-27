package com.google.android.exoplayer2.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class RepeatModeUtil {
    public static final int REPEAT_TOGGLE_MODE_ALL = 2;
    public static final int REPEAT_TOGGLE_MODE_NONE = 0;
    public static final int REPEAT_TOGGLE_MODE_ONE = 1;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatToggleModes {
    }

    private RepeatModeUtil() {
    }

    public static int getNextRepeatMode(int i2, int i3) {
        for (int i4 = 1; i4 <= 2; i4++) {
            int i5 = (i2 + i4) % 3;
            if (isRepeatModeEnabled(i5, i3)) {
                return i5;
            }
        }
        return i2;
    }

    public static boolean isRepeatModeEnabled(int i2, int i3) {
        if (i2 != 0) {
            return i2 != 1 ? i2 == 2 && (i3 & 2) != 0 : (i3 & 1) != 0;
        }
        return true;
    }
}
