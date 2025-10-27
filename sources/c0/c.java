package c0;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.func.Func1;

/* loaded from: classes.dex */
public final /* synthetic */ class c<P, R> {
    public static Object a(Func1 func1, Object obj) {
        try {
            return func1.call(obj);
        } catch (Exception e2) {
            throw ExceptionUtil.wrapRuntime(e2);
        }
    }
}
