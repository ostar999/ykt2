package com.google.android.gms.internal.icing;

import cn.hutool.core.text.StrPool;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzfi {
    public static String zza(zzfh zzfhVar, String str) throws SecurityException {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        zza(zzfhVar, sb, 0);
        return sb.toString();
    }

    private static final String zzs(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (Character.isUpperCase(cCharAt)) {
                sb.append(StrPool.UNDERLINE);
            }
            sb.append(Character.toLowerCase(cCharAt));
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:84:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void zza(com.google.android.gms.internal.icing.zzfh r13, java.lang.StringBuilder r14, int r15) throws java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 667
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzfi.zza(com.google.android.gms.internal.icing.zzfh, java.lang.StringBuilder, int):void");
    }

    public static final void zza(StringBuilder sb, int i2, String str, Object obj) throws SecurityException {
        if (obj instanceof List) {
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                zza(sb, i2, str, it.next());
            }
            return;
        }
        if (obj instanceof Map) {
            Iterator it2 = ((Map) obj).entrySet().iterator();
            while (it2.hasNext()) {
                zza(sb, i2, str, (Map.Entry) it2.next());
            }
            return;
        }
        sb.append('\n');
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            sb.append(' ');
        }
        sb.append(str);
        if (obj instanceof String) {
            sb.append(": \"");
            sb.append(zzgi.zzd(zzct.zzp((String) obj)));
            sb.append('\"');
            return;
        }
        if (obj instanceof zzct) {
            sb.append(": \"");
            sb.append(zzgi.zzd((zzct) obj));
            sb.append('\"');
            return;
        }
        if (obj instanceof zzdx) {
            sb.append(" {");
            zza((zzdx) obj, sb, i2 + 2);
            sb.append("\n");
            while (i3 < i2) {
                sb.append(' ');
                i3++;
            }
            sb.append("}");
            return;
        }
        if (obj instanceof Map.Entry) {
            sb.append(" {");
            Map.Entry entry = (Map.Entry) obj;
            int i5 = i2 + 2;
            zza(sb, i5, "key", entry.getKey());
            zza(sb, i5, "value", entry.getValue());
            sb.append("\n");
            while (i3 < i2) {
                sb.append(' ');
                i3++;
            }
            sb.append("}");
            return;
        }
        sb.append(": ");
        sb.append(obj.toString());
    }
}
