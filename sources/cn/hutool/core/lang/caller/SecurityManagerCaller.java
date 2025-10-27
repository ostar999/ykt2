package cn.hutool.core.lang.caller;

import cn.hutool.core.util.ArrayUtil;
import java.io.Serializable;

/* loaded from: classes.dex */
public class SecurityManagerCaller extends SecurityManager implements Caller, Serializable {
    private static final int OFFSET = 1;
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.lang.caller.Caller
    public Class<?> getCaller() {
        Class<?>[] classContext = getClassContext();
        if (classContext == null || 2 >= classContext.length) {
            return null;
        }
        return classContext[2];
    }

    @Override // cn.hutool.core.lang.caller.Caller
    public Class<?> getCallerCaller() {
        Class<?>[] classContext = getClassContext();
        if (classContext == null || 3 >= classContext.length) {
            return null;
        }
        return classContext[3];
    }

    @Override // cn.hutool.core.lang.caller.Caller
    public boolean isCalledBy(Class<?> cls) {
        Class[] classContext = getClassContext();
        if (ArrayUtil.isNotEmpty((Object[]) classContext)) {
            for (Class cls2 : classContext) {
                if (cls2.equals(cls)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // cn.hutool.core.lang.caller.Caller
    public Class<?> getCaller(int i2) {
        int i3;
        Class<?>[] classContext = getClassContext();
        if (classContext == null || (i3 = i2 + 1) >= classContext.length) {
            return null;
        }
        return classContext[i3];
    }
}
