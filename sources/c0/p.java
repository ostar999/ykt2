package c0;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.func.VoidFunc0;

/* loaded from: classes.dex */
public final /* synthetic */ class p {
    public static void a(VoidFunc0 voidFunc0) {
        try {
            voidFunc0.call();
        } catch (Exception e2) {
            throw ExceptionUtil.wrapRuntime(e2);
        }
    }
}
