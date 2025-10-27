package net.tsz.afinal.exception;

/* loaded from: classes9.dex */
public class AfinalException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public AfinalException() {
    }

    public AfinalException(String str) {
        super(str);
    }

    public AfinalException(Throwable th) {
        super(th);
    }

    public AfinalException(String str, Throwable th) {
        super(str, th);
    }
}
