package cn.hutool.core.exceptions;

import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class NotInitedException extends RuntimeException {
    private static final long serialVersionUID = 8247610319171014183L;

    public NotInitedException(Throwable th) {
        super(th);
    }

    public NotInitedException(String str) {
        super(str);
    }

    public NotInitedException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public NotInitedException(String str, Throwable th) {
        super(str, th);
    }

    public NotInitedException(String str, Throwable th, boolean z2, boolean z3) {
        super(str, th, z2, z3);
    }

    public NotInitedException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
