package cn.hutool.core.io.watch;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class WatchException extends RuntimeException {
    private static final long serialVersionUID = 8068509879445395353L;

    public WatchException(Throwable th) {
        super(ExceptionUtil.getMessage(th), th);
    }

    public WatchException(String str) {
        super(str);
    }

    public WatchException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public WatchException(String str, Throwable th) {
        super(str, th);
    }

    public WatchException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
