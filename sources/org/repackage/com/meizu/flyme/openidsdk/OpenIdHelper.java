package org.repackage.com.meizu.flyme.openidsdk;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.Method;

/* loaded from: classes9.dex */
public class OpenIdHelper {

    /* renamed from: a, reason: collision with root package name */
    private static final String f27998a = "OpenIdHelper";

    /* renamed from: b, reason: collision with root package name */
    private static Method f27999b;

    public static String a(Context context) {
        b bVarA = b.a();
        return bVarA.a(context.getApplicationContext(), bVarA.f28007a);
    }

    public static void a(boolean z2) {
        b.a();
        b.a(z2);
    }

    public static final boolean a() throws NoSuchMethodException, SecurityException {
        Context context = null;
        try {
            if (f27999b == null) {
                Method method = Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]);
                f27999b = method;
                method.setAccessible(true);
            }
            context = (Context) f27999b.invoke(null, new Object[0]);
        } catch (Exception e2) {
            Log.e(f27998a, "ActivityThread:currentApplication --> " + e2.toString());
        }
        if (context == null) {
            return false;
        }
        return b.a().a(context, false);
    }

    public static String b(Context context) {
        b bVarA = b.a();
        return bVarA.a(context.getApplicationContext(), bVarA.f28008b);
    }

    public static String c(Context context) {
        b bVarA = b.a();
        return bVarA.a(context.getApplicationContext(), bVarA.f28010d);
    }

    public static String d(Context context) {
        b bVarA = b.a();
        return bVarA.a(context.getApplicationContext(), bVarA.f28009c);
    }
}
