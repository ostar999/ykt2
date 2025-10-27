package org.wrtca.api;

import javax.microedition.khronos.egl.EGLContext;
import org.wrtca.api.EglBase;
import org.wrtca.gl.EglBase10;
import org.wrtca.gl.EglBase14;

/* loaded from: classes9.dex */
public final /* synthetic */ class a {
    static {
        Object obj = EglBase.lock;
    }

    public static EglBase a() {
        return c(null, EglBase.CONFIG_PLAIN);
    }

    public static EglBase b(EglBase.Context context) {
        return c(context, EglBase.CONFIG_PLAIN);
    }

    public static EglBase c(EglBase.Context context, int[] iArr) {
        return (EglBase14.isEGL14Supported() && (context == null || (context instanceof EglBase14.Context))) ? new EglBase14((EglBase14.Context) context, iArr) : new EglBase10((EglBase10.Context) context, iArr);
    }

    public static EglBase d(EGLContext eGLContext, int[] iArr) {
        return new EglBase10(new EglBase10.Context(eGLContext), iArr);
    }

    public static EglBase e(int[] iArr) {
        return new EglBase10(null, iArr);
    }

    public static EglBase f(android.opengl.EGLContext eGLContext, int[] iArr) {
        return new EglBase14(new EglBase14.Context(eGLContext), iArr);
    }

    public static EglBase g(int[] iArr) {
        return new EglBase14(null, iArr);
    }
}
