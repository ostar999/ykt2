package cn.hutool.crypto;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class CryptoException extends RuntimeException {
    private static final long serialVersionUID = 8068509879445395353L;

    public CryptoException(Throwable th) {
        super(ExceptionUtil.getMessage(th), th);
    }

    public CryptoException(String str) {
        super(str);
    }

    public CryptoException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public CryptoException(String str, Throwable th) {
        super(str, th);
    }

    public CryptoException(String str, Throwable th, boolean z2, boolean z3) {
        super(str, th, z2, z3);
    }

    public CryptoException(Throwable th, String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr), th);
    }
}
