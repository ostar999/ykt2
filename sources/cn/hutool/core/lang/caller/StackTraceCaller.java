package cn.hutool.core.lang.caller;

import cn.hutool.core.exceptions.UtilException;
import java.io.Serializable;

/* loaded from: classes.dex */
public class StackTraceCaller implements Caller, Serializable {
    private static final int OFFSET = 2;
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.lang.caller.Caller
    public Class<?> getCaller() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (3 >= stackTrace.length) {
            return null;
        }
        String className = stackTrace[3].getClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e2) {
            throw new UtilException(e2, "[{}] not found!", className);
        }
    }

    @Override // cn.hutool.core.lang.caller.Caller
    public Class<?> getCallerCaller() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (4 >= stackTrace.length) {
            return null;
        }
        String className = stackTrace[4].getClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e2) {
            throw new UtilException(e2, "[{}] not found!", className);
        }
    }

    @Override // cn.hutool.core.lang.caller.Caller
    public boolean isCalledBy(Class<?> cls) {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (stackTraceElement.getClassName().equals(cls.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override // cn.hutool.core.lang.caller.Caller
    public Class<?> getCaller(int i2) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int i3 = i2 + 2;
        if (i3 >= stackTrace.length) {
            return null;
        }
        String className = stackTrace[i3].getClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e2) {
            throw new UtilException(e2, "[{}] not found!", className);
        }
    }
}
