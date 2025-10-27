package com.caverock.androidsvg;

import android.graphics.Canvas;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
class CanvasLegacy {
    static final int MATRIX_SAVE_FLAG;
    private static final Method SAVE;

    static {
        try {
            MATRIX_SAVE_FLAG = ((Integer) Canvas.class.getField("MATRIX_SAVE_FLAG").get(null)).intValue();
            SAVE = Canvas.class.getMethod("save", Integer.TYPE);
        } catch (Throwable th) {
            throw sneakyThrow(th);
        }
    }

    public static void save(Canvas canvas, int i2) {
        try {
            SAVE.invoke(canvas, Integer.valueOf(i2));
        } catch (Throwable th) {
            throw sneakyThrow(th);
        }
    }

    private static RuntimeException sneakyThrow(Throwable th) {
        if (th != null) {
            return (RuntimeException) sneakyThrow0(th);
        }
        throw new NullPointerException("t");
    }

    private static <T extends Throwable> T sneakyThrow0(Throwable th) throws Throwable {
        throw th;
    }
}
