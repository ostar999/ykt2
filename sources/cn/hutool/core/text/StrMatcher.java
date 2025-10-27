package cn.hutool.core.text;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class StrMatcher {
    List<String> patterns;

    public StrMatcher(String str) {
        this.patterns = parse(str);
    }

    private static List<String> parse(String str) {
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        StringBuilder sbBuilder = StrUtil.builder();
        int i2 = 0;
        char c3 = 0;
        boolean z2 = false;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (z2) {
                sbBuilder.append(cCharAt);
                if ('}' == cCharAt) {
                    arrayList.add(sbBuilder.toString());
                    sbBuilder.setLength(0);
                    z2 = false;
                }
            } else if ('{' == cCharAt && '$' == c3) {
                String strSubstring = sbBuilder.substring(0, sbBuilder.length() - 1);
                if (CharSequenceUtil.isNotEmpty(strSubstring)) {
                    arrayList.add(strSubstring);
                }
                sbBuilder.setLength(0);
                sbBuilder.append(c3);
                sbBuilder.append(cCharAt);
                z2 = true;
            } else {
                sbBuilder.append(cCharAt);
            }
            i2++;
            c3 = cCharAt;
        }
        if (sbBuilder.length() > 0) {
            arrayList.add(sbBuilder.toString());
        }
        return arrayList;
    }

    public Map<String, String> match(String str) {
        HashMap mapNewHashMap = MapUtil.newHashMap(true);
        int length = 0;
        String strSub = null;
        for (String str2 : this.patterns) {
            if (CharSequenceUtil.isWrap(str2, "${", "}")) {
                strSub = CharSequenceUtil.sub(str2, 2, str2.length() - 1);
            } else {
                int iIndexOf = str.indexOf(str2, length);
                if (iIndexOf < 0) {
                    return MapUtil.empty();
                }
                if (strSub != null && iIndexOf > length) {
                    mapNewHashMap.put(strSub, str.substring(length, iIndexOf));
                }
                strSub = null;
                length = iIndexOf + str2.length();
            }
        }
        if (strSub != null && length < str.length()) {
            mapNewHashMap.put(strSub, str.substring(length));
        }
        return mapNewHashMap;
    }
}
