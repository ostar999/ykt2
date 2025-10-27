package org.bouncycastle.ocsp;

/* loaded from: classes9.dex */
public class OCSPException extends Exception {

    /* renamed from: e, reason: collision with root package name */
    Exception f27943e;

    public OCSPException(String str) {
        super(str);
    }

    public OCSPException(String str, Exception exc) {
        super(str);
        this.f27943e = exc;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.f27943e;
    }

    public Exception getUnderlyingException() {
        return this.f27943e;
    }
}
