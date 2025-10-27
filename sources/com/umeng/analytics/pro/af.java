package com.umeng.analytics.pro;

import android.content.Context;
import org.repackage.com.meizu.flyme.openidsdk.OpenIdHelper;

/* loaded from: classes6.dex */
class af implements z {
    @Override // com.umeng.analytics.pro.z
    public String a(Context context) throws NoSuchMethodException, SecurityException {
        if (context == null) {
            return null;
        }
        boolean zA = OpenIdHelper.a();
        al.a("getOAID", "isSupported", Boolean.valueOf(zA));
        if (zA) {
            return OpenIdHelper.b(context);
        }
        return null;
    }
}
