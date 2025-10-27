package com.beizi.ad.a.a;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.hjq.permissions.Permission;

/* loaded from: classes2.dex */
public final class k {
    public static boolean a(Context context) {
        return ContextCompat.checkSelfPermission(context, Permission.WRITE_EXTERNAL_STORAGE) == 0 && ContextCompat.checkSelfPermission(context, Permission.READ_EXTERNAL_STORAGE) == 0;
    }
}
