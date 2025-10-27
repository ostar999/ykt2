package cn.hutool.core.text.escape;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.text.replacer.StrReplacer;
import cn.hutool.core.util.CharUtil;

/* loaded from: classes.dex */
public class NumericEntityUnescaper extends StrReplacer {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.text.replacer.StrReplacer
    public int replace(CharSequence charSequence, int i2, StrBuilder strBuilder) {
        int i3;
        int length = charSequence.length();
        if (charSequence.charAt(i2) == '&' && i2 < length - 2 && charSequence.charAt(i2 + 1) == '#') {
            int i4 = i2 + 2;
            char cCharAt = charSequence.charAt(i4);
            if (cCharAt == 'x' || cCharAt == 'X') {
                i4++;
                i3 = 1;
            } else {
                i3 = 0;
            }
            if (i4 == length) {
                return 0;
            }
            int i5 = i4;
            while (i5 < length && CharUtil.isHexChar(charSequence.charAt(i5))) {
                i5++;
            }
            if (i5 != length && charSequence.charAt(i5) == ';') {
                try {
                    strBuilder.append((char) (i3 != 0 ? Integer.parseInt(charSequence.subSequence(i4, i5).toString(), 16) : Integer.parseInt(charSequence.subSequence(i4, i5).toString(), 10)));
                    return ((i5 + 2) - i4) + i3 + 1;
                } catch (NumberFormatException unused) {
                }
            }
        }
        return 0;
    }
}
