package cn.hutool.core.bean;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class BeanException extends RuntimeException {
    private static final long serialVersionUID = -8096998667745023423L;

    public BeanException(Throwable th) {
        super(ExceptionUtil.getMessage(th), th);
    }

    public BeanException(String str) {
        super(str);
    }

    public BeanException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public BeanException(String str, Throwable th) {
        super(str, th);
    }

    public BeanException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
