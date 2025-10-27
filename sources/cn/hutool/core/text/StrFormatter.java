package cn.hutool.core.text;

import cn.hutool.core.util.StrUtil;
import java.util.Map;

/* loaded from: classes.dex */
public class StrFormatter {
    public static String format(String str, Object... objArr) {
        return formatWith(str, StrPool.EMPTY_JSON, objArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String formatWith(java.lang.String r11, java.lang.String r12, java.lang.Object... r13) {
        /*
            boolean r0 = cn.hutool.core.text.CharSequenceUtil.isBlank(r11)
            if (r0 != 0) goto L85
            boolean r0 = cn.hutool.core.text.CharSequenceUtil.isBlank(r12)
            if (r0 != 0) goto L85
            boolean r0 = cn.hutool.core.util.ArrayUtil.isEmpty(r13)
            if (r0 == 0) goto L14
            goto L85
        L14:
            int r0 = r11.length()
            int r1 = r12.length()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            int r3 = r0 + 50
            r2.<init>(r3)
            r3 = 0
            r4 = r3
            r5 = r4
        L26:
            int r6 = r13.length
            if (r4 >= r6) goto L7e
            int r6 = r11.indexOf(r12, r5)
            r7 = -1
            if (r6 != r7) goto L3b
            if (r5 != 0) goto L33
            return r11
        L33:
            r2.append(r11, r5, r0)
            java.lang.String r11 = r2.toString()
            return r11
        L3b:
            r7 = 1
            if (r6 <= 0) goto L6e
            int r8 = r6 + (-1)
            char r9 = r11.charAt(r8)
            r10 = 92
            if (r9 != r10) goto L6e
            if (r6 <= r7) goto L5f
            int r9 = r6 + (-2)
            char r9 = r11.charAt(r9)
            if (r9 != r10) goto L5f
            r2.append(r11, r5, r8)
            r5 = r13[r4]
            java.lang.String r5 = cn.hutool.core.util.StrUtil.utf8Str(r5)
            r2.append(r5)
            goto L7a
        L5f:
            int r4 = r4 + (-1)
            r2.append(r11, r5, r8)
            char r5 = r12.charAt(r3)
            r2.append(r5)
            int r6 = r6 + 1
            goto L7b
        L6e:
            r2.append(r11, r5, r6)
            r5 = r13[r4]
            java.lang.String r5 = cn.hutool.core.util.StrUtil.utf8Str(r5)
            r2.append(r5)
        L7a:
            int r6 = r6 + r1
        L7b:
            r5 = r6
            int r4 = r4 + r7
            goto L26
        L7e:
            r2.append(r11, r5, r0)
            java.lang.String r11 = r2.toString()
        L85:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.text.StrFormatter.formatWith(java.lang.String, java.lang.String, java.lang.Object[]):java.lang.String");
    }

    public static String format(CharSequence charSequence, Map<?, ?> map, boolean z2) {
        if (charSequence == null) {
            return null;
        }
        if (map == null || map.isEmpty()) {
            return charSequence.toString();
        }
        String string = charSequence.toString();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String strUtf8Str = StrUtil.utf8Str(entry.getValue());
            if (strUtf8Str != null || !z2) {
                string = CharSequenceUtil.replace(string, StrPool.DELIM_START + entry.getKey() + "}", strUtf8Str);
            }
        }
        return string;
    }
}
