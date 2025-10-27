package com.plv.foundationsdk.utils;

import androidx.annotation.NonNull;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.lang.reflect.Field;

/* loaded from: classes4.dex */
public class PLVReflectionUtil {
    public static <T, R extends T> R copyField(@NonNull T t2, @NonNull R r2) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        try {
            for (Class<?> superclass = t2.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
                if (Object.class.equals(superclass)) {
                    break;
                }
                for (Field field : superclass.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (superclass.isAssignableFrom(r2.getClass())) {
                        field.set(r2, field.get(t2));
                    }
                }
            }
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
        }
        return r2;
    }

    public static <T> Class<? extends T> getClassNoInline(T t2) {
        return (Class<? extends T>) t2.getClass();
    }
}
