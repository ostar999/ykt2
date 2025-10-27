package com.aliyun.vod.common.utils;

import cn.hutool.core.text.StrPool;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class JsonFormatUtils {
    private static void doFill(StringBuilder sb, int i2, String str) {
        sb.append("\n");
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(str);
        }
    }

    public static String formatJson(String str) {
        int length;
        int i2;
        if (str == null || str.trim().length() == 0) {
            return "";
        }
        ArrayList arrayList = new ArrayList();
        while (str.length() > 0) {
            String token = getToken(str);
            str = str.substring(token.length());
            arrayList.add(token.trim());
        }
        int i3 = 0;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            int length2 = ((String) arrayList.get(i4)).getBytes().length;
            if (length2 > i3 && i4 < arrayList.size() - 1 && ((String) arrayList.get(i4 + 1)).equals(":")) {
                i3 = length2;
            }
        }
        StringBuilder sb = new StringBuilder();
        int i5 = 0;
        int i6 = 0;
        while (i5 < arrayList.size()) {
            String str2 = (String) arrayList.get(i5);
            if (str2.equals(",")) {
                sb.append(str2);
                doFill(sb, i6, StrPool.TAB);
            } else if (str2.equals(":")) {
                sb.append(" ");
                sb.append(str2);
                sb.append(" ");
            } else if (str2.equals(StrPool.DELIM_START)) {
                i2 = i5 + 1;
                if (((String) arrayList.get(i2)).equals("}")) {
                    sb.append("{ }");
                    i5 = i2;
                } else {
                    i6++;
                    sb.append(str2);
                    doFill(sb, i6, StrPool.TAB);
                }
            } else if (str2.equals("}")) {
                i6--;
                doFill(sb, i6, StrPool.TAB);
                sb.append(str2);
            } else if (str2.equals(StrPool.BRACKET_START)) {
                i2 = i5 + 1;
                if (((String) arrayList.get(i2)).equals(StrPool.BRACKET_END)) {
                    sb.append("[ ]");
                    i5 = i2;
                } else {
                    i6++;
                    sb.append(str2);
                    doFill(sb, i6, StrPool.TAB);
                }
            } else if (str2.equals(StrPool.BRACKET_END)) {
                i6--;
                doFill(sb, i6, StrPool.TAB);
                sb.append(str2);
            } else {
                sb.append(str2);
                if (i5 < arrayList.size() - 1 && ((String) arrayList.get(i5 + 1)).equals(":") && (length = i3 - str2.getBytes().length) > 0) {
                    for (int i7 = 0; i7 < length; i7++) {
                        sb.append(" ");
                    }
                }
            }
            i5++;
        }
        return sb.toString();
    }

    private static String getToken(String str) {
        StringBuilder sb = new StringBuilder();
        boolean z2 = false;
        while (str.length() > 0) {
            String strSubstring = str.substring(0, 1);
            str = str.substring(1);
            if (z2 || !(strSubstring.equals(":") || strSubstring.equals(StrPool.DELIM_START) || strSubstring.equals("}") || strSubstring.equals(StrPool.BRACKET_START) || strSubstring.equals(StrPool.BRACKET_END) || strSubstring.equals(","))) {
                if (strSubstring.equals(StrPool.BACKSLASH)) {
                    sb.append(strSubstring);
                    sb.append(str.substring(0, 1));
                    str = str.substring(1);
                } else if (strSubstring.equals("\"")) {
                    sb.append(strSubstring);
                    if (z2) {
                        break;
                    }
                    z2 = true;
                } else {
                    sb.append(strSubstring);
                }
            } else if (sb.toString().trim().length() == 0) {
                sb.append(strSubstring);
            }
        }
        return sb.toString();
    }
}
