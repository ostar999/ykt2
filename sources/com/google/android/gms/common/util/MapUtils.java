package com.google.android.gms.common.util;

import cn.hutool.core.text.StrPool;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.HashMap;

@KeepForSdk
/* loaded from: classes3.dex */
public class MapUtils {
    @KeepForSdk
    public static void writeStringMapToJson(StringBuilder sb, HashMap<String, String> map) {
        sb.append(StrPool.DELIM_START);
        boolean z2 = true;
        for (String str : map.keySet()) {
            if (z2) {
                z2 = false;
            } else {
                sb.append(",");
            }
            String str2 = map.get(str);
            sb.append("\"");
            sb.append(str);
            sb.append("\":");
            if (str2 == null) {
                sb.append("null");
            } else {
                sb.append("\"");
                sb.append(str2);
                sb.append("\"");
            }
        }
        sb.append("}");
    }
}
