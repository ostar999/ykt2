package com.hyphenate.easeui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
class CanvasLegacy {
    static final int CLIP_SAVE_FLAG;
    static final int CLIP_TO_LAYER_SAVE_FLAG;
    static final int FULL_COLOR_LAYER_SAVE_FLAG;
    static final int HAS_ALPHA_LAYER_SAVE_FLAG;
    static final int MATRIX_SAVE_FLAG;
    private static final Method SAVE;

    static {
        try {
            MATRIX_SAVE_FLAG = ((Integer) Canvas.class.getField("MATRIX_SAVE_FLAG").get(null)).intValue();
            CLIP_SAVE_FLAG = ((Integer) Canvas.class.getField("CLIP_SAVE_FLAG").get(null)).intValue();
            HAS_ALPHA_LAYER_SAVE_FLAG = ((Integer) Canvas.class.getField("HAS_ALPHA_LAYER_SAVE_FLAG").get(null)).intValue();
            FULL_COLOR_LAYER_SAVE_FLAG = ((Integer) Canvas.class.getField("FULL_COLOR_LAYER_SAVE_FLAG").get(null)).intValue();
            CLIP_TO_LAYER_SAVE_FLAG = ((Integer) Canvas.class.getField("CLIP_TO_LAYER_SAVE_FLAG").get(null)).intValue();
            Class cls = Float.TYPE;
            SAVE = Canvas.class.getMethod("saveLayer", cls, cls, cls, cls, Paint.class, Integer.TYPE);
        } catch (Throwable th) {
            throw sneakyThrow(th);
        }
    }

    public static void saveLayer(Canvas canvas, float f2, float f3, float f4, float f5, @Nullable Paint paint, int i2) {
        try {
            SAVE.invoke(canvas, Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(f5), paint, Integer.valueOf(i2));
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
