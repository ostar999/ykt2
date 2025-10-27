package com.xiaomi.push;

import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes6.dex */
public class gn extends Exception {

    /* renamed from: a, reason: collision with root package name */
    private gw f24945a;

    /* renamed from: a, reason: collision with other field name */
    private gx f495a;

    /* renamed from: a, reason: collision with other field name */
    private Throwable f496a;

    public gn() {
        this.f24945a = null;
        this.f495a = null;
        this.f496a = null;
    }

    public gn(gw gwVar) {
        this.f495a = null;
        this.f496a = null;
        this.f24945a = gwVar;
    }

    public gn(String str) {
        super(str);
        this.f24945a = null;
        this.f495a = null;
        this.f496a = null;
    }

    public gn(String str, Throwable th) {
        super(str);
        this.f24945a = null;
        this.f495a = null;
        this.f496a = th;
    }

    public gn(Throwable th) {
        this.f24945a = null;
        this.f495a = null;
        this.f496a = th;
    }

    public Throwable a() {
        return this.f496a;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        gw gwVar;
        gx gxVar;
        String message = super.getMessage();
        return (message != null || (gxVar = this.f495a) == null) ? (message != null || (gwVar = this.f24945a) == null) ? message : gwVar.toString() : gxVar.toString();
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        if (this.f496a != null) {
            printStream.println("Nested Exception: ");
            this.f496a.printStackTrace(printStream);
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        if (this.f496a != null) {
            printWriter.println("Nested Exception: ");
            this.f496a.printStackTrace(printWriter);
        }
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String message = super.getMessage();
        if (message != null) {
            sb.append(message);
            sb.append(": ");
        }
        gx gxVar = this.f495a;
        if (gxVar != null) {
            sb.append(gxVar);
        }
        gw gwVar = this.f24945a;
        if (gwVar != null) {
            sb.append(gwVar);
        }
        if (this.f496a != null) {
            sb.append("\n  -- caused by: ");
            sb.append(this.f496a);
        }
        return sb.toString();
    }
}
