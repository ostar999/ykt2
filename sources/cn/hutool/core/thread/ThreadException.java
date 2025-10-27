package cn.hutool.core.thread;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class ThreadException extends RuntimeException {
    private static final long serialVersionUID = 5253124428623713216L;

    public ThreadException(Throwable th) {
        super(ExceptionUtil.getMessage(th), th);
    }

    public ThreadException(String str) {
        super(str);
    }

    public ThreadException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public ThreadException(String str, Throwable th) {
        super(str, th);
    }

    public ThreadException(String str, Throwable th, boolean z2, boolean z3) {
        super(str, th, z2, z3);
    }

    public ThreadException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
