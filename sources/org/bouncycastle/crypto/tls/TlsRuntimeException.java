package org.bouncycastle.crypto.tls;

/* loaded from: classes9.dex */
public class TlsRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1928023487348344086L;

    /* renamed from: e, reason: collision with root package name */
    Throwable f27885e;

    public TlsRuntimeException(String str) {
        super(str);
    }

    public TlsRuntimeException(String str, Throwable th) {
        super(str);
        this.f27885e = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.f27885e;
    }
}
