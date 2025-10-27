package com.cmic.sso.sdk.view;

import android.content.Context;
import android.content.res.Resources;

/* loaded from: classes3.dex */
public class g {
    public static int a(Context context, String str) {
        int iA = a(context, str, "id");
        if (iA != 0) {
            return iA;
        }
        throw new Resources.NotFoundException(str);
    }

    public static int a(Context context, String str, String str2) {
        return context.getResources().getIdentifier(str, str2, context.getPackageName());
    }

    public static int b(Context context, String str) {
        int iA = a(context, str, "drawable");
        if (iA != 0) {
            return iA;
        }
        throw new Resources.NotFoundException(str);
    }

    public static int c(Context context, String str) {
        int iA = a(context, str, "anim");
        if (iA != 0) {
            return iA;
        }
        throw new Resources.NotFoundException(str);
    }
}
