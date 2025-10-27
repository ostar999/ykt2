package cn.hutool.core.convert;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class ConvertException extends RuntimeException {
    private static final long serialVersionUID = 4730597402855274362L;

    public ConvertException(Throwable th) {
        super(ExceptionUtil.getMessage(th), th);
    }

    public ConvertException(String str) {
        super(str);
    }

    public ConvertException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public ConvertException(String str, Throwable th) {
        super(str, th);
    }

    public ConvertException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
