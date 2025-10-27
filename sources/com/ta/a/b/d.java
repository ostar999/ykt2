package com.ta.a.b;

import android.content.Context;
import com.hjq.permissions.Permission;

/* loaded from: classes6.dex */
public class d {
    public static boolean a(Context context) {
        try {
            return a(context, Permission.WRITE_EXTERNAL_STORAGE);
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean b(Context context) {
        try {
            return a(context, Permission.READ_PHONE_STATE);
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean a(Context context, String str) {
        if (context == null) {
            return false;
        }
        if (com.ta.a.e.a.a(context) >= 23) {
            if (context.checkSelfPermission(str) != 0) {
                return false;
            }
        } else if (c.a(context, str) != 0) {
            return false;
        }
        return true;
    }
}
