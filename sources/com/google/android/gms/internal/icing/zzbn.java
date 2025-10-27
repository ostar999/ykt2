package com.google.android.gms.internal.icing;

import android.net.Uri;
import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;

/* loaded from: classes3.dex */
public final class zzbn {

    @GuardedBy("PhenotypeConstants.class")
    private static final ArrayMap<String, Uri> zzcv = new ArrayMap<>();

    public static synchronized Uri zzl(String str) {
        Uri uri;
        ArrayMap<String, Uri> arrayMap = zzcv;
        uri = arrayMap.get(str);
        if (uri == null) {
            String strValueOf = String.valueOf(Uri.encode(str));
            uri = Uri.parse(strValueOf.length() != 0 ? "content://com.google.android.gms.phenotype/".concat(strValueOf) : new String("content://com.google.android.gms.phenotype/"));
            arrayMap.put(str, uri);
        }
        return uri;
    }
}
