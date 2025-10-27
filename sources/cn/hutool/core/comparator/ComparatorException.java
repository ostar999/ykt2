package cn.hutool.core.comparator;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class ComparatorException extends RuntimeException {
    private static final long serialVersionUID = 4475602435485521971L;

    public ComparatorException(Throwable th) {
        super(ExceptionUtil.getMessage(th), th);
    }

    public ComparatorException(String str) {
        super(str);
    }

    public ComparatorException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public ComparatorException(String str, Throwable th) {
        super(str, th);
    }

    public ComparatorException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
