package org.apache.http;

import java.lang.reflect.InvocationTargetException;
import org.apache.http.util.ExceptionUtils;

/* loaded from: classes9.dex */
public class HttpException extends Exception {
    private static final long serialVersionUID = -5437299376222011036L;

    public HttpException() {
    }

    public HttpException(String str) {
        super(str);
    }

    public HttpException(String str, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super(str);
        ExceptionUtils.initCause(this, th);
    }
}
