package cn.hutool.core.clone;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class CloneRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 6774837422188798989L;

    public CloneRuntimeException(Throwable th) {
        super(ExceptionUtil.getMessage(th), th);
    }

    public CloneRuntimeException(String str) {
        super(str);
    }

    public CloneRuntimeException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public CloneRuntimeException(String str, Throwable th) {
        super(str, th);
    }

    public CloneRuntimeException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
