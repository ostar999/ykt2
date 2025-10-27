package com.mobile.auth.gatewayauth;

import android.content.res.Configuration;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class a {
    public static float a() {
        try {
            try {
                Configuration configuration = new Configuration();
                Class<?> cls = Class.forName("android.app.ActivityManagerNative");
                Object objInvoke = cls.getMethod("getDefault", new Class[0]).invoke(cls, new Object[0]);
                configuration.updateFrom((Configuration) objInvoke.getClass().getMethod("getConfiguration", new Class[0]).invoke(objInvoke, new Object[0]));
                return configuration.fontScale;
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e2) {
                e2.printStackTrace();
                return 1.0f;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1.0f;
        }
    }
}
