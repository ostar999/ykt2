package cn.hutool.core.compiler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class CompilerException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public CompilerException(Throwable th) {
        super(ExceptionUtil.getMessage(th), th);
    }

    public CompilerException(String str) {
        super(str);
    }

    public CompilerException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public CompilerException(String str, Throwable th) {
        super(str, th);
    }

    public CompilerException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
