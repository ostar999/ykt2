package net.tsz.afinal.exception;

/* loaded from: classes9.dex */
public class DbException extends AfinalException {
    private static final long serialVersionUID = 1;

    public DbException() {
    }

    public DbException(String str) {
        super(str);
    }

    public DbException(Throwable th) {
        super(th);
    }

    public DbException(String str, Throwable th) {
        super(str, th);
    }
}
