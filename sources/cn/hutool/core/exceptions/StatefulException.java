package cn.hutool.core.exceptions;

import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class StatefulException extends RuntimeException {
    private static final long serialVersionUID = 6057602589533840889L;
    private int status;

    public StatefulException() {
    }

    public int getStatus() {
        return this.status;
    }

    public StatefulException(String str) {
        super(str);
    }

    public StatefulException(String str, Object... objArr) {
        super(CharSequenceUtil.format(str, objArr));
    }

    public StatefulException(Throwable th) {
        super(th);
    }

    public StatefulException(String str, Throwable th) {
        super(str, th);
    }

    public StatefulException(String str, Throwable th, boolean z2, boolean z3) {
        super(str, th, z2, z3);
    }

    public StatefulException(int i2, String str) {
        super(str);
        this.status = i2;
    }

    public StatefulException(int i2, Throwable th) {
        super(th);
        this.status = i2;
    }

    public StatefulException(int i2, String str, Throwable th) {
        super(str, th);
        this.status = i2;
    }
}
