package cn.hutool.core.date;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class DateException extends RuntimeException {
    private static final long serialVersionUID = 8247610319171014183L;

    public DateException(Throwable th) {
        super(ExceptionUtil.getMessage(th), th);
    }

    public DateException(String str) {
        super(str);
    }

    public DateException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public DateException(String str, Throwable th) {
        super(str, th);
    }

    public DateException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
