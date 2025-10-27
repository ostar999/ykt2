package net.lingala.zip4j.exception;

/* loaded from: classes9.dex */
public class ZipException extends Exception {
    private static final long serialVersionUID = 1;
    private int code;

    public ZipException() {
        this.code = -1;
    }

    public int getCode() {
        return this.code;
    }

    public ZipException(String str) {
        super(str);
        this.code = -1;
    }

    public ZipException(String str, Throwable th) {
        super(str, th);
        this.code = -1;
    }

    public ZipException(String str, int i2) {
        super(str);
        this.code = i2;
    }

    public ZipException(String str, Throwable th, int i2) {
        super(str, th);
        this.code = i2;
    }

    public ZipException(Throwable th) {
        super(th);
        this.code = -1;
    }

    public ZipException(Throwable th, int i2) {
        super(th);
        this.code = i2;
    }
}
