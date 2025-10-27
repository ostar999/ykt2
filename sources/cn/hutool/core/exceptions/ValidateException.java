package cn.hutool.core.exceptions;

import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class ValidateException extends StatefulException {
    private static final long serialVersionUID = 6057602589533840889L;

    public ValidateException() {
    }

    public ValidateException(String str) {
        super(str);
    }

    public ValidateException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public ValidateException(Throwable th) {
        super(th);
    }

    public ValidateException(String str, Throwable th) {
        super(str, th);
    }

    public ValidateException(int i2, String str) {
        super(i2, str);
    }

    public ValidateException(int i2, Throwable th) {
        super(i2, th);
    }

    public ValidateException(String str, Throwable th, boolean z2, boolean z3) {
        super(str, th, z2, z3);
    }

    public ValidateException(int i2, String str, Throwable th) {
        super(i2, str, th);
    }
}
