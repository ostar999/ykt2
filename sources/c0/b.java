package c0;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.func.Func0;

/* loaded from: classes.dex */
public final /* synthetic */ class b<R> {
    public static Object a(Func0 func0) {
        try {
            return func0.call();
        } catch (Exception e2) {
            throw ExceptionUtil.wrapRuntime(e2);
        }
    }
}
