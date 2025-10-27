package org.eclipse.jetty.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import okhttp3.HttpUrl;

/* loaded from: classes9.dex */
public class MultiException extends Exception {
    private Object nested;

    public MultiException() {
        super("Multiple exceptions");
    }

    public void add(Throwable th) {
        if (!(th instanceof MultiException)) {
            this.nested = LazyList.add(this.nested, th);
            return;
        }
        MultiException multiException = (MultiException) th;
        for (int i2 = 0; i2 < LazyList.size(multiException.nested); i2++) {
            this.nested = LazyList.add(this.nested, LazyList.get(multiException.nested, i2));
        }
    }

    public Throwable getThrowable(int i2) {
        return (Throwable) LazyList.get(this.nested, i2);
    }

    public List<Throwable> getThrowables() {
        return LazyList.getList(this.nested);
    }

    public void ifExceptionThrow() throws Exception {
        int size = LazyList.size(this.nested);
        if (size != 0) {
            if (size != 1) {
                throw this;
            }
            Throwable th = (Throwable) LazyList.get(this.nested, 0);
            if (th instanceof Error) {
                throw ((Error) th);
            }
            if (!(th instanceof Exception)) {
                throw this;
            }
            throw ((Exception) th);
        }
    }

    public void ifExceptionThrowMulti() throws MultiException {
        if (LazyList.size(this.nested) > 0) {
            throw this;
        }
    }

    public void ifExceptionThrowRuntime() throws Error {
        int size = LazyList.size(this.nested);
        if (size != 0) {
            if (size != 1) {
                throw new RuntimeException(this);
            }
            Throwable th = (Throwable) LazyList.get(this.nested, 0);
            if (th instanceof Error) {
                throw ((Error) th);
            }
            if (!(th instanceof RuntimeException)) {
                throw new RuntimeException(th);
            }
            throw ((RuntimeException) th);
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        super.printStackTrace();
        for (int i2 = 0; i2 < LazyList.size(this.nested); i2++) {
            ((Throwable) LazyList.get(this.nested, i2)).printStackTrace();
        }
    }

    public int size() {
        return LazyList.size(this.nested);
    }

    @Override // java.lang.Throwable
    public String toString() {
        if (LazyList.size(this.nested) > 0) {
            return MultiException.class.getSimpleName() + LazyList.getList(this.nested);
        }
        return MultiException.class.getSimpleName() + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        for (int i2 = 0; i2 < LazyList.size(this.nested); i2++) {
            ((Throwable) LazyList.get(this.nested, i2)).printStackTrace(printStream);
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        for (int i2 = 0; i2 < LazyList.size(this.nested); i2++) {
            ((Throwable) LazyList.get(this.nested, i2)).printStackTrace(printWriter);
        }
    }
}
