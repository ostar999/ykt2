package c0;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.func.VoidFunc;

/* loaded from: classes.dex */
public final /* synthetic */ class o<P> {
    public static void a(VoidFunc voidFunc, Object... objArr) {
        try {
            voidFunc.call(objArr);
        } catch (Exception e2) {
            throw ExceptionUtil.wrapRuntime(e2);
        }
    }
}
