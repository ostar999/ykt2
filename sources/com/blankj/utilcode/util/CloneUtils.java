package com.blankj.utilcode.util;

import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public final class CloneUtils {
    private CloneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static <T> T deepClone(T t2, Type type) {
        try {
            return (T) UtilsBridge.fromJson(UtilsBridge.toJson(t2), type);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
