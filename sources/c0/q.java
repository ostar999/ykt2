package c0;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.func.VoidFunc1;

/* loaded from: classes.dex */
public final /* synthetic */ class q<P> {
    public static void a(VoidFunc1 voidFunc1, Object obj) {
        try {
            voidFunc1.call(obj);
        } catch (Exception e2) {
            throw ExceptionUtil.wrapRuntime(e2);
        }
    }
}
