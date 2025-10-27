package com.google.android.gms.internal.icing;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class zzax {
    private static HashMap<String, String> zzbw;
    private static Object zzcb;
    private static boolean zzcc;
    public static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzbs = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzbt = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzbu = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzbv = new AtomicBoolean();
    private static final HashMap<String, Boolean> zzbx = new HashMap<>();
    private static final HashMap<String, Integer> zzby = new HashMap<>();
    private static final HashMap<String, Long> zzbz = new HashMap<>();
    private static final HashMap<String, Float> zzca = new HashMap<>();
    private static String[] zzcd = new String[0];

    public static String zza(ContentResolver contentResolver, String str, String str2) {
        synchronized (zzax.class) {
            if (zzbw == null) {
                zzbv.set(false);
                zzbw = new HashMap<>();
                zzcb = new Object();
                zzcc = false;
                contentResolver.registerContentObserver(CONTENT_URI, true, new zzba(null));
            } else if (zzbv.getAndSet(false)) {
                zzbw.clear();
                zzbx.clear();
                zzby.clear();
                zzbz.clear();
                zzca.clear();
                zzcb = new Object();
                zzcc = false;
            }
            Object obj = zzcb;
            if (zzbw.containsKey(str)) {
                String str3 = zzbw.get(str);
                return str3 != null ? str3 : null;
            }
            for (String str4 : zzcd) {
                if (str.startsWith(str4)) {
                    if (!zzcc || zzbw.isEmpty()) {
                        zzbw.putAll(zza(contentResolver, zzcd));
                        zzcc = true;
                        if (zzbw.containsKey(str)) {
                            String str5 = zzbw.get(str);
                            return str5 != null ? str5 : null;
                        }
                    }
                    return null;
                }
            }
            Cursor cursorQuery = contentResolver.query(CONTENT_URI, null, null, new String[]{str}, null);
            if (cursorQuery == null) {
                if (cursorQuery != null) {
                }
                return null;
            }
            try {
                if (!cursorQuery.moveToFirst()) {
                    zza(obj, str, (String) null);
                    return null;
                }
                String string = cursorQuery.getString(1);
                if (string != null && string.equals(null)) {
                    string = null;
                }
                zza(obj, str, string);
                return string != null ? string : null;
            } finally {
                cursorQuery.close();
            }
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzax.class) {
            if (obj == zzcb) {
                zzbw.put(str, str2);
            }
        }
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor cursorQuery = contentResolver.query(zzbs, null, null, strArr, null);
        TreeMap treeMap = new TreeMap();
        if (cursorQuery == null) {
            return treeMap;
        }
        while (cursorQuery.moveToNext()) {
            try {
                treeMap.put(cursorQuery.getString(0), cursorQuery.getString(1));
            } finally {
                cursorQuery.close();
            }
        }
        return treeMap;
    }
}
