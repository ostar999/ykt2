package org.apache.http.client.utils;

import java.lang.reflect.InvocationTargetException;
import org.apache.http.annotation.Immutable;

@Immutable
/* loaded from: classes9.dex */
public class CloneUtils {
    private CloneUtils() {
    }

    public static Object clone(Object obj) throws CloneNotSupportedException {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Cloneable)) {
            throw new CloneNotSupportedException();
        }
        try {
            try {
                return obj.getClass().getMethod("clone", null).invoke(obj, null);
            } catch (IllegalAccessException e2) {
                throw new IllegalAccessError(e2.getMessage());
            } catch (InvocationTargetException e3) {
                Throwable cause = e3.getCause();
                if (cause instanceof CloneNotSupportedException) {
                    throw ((CloneNotSupportedException) cause);
                }
                throw new Error("Unexpected exception", cause);
            }
        } catch (NoSuchMethodException e4) {
            throw new NoSuchMethodError(e4.getMessage());
        }
    }
}
