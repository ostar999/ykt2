package com.google.android.gms.internal.icing;

import android.net.Uri;
import java.util.Map;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
public final class zzbm {
    private final Map<String, Map<String, String>> map;

    public zzbm(Map<String, Map<String, String>> map) {
        this.map = map;
    }

    @Nullable
    public final String zza(@Nullable Uri uri, @Nullable String str, @Nullable String str2, String str3) {
        if (uri != null) {
            str = uri.toString();
        } else if (str == null) {
            return null;
        }
        Map<String, String> map = this.map.get(str);
        if (map == null) {
            return null;
        }
        if (str2 != null) {
            String strValueOf = String.valueOf(str3);
            str3 = strValueOf.length() != 0 ? str2.concat(strValueOf) : new String(str2);
        }
        return map.get(str3);
    }
}
