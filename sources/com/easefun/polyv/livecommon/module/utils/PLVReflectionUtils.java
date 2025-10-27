package com.easefun.polyv.livecommon.module.utils;

import com.plv.foundationsdk.log.PLVCommonLog;
import java.lang.reflect.Field;

/* loaded from: classes3.dex */
public class PLVReflectionUtils {
    public static void cleanFields(Object object) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                field.set(object, null);
            } catch (IllegalAccessException e2) {
                PLVCommonLog.d("PLVReflectionUtils", e2.getMessage());
            }
        }
    }
}
