package org.bouncycastle.i18n.filter;

/* loaded from: classes9.dex */
public class HTMLFilter implements Filter {
    @Override // org.bouncycastle.i18n.filter.Filter
    public String doFilter(String str) {
        int i2;
        String str2;
        StringBuffer stringBuffer = new StringBuffer(str);
        int i3 = 0;
        while (i3 < stringBuffer.length()) {
            char cCharAt = stringBuffer.charAt(i3);
            if (cCharAt == '\"') {
                i2 = i3 + 1;
                str2 = "&#34";
            } else if (cCharAt == '#') {
                i2 = i3 + 1;
                str2 = "&#35";
            } else if (cCharAt == '+') {
                i2 = i3 + 1;
                str2 = "&#43";
            } else if (cCharAt == '-') {
                i2 = i3 + 1;
                str2 = "&#45";
            } else if (cCharAt == '>') {
                i2 = i3 + 1;
                str2 = "&#62";
            } else if (cCharAt == ';') {
                i2 = i3 + 1;
                str2 = "&#59";
            } else if (cCharAt != '<') {
                switch (cCharAt) {
                    case '%':
                        i2 = i3 + 1;
                        str2 = "&#37";
                        break;
                    case '&':
                        i2 = i3 + 1;
                        str2 = "&#38";
                        break;
                    case '\'':
                        i2 = i3 + 1;
                        str2 = "&#39";
                        break;
                    case '(':
                        i2 = i3 + 1;
                        str2 = "&#40";
                        break;
                    case ')':
                        i2 = i3 + 1;
                        str2 = "&#41";
                        break;
                    default:
                        i3 -= 3;
                        continue;
                        i3 += 4;
                }
            } else {
                i2 = i3 + 1;
                str2 = "&#60";
            }
            stringBuffer.replace(i3, i2, str2);
            i3 += 4;
        }
        return stringBuffer.toString();
    }

    @Override // org.bouncycastle.i18n.filter.Filter
    public String doFilterUrl(String str) {
        return doFilter(str);
    }
}
