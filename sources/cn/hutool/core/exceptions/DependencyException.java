package cn.hutool.core.exceptions;

import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class DependencyException extends RuntimeException {
    private static final long serialVersionUID = 8247610319171014183L;

    public DependencyException(Throwable th) {
        super(ExceptionUtil.getMessage(th), th);
    }

    public DependencyException(String str) {
        super(str);
    }

    public DependencyException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public DependencyException(String str, Throwable th) {
        super(str, th);
    }

    public DependencyException(String str, Throwable th, boolean z2, boolean z3) {
        super(str, th, z2, z3);
    }

    public DependencyException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
