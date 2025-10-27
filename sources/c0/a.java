package c0;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.func.Func;

/* loaded from: classes.dex */
public final /* synthetic */ class a<P, R> {
    public static Object a(Func func, Object... objArr) {
        try {
            return func.call(objArr);
        } catch (Exception e2) {
            throw ExceptionUtil.wrapRuntime(e2);
        }
    }
}
