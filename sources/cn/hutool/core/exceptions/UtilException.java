package cn.hutool.core.exceptions;

import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class UtilException extends RuntimeException {
    private static final long serialVersionUID = 8247610319171014183L;

    public UtilException(Throwable th) {
        super(ExceptionUtil.getMessage(th), th);
    }

    public UtilException(String str) {
        super(str);
    }

    public UtilException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public UtilException(String str, Throwable th) {
        super(str, th);
    }

    public UtilException(String str, Throwable th, boolean z2, boolean z3) {
        super(str, th, z2, z3);
    }

    public UtilException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
