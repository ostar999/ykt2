package com.plv.livescenes.net;

import androidx.annotation.NonNull;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes5.dex */
public class PLVEncryptManager {
    @NonNull
    public static String createSign(Map<String, String> map) {
        String[] strArr = (String[]) map.keySet().toArray(new String[0]);
        Arrays.sort(strArr);
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            sb.append(str);
            sb.append(map.get(str));
        }
        return EncryptUtils.encryptMD5ToString(sb.toString()).toUpperCase();
    }
}
